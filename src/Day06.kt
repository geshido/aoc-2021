fun main() {
    fun part1(input: List<String>): Int {
        val fishes = input.first().split(",").map { it.toInt() }.toMutableList()
        for (day in 1..80) {
            val newFishes = mutableListOf<Int>()
            for (idx in fishes.indices) {
                when {
                    fishes[idx] == 0 -> {
                        newFishes.add(8)
                        fishes[idx] = 6
                    }
                    else -> fishes[idx]--
                }
            }
            fishes.addAll(newFishes)
        }
        return fishes.size
    }

    fun part2(input: List<String>): Long {
        val fishes = mutableMapOf<Int, Long>()
        (0..8).forEach { fishes[it] = 0 }

        input.first().split(",").map { it.toInt() }.forEach { fish ->
            fishes[fish] = (fishes[fish] ?: 0) + 1
        }

        for (day in 1..256) {
            val zeroed = fishes[0] ?: 0

            for (iter in 1..8) {
                fishes[iter - 1] = (fishes[iter] ?: 0)
            }
            fishes[6] = (fishes[6] ?: 0) + zeroed
            fishes[8] = zeroed
        }

        return fishes.values.sum()
    }

    val testInput = readInput("Day06_test")
    part1(testInput).also {
        println("Part1 test: $it")
        check(it == 5934)
    }
    part2(testInput).also {
        println("Part2 test: $it")
        check(it == 26984457539)
    }

    val input = readInput("Day06")
    println("Part1: ${part1(input)}")
    println("Part2: ${part2(input)}")
}