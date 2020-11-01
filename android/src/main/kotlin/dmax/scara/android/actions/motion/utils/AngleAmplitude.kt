package dmax.scara.android.actions.motion.utils

class AngleAmplitude(
    private val min: Int = 0,
    private val max: Int = 180,
    private val step: Int,
    current: Int
) {

    var angle = current
    var up: Boolean = true

    fun next(): Int {
        if (angle + step >= max) up = false
        if (angle - step <= min) up = true
        angle = if (up) angle + step else angle - step
        return angle
    }

}
