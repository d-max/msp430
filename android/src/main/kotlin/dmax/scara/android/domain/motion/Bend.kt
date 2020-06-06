package dmax.scara.android.domain.motion

import dmax.scara.android.domain.mechanics.Joint

fun bendBase(angle: Int) : Motion {
    return Motion(base = Joint(angle))
}

fun bendElbow(angle: Int) : Motion {
    return Motion(elbow = Joint(angle))
}

fun bendWrist(angle: Int) : Motion {
    return Motion(wrist = Joint(angle))
}
