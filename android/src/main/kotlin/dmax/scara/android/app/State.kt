package dmax.scara.android.app

import dmax.scara.android.domain.mechanics.Joint

data class State(
    var base: Joint = Joint(angle = Config.Joints.base),
    var elbow: Joint = Joint(angle = Config.Joints.elbow),
    var wrist: Joint = Joint(angle = Config.Joints.wrist)
)
