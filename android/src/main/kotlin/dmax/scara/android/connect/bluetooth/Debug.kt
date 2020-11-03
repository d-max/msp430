package dmax.scara.android.connect.bluetooth

class Appender {

    data class Channel(
        val onLow: Int,
        val onHigh: Int,
        val offLow: Int,
        val offHigh: Int
    )

    private val list = mutableListOf<Int>()
    private var previous = listOf<Channel>()

    fun append(data: Int) {
        list.add(data)
        if (list.size == 12) {
            val current = list.chunked(4).map { Channel(it[0], it[1], it[2], it[3]) }
            findDifference(current)
            previous = current
            list.clear()
        }
    }

    private fun findDifference(current: List<Channel>) {
        if (previous.isNotEmpty()) {
            current.forEachIndexed { index, it ->
                val prev = previous[index]
                if (it != prev) {
                    println("! $index : $it != $prev")
                }
            }
        }
    }
}

fun printDutyCycles(port: Byte, angle: Int) {
    val min = 103
    val max = 492
    val step = 2
    val duty = min + angle * step
    val low = duty and 0x0f
    val high = duty ushr 4
    buildString {
        append(">")
        append(" $port")
        append(" $angle")
        append(" $duty")
        append(" ")
        append(duty.toBinary(8))
        append(" = ")
        append(high.toBinary(4))
        append(" ")
        append(low.toBinary(4))
    }.let(::println)
}

private fun Int.toBinary(len: Int): String {
    return String.format("%" + len + "s", toString(2).drop(1)).replace(" ".toRegex(), "0")
}

