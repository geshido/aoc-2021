fun main() {
    val day = "XX"

    fun part1(input: List<String>): Int {
        return 0
    }
    fun part2(input: List<String>): Int {
        return 0
    }

    val testInput = readInput("Day${day}_test")
    part1(testInput).also {
        println("Part1 test: $it")
        check(it == 0)
    }
    part2(testInput).also {
        println("Part2 test: $it")
        check(it == 0)
    }

    val input = readInput("Day$day")
    println(part1(input))
    println(part2(input))
}