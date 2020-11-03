package dmax.scara.android.actions.motion

import dmax.scara.android.actions.Actor
import dmax.scara.android.actions.motion.utils.AngleAmplitude
import dmax.scara.android.app.misc.State
import dmax.scara.android.dispatch.Dispatcher
import dmax.scara.android.dispatch.Event
import dmax.scara.android.domain.mechanics.Joint
import kotlinx.coroutines.delay

class BendBaseInfiniteActor(
    private val state: State,
    private val dispatcher: Dispatcher
) : Actor {

    override suspend operator fun invoke() {
        val amplitude = AngleAmplitude(
            step = 10,
            current = state.arm.base.angle,
            max = 160
        )
        while (true) {
            val angle = amplitude.next()
            val event = Event(base = Joint(angle))
            dispatcher.dispatch(event)
            delay(500)
        }
    }
}




