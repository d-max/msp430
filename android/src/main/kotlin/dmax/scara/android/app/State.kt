package dmax.scara.android.app

import dmax.scara.android.domain.mechanics.Arm
import dmax.scara.android.domain.mechanics.Joint

class State(arm: Arm) {

    var arm: Arm = arm
        private set

    var base: Joint
        get() = arm.base
        set(value) { arm = arm.copy(base = value) }

    var elbow: Joint
        get() = arm.elbow
        set(value) { arm = arm.copy(elbow = value) }

    var wrist: Joint
        get() = arm.wrist
        set(value) { arm = arm.copy(wrist = value) }
}
