package dmax.scara.android.connect

import dmax.scara.android.app.Config

enum class Servo(val id: Byte) {
    Base(id = Config.Servo.base.toByte()),
    Elbow(id = Config.Servo.elbow.toByte()),
    Wrist(id = Config.Servo.wrist.toByte()),
}

data class Command(val servo: Servo, val angle: Int)
