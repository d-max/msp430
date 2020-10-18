package dmax.scara.android.dispatch.impl

import dmax.scara.android.app.misc.State
import dmax.scara.android.connect.Command
import dmax.scara.android.connect.Command.Servo
import dmax.scara.android.connect.Connector
import dmax.scara.android.dispatch.Dispatcher
import dmax.scara.android.dispatch.Event

class SimpleDispatcher(
    private val state: State,
    private val connector: Connector
) : Dispatcher {

    override suspend fun dispatch(event: Event) {
        val (base, elbow, wrist) = event
        base?.let {
            connector.send(Command(Servo.Base, base.angle))
            state.arm = state.arm.copy(base = base)
        }
        elbow?.let {
            connector.send(Command(Servo.Elbow, elbow.angle))
            state.arm = state.arm.copy(elbow = elbow)
        }
        wrist?.let {
            connector.send(Command(Servo.Wrist, wrist.angle))
            state.arm = state.arm.copy(wrist = wrist)
        }
    }
}
