enum class Day13FoldType {
    FoldByX,
    FoldByY,
}

fun main() {
    val day = "13"

    data class Fold(val type: Day13FoldType, val line: Int)
    data class Input(val coords: List<Pair<Int, Int>>, val folds: List<Fold>)

    fun prepare(input: List<String>): Input {
        val coords = input.filter { it.contains(",") }.map { val (x, y) = it.split(","); x.toInt() to y.toInt() }
        val folds = input.filter { it.startsWith("fold") }.map {
            val (_, _, data) = it.split(" ")
            val (xy, num) = data.split("=")
            Fold(
                when (xy) {
                    "x" -> Day13FoldType.FoldByX
                    "y" -> Day13FoldType.FoldByY
                    else -> throw Exception("unknown fold type")
                }, num.toInt()
            )
        }
        return Input(coords, folds)
    }

    fun fold(f: Fold, pos: Pair<Int, Int>): Pair<Int, Int> = when (f.type) {
        Day13FoldType.FoldByY -> when {
            pos.second < f.line -> pos
            else -> pos.first to f.line - (pos.second - f.line)
        }
        Day13FoldType.FoldByX -> when {
            pos.first < f.line -> pos
            else -> f.line - (pos.first - f.line) to pos.second
        }
    }

    fun part1(input: List<String>): Int {
        val prepared = prepare(input)
        val f = prepared.folds.first()

        return prepared.coords.map { fold(f, it) }.toSet().size
    }

    fun part2(input: List<String>): Int {
        val prepared = prepare(input)
        val folded = prepared.folds.fold(prepared.coords.toSet()) { s, f ->
            s.map { fold(f, it) }.toSet()
        }
        val maxX = folded.maxOf { it.first }
        val maxY = folded.maxOf { it.second }
        val field = Array(maxY + 1) { CharArray(maxX + 1) { ' ' } }
        folded.forEach { field[it.second][it.first] = '#' }
        println(field.joinToString("\n") { it.joinToString("") })
        return 0
    }

    val testInput = readInput("Day${day}_test")
    part1(testInput).also {
        println("Part1 test: $it")
        check(it == 17)
    }
    part2(testInput).also {
        println("Part2 test: $it")
        check(it == 0)
    }

    val input = readInput("Day$day")
    println(part1(input))
    println(part2(input))
}