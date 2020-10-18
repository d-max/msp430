package dmax.scara.android.actions.motion

import dmax.scara.android.actions.Call
import dmax.scara.android.dispatch.Dispatcher
import dmax.scara.android.dispatch.Event
import dmax.scara.android.domain.mechanics.Joint

class BendWristCall(
    private val dispatcher: Dispatcher
) : Call<BendWristCall.Input> {

    override suspend operator fun invoke(input: Input) {
        val event = Event(wrist = Joint(input.angle))
        dispatcher.dispatch(event)
    }

    data class Input(val angle: Int)
}
