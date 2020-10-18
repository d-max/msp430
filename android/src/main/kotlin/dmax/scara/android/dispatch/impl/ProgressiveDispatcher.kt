package dmax.scara.android.dispatch.impl

import dmax.scara.android.app.misc.State
import dmax.scara.android.connect.Command
import dmax.scara.android.connect.Command.Servo
import dmax.scara.android.connect.Connector
import dmax.scara.android.dispatch.Dispatcher
import dmax.scara.android.dispatch.Event
import dmax.scara.android.domain.mechanics.Joint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancelChildren
import kotlinx.coroutines.channels.SendChannel
import kotlinx.coroutines.channels.actor
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.math.abs
import kotlin.math.max
import kotlin.math.min

// todo reimplement me
class ProgressiveDispatcher(
    private val speedConfig: SpeedConfig,
    private val state: State,
    private val connector: Connector
) : Dispatcher {

    data class SpeedConfig(
        val stepAngle: Byte,
        val stepDelay: Long,
    )

    private var job = Job()
    private val scope = CoroutineScope(Dispatchers.Main)
    private val channel: SendChannel<Command>

    init {
        channel = scope.actor {
            for (command in channel) {
                connector.send(command)
            }
        }
    }

    override suspend fun dispatch(event: Event) {
        val (base, elbow, wrist) = state.arm

        val baseNew = event.base ?: base
        val elbowNew = event.elbow ?: elbow
        val wristNew = event.wrist ?: wrist

        val baseAngles = (base.angle to baseNew.angle).calculate()
        val elbowAngles = (elbow.angle to elbowNew.angle).calculate()
        val wristAngles = (wrist.angle to wristNew.angle).calculate()

        val baseCommands = baseAngles.map { Command(Servo.Base, it) }
        val elbowCommands = elbowAngles.map { Command(Servo.Elbow, it) }
        val wristCommands = wristAngles.map { Command(Servo.Wrist, it) }

        job.cancelChildren()
        enqueue(baseCommands, ::update)
        enqueue(elbowCommands, ::update)
        enqueue(wristCommands, ::update)
    }

    private fun enqueue(
        commands: List<Command>,
        update: (Command) -> Unit
    ) = scope.launch(job) {
        for (command in commands) {
            channel.send(command)
            update.invoke(command)
            delay(speedConfig.stepDelay)
        }
    }

    private fun update(command: Command) = with(state) {
        arm = when (command.servo) {
            Servo.Base -> arm.copy(base = Joint(command.angle))
            Servo.Elbow -> arm.copy(elbow = Joint(command.angle))
            Servo.Wrist -> arm.copy(wrist = Joint(command.angle))
        }
    }

    private fun Int.narrow() = min(1, max(-1, this))

    private fun Pair<Int, Int>.calculate(): List<Int> {
        val (current, new) = this
        val diff = new - current
        val count = abs(diff) / speedConfig.stepAngle
        val direction = diff.narrow()
        return List(count) { index ->
            val shift = (index + 1) * speedConfig.stepAngle * direction
            current + shift
        }
    }
}

