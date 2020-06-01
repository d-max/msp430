package dmax.scara.android.domain.motion

import dmax.scara.android.domain.mechanics.Arm
import dmax.scara.android.domain.mechanics.Joint

fun Arm.bendBase(angle: Int) : Motion {
    return Motion(base = Joint(angle))
}

fun Arm.bendElbow(angle: Int) : Motion {
    return Motion(elbow = Joint(angle))
}

fun Arm.bendWrist(angle: Int) : Motion {
    return Motion(wrist = Joint(angle))
}
