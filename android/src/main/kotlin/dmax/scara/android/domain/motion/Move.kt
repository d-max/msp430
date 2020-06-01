package dmax.scara.android.domain.motion

import dmax.scara.android.domain.math.Condition
import dmax.scara.android.domain.math.calculateAngles
import dmax.scara.android.domain.mechanics.Arm
import dmax.scara.android.domain.mechanics.Joint

fun Arm.move(x: Int, y: Int) : Motion {
    val condition = Condition(x = x, y = y, a = femur.length, b = tibia.length)
    val result = calculateAngles(condition)
    return Motion(base = Joint(result.alpha), elbow = Joint(result.beta))
}
