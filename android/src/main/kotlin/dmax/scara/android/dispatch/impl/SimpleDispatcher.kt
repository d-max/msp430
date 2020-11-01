package dmax.scara.android.dispatch.impl

import dmax.scara.android.app.misc.State
import dmax.scara.android.connect.Command
import dmax.scara.android.connect.Command.Servo
import dmax.scara.android.connect.Connector
import dmax.scara.android.dispatch.Dispatcher
import dmax.scara.android.dispatch.Event
import kotlinx.coroutines.delay

class SimpleDispatcher(
    private val delay: Long,
    private val state: State,
    private val connector: Connector
) : Dispatcher {

    override suspend fun dispatch(event: Event) {
        val (base, elbow, wrist) = event
        base?.let {
            connector.send(Command(Servo.Base, it.angle))
            state.arm = state.arm.copy(base = it)
        }
        elbow?.let {
            connector.send(Command(Servo.Elbow, it.angle))
            state.arm = state.arm.copy(elbow = it)
        }
        wrist?.let {
            connector.send(Command(Servo.Wrist, it.angle))
            state.arm = state.arm.copy(wrist = it)
        }
        delay(delay)
    }
}
