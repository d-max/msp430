package dmax.scara.android.actions.motion

import dmax.scara.android.actions.Actor
import dmax.scara.android.dispatch.Dispatcher
import dmax.scara.android.dispatch.Event
import dmax.scara.android.domain.mechanics.Joint
import kotlinx.coroutines.delay

class DemoActor(
    private val dispatcher: Dispatcher
) : Actor {

    override suspend fun invoke() {
        while (true) {
            with(dispatcher) {
                dispatch(Event(elbow = Joint(0)))
                delay(3_000)

                dispatch(Event(elbow = Joint(90)))
                delay(2_000)

                dispatch(Event(wrist = Joint(40)))
                delay(1_000)

                dispatch(Event(wrist = Joint(70)))
                delay(1_000)

                dispatch(Event(wrist = Joint(90)))
                delay(1_000)

                dispatch(Event(elbow = Joint(170), wrist = Joint(20)))
                delay(3_000)
            }
        }
    }
}
