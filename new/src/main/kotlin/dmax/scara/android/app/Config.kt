package dmax.scara.android.app

object Config {

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

    /** Bluetooth socket config */
    object Bluetooth {
        const val address = "00:12:06:21:88:70"
        const val uuid = "00001101-0000-1000-8000-00805F9B34FB"
    }

}
