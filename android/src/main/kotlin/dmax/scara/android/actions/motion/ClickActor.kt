package dmax.scara.android.actions.motion

import dmax.scara.android.actions.Actor
import dmax.scara.android.dispatch.Dispatcher
import dmax.scara.android.dispatch.Event
import dmax.scara.android.domain.mechanics.Joint

class ClickActor(
    private val dispatcher: Dispatcher
) : Actor {

    override suspend operator fun invoke() {
        val eventUp = Event(wrist = Joint(0))
        val eventDown = Event(wrist = Joint(180))
        with(dispatcher) {
            dispatch(eventUp)
            dispatch(eventDown)
            dispatch(eventUp)
        }
    }
}
