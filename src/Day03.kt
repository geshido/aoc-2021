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
        println("gamma: $gamma")
        println("epsilon: $epsilon")

        return gamma * epsilon
    }


    fun part2(input: List<String>): Int {
        val counts = getCount(input)

        fun Char.oxygenOk(count: Int): Boolean = if (count >= 0) this == '1' else this == '0'
        fun Char.co2Ok(count: Int): Boolean = if (count >= 0) this == '0' else this == '1'

        data class Iter(val input: List<String>, val counts: List<Int>)
        fun find(initial: Iter, ok: Char.(count: Int) -> Boolean): List<String> =
            initial.counts.foldIndexed(initial) { idx, iter, _ ->
                if (iter.input.size > 1) {
                    val newAcc = iter.input.filter { it[idx].ok(iter.counts[idx]) }
                    Iter(newAcc, getCount(newAcc))
                } else {
                    iter
                }
            }.input

        val oxygen = find(Iter(input, counts), Char::oxygenOk)
        val co2 = find(Iter(input, counts), Char::co2Ok)
        println(oxygen)
        println(co2)

        return Integer.parseUnsignedInt(oxygen.first(), 2) * Integer.parseUnsignedInt(co2.first(), 2)
    }

    val testInput = readInput("Day03_test")
//    check(part1(testInput) == 198)
    val out = part2(testInput)
    println(out)
    check(out == 230)

    val input = readInput("Day03")
//    println(part1(input))
    println("Part 2: ${part2(input)}")
}