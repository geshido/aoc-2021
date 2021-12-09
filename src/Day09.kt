fun main() {
    val day = "09"

    fun part1(input: List<List<Int>>): Int {
        return input.minimums().sumOf { (y, x) -> input[y][x] + 1 }
    }

    fun part2(input: List<List<Int>>): Int {
        val minimums = input.minimums()
        val basins = minimums.map { min ->
            val basin = mutableSetOf<Pair<Int, Int>>()
            input.basin(min, basin)
            basin
        }
        return basins.sortedByDescending { it.count() }.take(3).fold(1) { res, b ->
            res * b.count()
        }
    }

    val testInput = readInput("Day${day}_test").prepare()
    part1(testInput).also {
        println("Part1 test: $it")
        check(it == 15)
    }
    part2(testInput).also {
        println("Part2 test: $it")
        check(it == 1134)
    }

    val input = readInput("Day$day").prepare()
    println(part1(input))
    println(part2(input))

}

private fun List<String>.prepare(): List<List<Int>> =
    map { row -> row.split("").filterNot { it.isEmpty() }.map { it.toInt() } }

private fun List<List<Int>>.neighbours(rowIndex: Int, index: Int): Set<Pair<Int, Int>> {
    val s = mutableSetOf<Pair<Int, Int>>()
    if (rowIndex - 1 >= 0) s += rowIndex - 1 to index
    if (rowIndex + 1 < this.size) s += rowIndex + 1 to index
    if (index - 1 >= 0) s += rowIndex to index - 1
    if (index + 1 < this[rowIndex].size) s += rowIndex to index + 1

    return s
}

private fun List<List<Int>>.minimums(): Set<Pair<Int, Int>> {
    val s = mutableSetOf<Pair<Int, Int>>()
    for (rowIndex in indices) {
        for (index in this[rowIndex].indices) {
            val neighbours = neighbours(rowIndex, index)
            if (neighbours.all { (y, x) -> this[y][x] > this[rowIndex][index] }) {
                s += rowIndex to index
            }
        }
    }
    return s
}

private fun List<List<Int>>.basin(pos: Pair<Int, Int>, result: MutableSet<Pair<Int, Int>>) {
    if (result.contains(pos) || this[pos.first][pos.second] == 9) {
        return
    }

    result += pos
    this.neighbours(pos.first, pos.second).forEach { this.basin(it, result) }
}

