package dmax.scara.android.dispatch

import dmax.scara.android.app.State
import dmax.scara.android.connect.Command
import dmax.scara.android.connect.Connector
import dmax.scara.android.connect.Servo
import dmax.scara.android.domain.motion.Motion
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SimpleDispatcher(
    private val state: State,
    private val connector: Connector
) : Dispatcher {

    private val scope = CoroutineScope(Dispatchers.Main)

    override fun dispatch(motion: Motion) {
        val (base, elbow, wrist) = motion
        base?.let {
            scope.launch {
                connector.send(Command(Servo.Base, base.angle))
                state.base = base
            }
        }
        elbow?.let {
            scope.launch {
                connector.send(Command(Servo.Elbow, elbow.angle))
                state.elbow = elbow
            }
        }
        wrist?.let {
            scope.launch {
                connector.send(Command(Servo.Wrist, wrist.angle))
                state.wrist = wrist
            }
        }
    }
}
