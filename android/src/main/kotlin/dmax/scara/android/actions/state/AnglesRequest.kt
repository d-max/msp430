package dmax.scara.android.actions.state

import dmax.scara.android.actions.Request
import dmax.scara.android.app.misc.State

class AnglesRequest(
    private val state: State
) : Request<AnglesRequest.Output> {

    override suspend operator fun invoke(): Output {
        val (base, elbow, wrist) = state.arm
        return Output(base.angle, elbow.angle, wrist.angle)
    }

    data class Output(val base: Int, val elbow: Int, val wrist: Int)
}
