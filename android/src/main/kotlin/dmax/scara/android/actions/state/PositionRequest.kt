package dmax.scara.android.actions.state

import dmax.scara.android.actions.Request
import dmax.scara.android.app.misc.State
import dmax.scara.android.domain.math.AngleCondition
import dmax.scara.android.domain.math.calculateCoordinates

class PositionRequest(
    private val state: State
) : Request<PositionRequest.Output> {

    override suspend operator fun invoke(): Output {
        val (base, elbow, _, femur, tibia) = state.arm
        val condition = AngleCondition(
            alpha = base.angle,
            beta = elbow.angle,
            a = femur.length,
            b = tibia.length
        )
        val (x, y) = calculateCoordinates(condition)
        return Output(x, y)
    }

    data class Output(val x: Int, val y: Int)
}
