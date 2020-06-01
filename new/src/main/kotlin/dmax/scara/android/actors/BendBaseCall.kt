package dmax.scara.android.actors

import dmax.scara.android.app.State
import dmax.scara.android.dispatch.Dispatcher
import dmax.scara.android.domain.motion.bendBase

class BendBaseCall(
    private val state: State,
    private val dispatcher: Dispatcher
) : Call<BendBaseCall.Input> {

    override suspend operator fun invoke(input: Input) {
        val motion = state.arm.bendBase(input.angle)
        dispatcher.dispatch(motion)
    }

    data class Input(val angle: Int)
}
