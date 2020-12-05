package dmax.scara.android.domain.math

import kotlin.math.acos
import kotlin.math.atan
import kotlin.math.cos
import kotlin.math.pow
import kotlin.math.sin
import kotlin.math.sqrt

data class PositionCondition(
    val a: Int,
    val b: Int,
    val x: Int,
    val y: Int
)

data class AngleCondition(
    val a: Int,
    val b: Int,
    val alpha: Int,
    val beta: Int
)

data class AngleResult(
    val alpha: Int,
    val beta: Int
)

data class PositionResult(
    val x: Int,
    val y: Int
)

fun calculateAngles(condition: PositionCondition): AngleResult {
    val (a, b, x, y) = condition.toDouble()
    val l = sqrt(x.pow(2) + y.pow(2))
    val alpha1 = atan(y / x).toAngles()
    val alpha2 = acos((l.pow(2) + a.pow(2) - b.pow(2)) / (2 * a * l)).toAngles()
    val beta = acos((a.pow(2) + b.pow(2) - l.pow(2)) / (2 * a * b)).toAngles()
    println("a=$a b=$b x=$x y=$y :: l=$l alpha1=$alpha1 alpha2=$alpha2 beta=$beta")
    return angleResult(alpha1 + alpha2, beta)
}

fun calculateCoordinates(condition: AngleCondition): PositionResult {
    val (a, b, alpha, beta) = condition.toDouble()
    val l = sqrt(a.pow(2) + b.pow(2) - 2 * a * b * cos(beta))
    val alpha2 = acos((l.pow(2) + a.pow(2) - b.pow(2)) / (2 * a * l)).toAngles()
    val alpha1 = alpha - alpha2
    val x = l * sin(alpha1)
    val y = l * cos(alpha1)
    println("a=$a b=$b alpha=$alpha beta=$beta :: l=$l alpha1=$alpha1 alpha2=$alpha2 x=$x y=$y")
    return positionResult(x, y)
}

private data class DoubleTuple(
    val one: Double,
    val two: Double,
    val three: Double,
    val four: Double
)

private fun PositionCondition.toDouble() = DoubleTuple(
    a.toDouble(),
    b.toDouble(),
    x.toDouble(),
    y.toDouble()
)

private fun AngleCondition.toDouble() = DoubleTuple(
    a.toDouble(),
    b.toDouble(),
    alpha.toDouble(),
    beta.toDouble()
)

private fun angleResult(alpha: Double, beta: Double) = AngleResult(
    alpha.toInt(),
    beta.toInt()
)

private fun positionResult(x: Double, y: Double) = PositionResult(
    x.toInt(),
    y.toInt()
)

private fun Pair<Double, Double>.toInt() = PositionResult(
    first.toInt(),
    second.toInt()
)

private fun Double.toAngles(): Double = this * 180 / 3.141592654
