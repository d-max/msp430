package dmax.scara.android.actors

import dmax.scara.android.dispatch.Dispatcher
import dmax.scara.android.domain.motion.bendBase

class BendBaseCall(
    private val dispatcher: Dispatcher
) : Call<BendBaseCall.Input> {

    override suspend operator fun invoke(input: Input) {
        val motion = bendBase(input.angle)
        dispatcher.dispatch(motion)
    }

    data class Input(val angle: Int)
}
