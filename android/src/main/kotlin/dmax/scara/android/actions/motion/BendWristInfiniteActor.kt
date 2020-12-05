package dmax.scara.android.actions.motion

import dmax.scara.android.actions.Actor
import dmax.scara.android.actions.motion.utils.AngleAmplitude
import dmax.scara.android.app.misc.State
import dmax.scara.android.dispatch.Dispatcher
import dmax.scara.android.dispatch.Event
import dmax.scara.android.domain.mechanics.Joint
import kotlinx.coroutines.delay

class BendWristInfiniteActor(
    private val state: State,
    private val dispatcher: Dispatcher
) : Actor {

    override suspend operator fun invoke() {
        val amplitude = AngleAmplitude(
            step = 20,
            current = state.arm.wrist.angle,
            min = 20,
            max = 80
        )
        while (true) {
            val angle = amplitude.next()
            val event = Event(wrist = Joint(angle))
            dispatcher.dispatch(event)
            delay(1000)
        }
    }
}




