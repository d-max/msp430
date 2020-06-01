package dmax.scara.android.app

import dmax.scara.android.domain.mechanics.Arm
import dmax.scara.android.domain.mechanics.Bone
import dmax.scara.android.domain.mechanics.Joint

object Config {

    object Speed {
        const val angle = 3
        const val delay = 30L
    }

    /** Mapping joint to servo id */
    object Servo {
        const val base = 2
        const val elbow = 1
        const val wrist = 0
    }

    /** Size of legs in centimeters */
    object Segment {
        const val femur = 5
        const val tibia = 5
    }

    /** Default angles of joints in degrees */
    object Joints {
        const val base = 90
        const val elbow = 90
        const val wrist = 90
    }

    /** Bluetooth socket config */
    object Bluetooth {
        const val address = "00:12:06:21:88:70"
        const val uuid = "00001101-0000-1000-8000-00805F9B34FB"
    }

    fun createDefaultArm() = Arm(
        femur = Bone(length = Segment.femur),
        tibia = Bone(length = Segment.tibia),
        fibula = Bone(length = Segment.femur),
        base = Joint(angle = Joints.base),
        elbow = Joint(angle = Joints.elbow),
        wrist = Joint(angle = Joints.wrist)
    )
}
