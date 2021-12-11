fun main() {
    val day = "11"

    fun List<List<Int>>.neighbours(pos: Pair<Int, Int>): Set<Pair<Int, Int>> = pos.let { (r,c) ->
        listOf(
            r - 1 to c - 1,
            r to c - 1,
            r + 1 to c - 1,
            r - 1 to c,
            r + 1 to c,
            r - 1 to c + 1,
            r to c + 1,
            r + 1 to c + 1
        ).filter { (r,c) -> r in indices && c in first().indices }.toSet()
    }

    fun processStep(input: List<MutableList<Int>>, onFlash: (Pair<Int, Int>) -> Unit) {
        val all = input.positions()
        val seen = mutableSetOf<Pair<Int, Int>>()
        all.forEach { pos -> input[pos]++ }
        do {
            all.filter { input[it] > 9 }.forEach { pos ->
                input[pos] = 0
                if (pos !in seen) {
                    onFlash(pos)
                    seen += pos
                    input.neighbours(pos).filter { input[it] != 0 }.forEach { input[it]++ }
                }
            }
        } while (all.any { input[it] > 9 })
    }

    fun part1(input: List<MutableList<Int>>): Int {
        var flashes = 0
        for (step in 1..100) {
            processStep(input) {
                flashes++
            }
        }
        return flashes
    }

    fun part2(input: List<MutableList<Int>>): Int {
        val all = input.positions()
        var step = 0
        while (true) {
            step++
            processStep(input) {}
            if (all.all { input[it] == 0 }) return step
        }
    }

    fun List<String>.prepare() = map { line -> line.toList().map { "$it".toInt() }.toMutableList() }
    part1(readInput("Day${day}_test").prepare()).also {
        println("Part1 test: $it")
        check(it == 1656)
    }
    part2(readInput("Day${day}_test").prepare()).also {
        println("Part2 test: $it")
        check(it == 195)
    }

    println(part1(readInput("Day$day").prepare()))
    println(part2(readInput("Day$day").prepare()))
}