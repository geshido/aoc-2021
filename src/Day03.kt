fun main() {
    fun getCount(input: List<String>): List<Int> = input.fold(mutableListOf()) { acc, it ->
        val bits = it.split("").filterNot { it.isEmpty() }.map { it.toInt() }
        if (acc.size == 0) {
            bits.map { if (it > 0) 1 else -1 }.toMutableList()
        } else {
            bits.forEachIndexed { index, i -> acc[index] += if (i > 0) 1 else -1 }
            acc
        }
    }

    fun part1(input: List<String>): Int {
        val counts = getCount(input)
        val gamma = Integer.parseUnsignedInt(buildString {
            counts.forEach { append(if (it > 0) "1" else "0") }
        }, 2)
        val epsilon = Integer.parseUnsignedInt(buildString {
            counts.forEach { append(if (it > 0) "0" else "1") }
        }, 2)

        return gamma * epsilon
    }

    data class Iter(val input: List<String>, val counts: List<Int>) {
        fun find(bitIdx: Int, ok: Char.(count: Int) -> Boolean): String = when (input.size) {
            1 -> input.first()
            else -> {
                val filtered = input.filter { it[bitIdx].ok(counts[bitIdx]) }
                Iter(filtered, getCount(filtered)).find(bitIdx + 1, ok)
            }
        }
    }

    fun part2(input: List<String>): Int {
        val counts = getCount(input)

        fun Char.oxygenOk(count: Int): Boolean = if (count >= 0) this == '1' else this == '0'
        fun Char.co2Ok(count: Int): Boolean = if (count >= 0) this == '0' else this == '1'

        val oxygen = Iter(input, counts).find(0, Char::oxygenOk)
        val co2 = Iter(input, counts).find(0, Char::co2Ok)

        return Integer.parseUnsignedInt(oxygen, 2) * Integer.parseUnsignedInt(co2, 2)
    }

    val testInput = readInput("Day03_test")

    val out1 = part1(testInput)
    println("Test1: $out1")
    check(out1 == 198)

    val out2= part2(testInput)
    println("Test2: $out2")
    check(out2 == 230)

    val input = readInput("Day03")
    println("Part 1: ${part1(input)}")
    println("Part 2: ${part2(input)}")
}