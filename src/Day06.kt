fun main() {
    fun countFishes(input: List<Int>, numDays: Int): Long {
        val fishes = LongArray(9) { 0 }
        (0..8).forEach { fishes[it] = 0 }

        input.forEach { fish ->
            fishes[fish]++
        }

        for (day in 1..numDays) {
            val zeroed = fishes[0]

            for (iter in 1..8) {
                fishes[iter - 1] = fishes[iter]
            }
            fishes[6] = fishes[6] + zeroed
            fishes[8] = zeroed
        }

        return fishes.sum()
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