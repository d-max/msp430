package dmax.scara.android.actors

import dmax.scara.android.dispatch.Dispatcher
import dmax.scara.android.domain.motion.bendWrist

class BendWristCall(
    private val dispatcher: Dispatcher
) : Call<BendWristCall.Input> {

    override suspend operator fun invoke(input: Input) {
        val motion = bendWrist(input.angle)
        dispatcher.dispatch(motion)
    }

    data class Input(val angle: Int)
}
