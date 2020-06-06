package dmax.scara.android.domain.motion

import dmax.scara.android.app.Config
import dmax.scara.android.domain.math.Condition
import dmax.scara.android.domain.math.calculateAngles
import dmax.scara.android.domain.mechanics.Joint

fun move(x: Int, y: Int) : Motion {
    val femur = Config.Segments.femur
    val tibia = Config.Segments.tibia
    val shiftX = Config.Shift.x
    val shiftY = Config.Shift.y

    val condition = Condition(x = x + shiftX, y = y + shiftY, a = femur, b = tibia)
    val result = calculateAngles(condition)

    return Motion(base = Joint(result.alpha), elbow = Joint(result.beta))
}
