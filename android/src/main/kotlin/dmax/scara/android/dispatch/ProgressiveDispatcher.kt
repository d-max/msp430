package dmax.scara.android.dispatch

import dmax.scara.android.app.Config
import dmax.scara.android.app.State
import dmax.scara.android.connect.Command
import dmax.scara.android.connect.Connector
import dmax.scara.android.connect.Servo
import dmax.scara.android.domain.mechanics.Joint
import dmax.scara.android.domain.motion.Motion
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

class ProgressiveDispatcher(
    private val state: State,
    private val connector: Connector
) : Dispatcher {

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

    override fun dispatch(motion: Motion) {
        val (base, elbow, wrist) = state

        val baseNew = motion.base ?: base
        val elbowNew = motion.elbow ?: elbow
        val wristNew = motion.wrist ?: wrist

        val baseAngles = (base.angle to baseNew.angle).calculate()
        val elbowAngles = (elbow.angle to elbowNew.angle).calculate()
        val wristAngles = (wrist.angle to wristNew.angle).calculate()

        val baseCommands = baseAngles.map { Command(Servo.Base, it) }
        val elbowCommands = elbowAngles.map { Command(Servo.Elbow, it) }
        val wristCommands = wristAngles.map { Command(Servo.Wrist, it) }

        job.cancelChildren()
        send(baseCommands, ::update)
        send(elbowCommands, ::update)
        send(wristCommands, ::update)
    }

    private fun send(
        commands: List<Command>,
        update: (Command) -> Unit
    ) = scope.launch(job) {
        for (command in commands) {
            channel.send(command)
            update.invoke(command)
            delay(Config.Speed.delay)
        }
    }

    private fun update(command: Command) {
        when(command.servo) {
            Servo.Base -> state.base = Joint(command.angle)
            Servo.Elbow -> state.elbow = Joint(command.angle)
            Servo.Wrist -> state.wrist = Joint(command.angle)
        }
    }

    private fun Int.narrow() = min(1, max(-1, this))

    private fun Pair<Int, Int>.calculate(): List<Int> {
        val (current, new) = this
        val diff = new - current
        val count = abs(diff) / Config.Speed.angle
        val direction = diff.narrow()
        return List(count) { index ->
            val shift = (index + 1) * Config.Speed.angle * direction
            current + shift
        }
    }
}

