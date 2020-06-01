package dmax.scara.android.actors

import dmax.scara.android.app.State
import dmax.scara.android.dispatch.Dispatcher
import dmax.scara.android.domain.motion.move

class MoveCall(
    private val state: State,
    private val dispatcher: Dispatcher
) : Call<MoveCall.Input> {

    override suspend operator fun invoke(input: Input) {
        val (x, y) = input
        val motion = state.arm.move(x, y)
        dispatcher.dispatch(motion)
    }

    data class Input(val x: Int, val y: Int)
}
