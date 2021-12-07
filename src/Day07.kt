import kotlin.math.abs

fun main() {
    fun cheapestTarget(crabs: List<Int>, fuel: Int.(target: Int) -> Int): Int {
        val targets = IntArray(crabs.size)
        crabs.indices.forEach { target ->
            targets[target] = crabs.sumOf { it.fuel(target) }
        }
        return targets.minOf { it }
    }

    fun part1(positions: List<Int>): Int = cheapestTarget(positions) { abs(this - it) }
    fun part2(positions: List<Int>): Int = cheapestTarget(positions) {
        val n = abs(this - it)
        n * (n + 1) / 2
    }

    val testInput = readInput("Day07_test").first().split(",").map { it.toInt() }
    part1(testInput).also {
        println("Part1 test: $it")
        check(it == 37)
    }
    part2(testInput).also {
        println("Part2 test: $it")
        check(it == 168)
    }

    val input = readInput("Day07").first().split(",").map { it.toInt() }
    println("Part1: ${part1(input)}")
    println("Part2: ${part2(input)}")
}