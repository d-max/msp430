package dmax.scara.android.actions.motion

import dmax.scara.android.actions.Call
import dmax.scara.android.app.misc.State
import dmax.scara.android.dispatch.Dispatcher
import dmax.scara.android.dispatch.Event
import dmax.scara.android.domain.math.PositionCondition
import dmax.scara.android.domain.math.calculateAngles
import dmax.scara.android.domain.mechanics.Joint

class MoveCall(
    private val state: State,
    private val dispatcher: Dispatcher
) : Call<MoveCall.Input> {

    override suspend operator fun invoke(input: Input) {
        val (x, y) = input
        val (_, _, _, femur, tibia) = state.arm
        val condition = PositionCondition(
            x = x,
            y = y,
            a = femur.length,
            b = tibia.length
        )
        val result = calculateAngles(condition)
        val event = Event(base = Joint(result.alpha), elbow = Joint(result.beta))
        dispatcher.dispatch(event)
    }

    data class Input(val x: Int, val y: Int)
}
