package dmax.scara.android.actions.motion

import dmax.scara.android.actions.Actor
import dmax.scara.android.actions.motion.utils.AngleAmplitude
import dmax.scara.android.app.misc.State
import dmax.scara.android.dispatch.Dispatcher
import dmax.scara.android.dispatch.Event
import dmax.scara.android.domain.mechanics.Joint

class BendWristInfiniteActor(
    private val state: State,
    private val dispatcher: Dispatcher
) : Actor {

    override suspend operator fun invoke() {
        val amplitude = AngleAmplitude(
            step = 10,
            current = state.arm.wrist.angle,
            max = 90
        )
        while (true) {
            val angle = amplitude.next()
            val event = Event(wrist = Joint(angle))
            dispatcher.dispatch(event)
        }
    }
}




