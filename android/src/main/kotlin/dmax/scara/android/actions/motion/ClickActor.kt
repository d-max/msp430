package dmax.scara.android.actions.motion

import dmax.scara.android.actions.Actor
import dmax.scara.android.dispatch.Dispatcher
import dmax.scara.android.dispatch.Event
import dmax.scara.android.domain.mechanics.Joint

class ClickActor(
    private val dispatcher: Dispatcher
) : Actor {

    override suspend operator fun invoke() {
        val eventUp = Event(wrist = Joint(160))
        val eventDown = Event(wrist = Joint(90))
        with(dispatcher) {
            dispatch(eventUp)
            dispatch(eventDown)
            dispatch(eventUp)
        }
    }
}
