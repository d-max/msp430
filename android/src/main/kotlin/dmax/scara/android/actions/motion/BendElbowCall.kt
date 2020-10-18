package dmax.scara.android.actions.motion

import dmax.scara.android.actions.Call
import dmax.scara.android.dispatch.Dispatcher
import dmax.scara.android.dispatch.Event
import dmax.scara.android.domain.mechanics.Joint

class BendElbowCall(
    private val dispatcher: Dispatcher
) : Call<BendElbowCall.Input> {

    override suspend operator fun invoke(input: Input) {
        val event = Event(elbow = Joint(input.angle))
        dispatcher.dispatch(event)
    }

    data class Input(val angle: Int)
}
