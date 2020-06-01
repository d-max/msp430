package dmax.scara.android.domain.math

import kotlin.math.acos
import kotlin.math.atan
import kotlin.math.pow
import kotlin.math.sqrt

data class Condition<T : Number>(
    val a: T,
    val b: T,
    val x: T,
    val y: T
)

data class Result<T : Number>(
    val alpha: T,
    val beta: T
)

fun calculateAngles(condition: Condition<Int>): Result<Int> {
    val (a, b, x, y) = condition.toDouble()
    val l = sqrt(x.pow(2) + y.pow(2))
    val alpha1 = atan(y / x)
    val alpha2 = acos((l.pow(2) + a.pow(2) - b.pow(2)) / 2 * a * l)
    val beta = acos((a.pow(2) + b.pow(2) - l.pow(2)) / 2 * a * b)
    return Result(alpha1 + alpha2, beta).toInt()
}

private fun <T : Number> Condition<T>.toDouble() = Condition(
    a.toDouble(),
    b.toDouble(),
    x.toDouble(),
    y.toDouble()
)

private fun <T : Number> Result<T>.toInt() = Result(
    alpha.toInt(),
    beta.toInt()
)
