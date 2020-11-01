package dmax.scara.android.app

import dmax.scara.android.connect.Command.Servo
import dmax.scara.android.connect.Command.Servo.Base
import dmax.scara.android.connect.Command.Servo.Elbow
import dmax.scara.android.connect.Command.Servo.Wrist
import dmax.scara.android.connect.bluetooth.BluetoothConnector
import dmax.scara.android.dispatch.impl.ProgressiveDispatcher
import dmax.scara.android.domain.mechanics.Arm
import dmax.scara.android.domain.mechanics.Bone
import dmax.scara.android.domain.mechanics.Joint

object Config {

    /** Mock connector logging delay */
    fun loggingDelay() = 1000L
    fun operationalDelay() = 500L

    /** Bluetooth socket config */
    fun socketConfig() = BluetoothConnector.SocketConfig(
        address = "00:12:06:21:88:70",
        uuid = "00001101-0000-1000-8000-00805F9B34FB"
    )

    /** Mapping joint to servo id */
    fun servoPortMapper(servo: Servo): Byte = when(servo) {
        Base -> 2
        Elbow -> 1
        Wrist -> 0
    }

    /** Progressive dispatcher step */
    fun speedConfig() = ProgressiveDispatcher.SpeedConfig(
        stepAngle = 3,
        stepDelay = 50L
    )

    /** Default arm configuration */
    fun defaultArm() = Arm(
        base = Joint(angle = 100),
        elbow = Joint(angle = 50),
        wrist = Joint(angle = 50),
        femur = Bone(length = 9),
        tibia = Bone(length = 9),
    )
}
