package dmax.scara.android.actors

import dmax.scara.android.dispatch.Dispatcher
import dmax.scara.android.domain.motion.bendElbow

class BendElbowCall(
    private val dispatcher: Dispatcher
) : Call<BendElbowCall.Input> {

    override suspend operator fun invoke(input: Input) {
        val motion = bendElbow(input.angle)
        dispatcher.dispatch(motion)
    }

    data class Input(val angle: Int)
}
