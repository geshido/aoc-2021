fun main() {
    fun countFishes(input: List<Int>, numDays: Int): Long {
        val fishes = mutableMapOf<Int, Long>()
        (0..8).forEach { fishes[it] = 0 }

        input.forEach { fish ->
            fishes[fish] = (fishes[fish] ?: 0) + 1
        }

        for (day in 1..numDays) {
            val zeroed = fishes[0] ?: 0

            for (iter in 1..8) {
                fishes[iter - 1] = (fishes[iter] ?: 0)
            }
            fishes[6] = (fishes[6] ?: 0) + zeroed
            fishes[8] = zeroed
        }

        return fishes.values.sum()
    }
    fun part1(input: List<Int>): Long {
        return countFishes(input, 80)
    }

    fun part2(input: List<Int>): Long {
        return countFishes(input, 256)
    }

    val testInput = readInput("Day06_test").first().split(",").map { it.toInt() }
    part1(testInput).also {
        println("Part1 test: $it")
        check(it == 5934L)
    }
    part2(testInput).also {
        println("Part2 test: $it")
        check(it == 26984457539L)
    }

    val input = readInput("Day06").first().split(",").map { it.toInt() }
    println("Part1: ${part1(input)}")
    println("Part2: ${part2(input)}")
}