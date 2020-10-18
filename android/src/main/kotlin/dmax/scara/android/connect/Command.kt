package dmax.scara.android.connect

data class Command(val servo: Servo, val angle: Int) {
    enum class Servo {
        Base, Elbow, Wrist,
    }
}
