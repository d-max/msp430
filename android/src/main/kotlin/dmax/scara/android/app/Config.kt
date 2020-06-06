package dmax.scara.android.app

object Config {

    object Speed {
        const val angle = 3
        const val delay = 50L
    }

    /** Mapping joint to servo id */
    object Servo {
        const val base = 2
        const val elbow = 1
        const val wrist = 0
    }

    /** Size of legs in centimeters */
    object Segments {
        const val femur = 9
        const val tibia = 9
    }

    /** Shift of (0,0) in centimeters */
    object Shift {
        const val x = 8
        const val y = 0
    }

    /** Default angles of joints in degrees */
    object Joints {
        const val base = 100
        const val elbow = 50
        const val wrist = 100
    }

    /** Bluetooth socket config */
    object Bluetooth {
        const val address = "00:12:06:21:88:70"
        const val uuid = "00001101-0000-1000-8000-00805F9B34FB"
    }
}
