typealias Field = Array<IntArray>

fun main() {
    data class Point(val x: Int, val y: Int) {
        fun stepTo(to: Point): IntArray {
            val d = IntArray(2)
            d[0] = when {
                x < to.x -> 1
                x > to.x -> -1
                else -> 0
            }
            d[1] = when {
                y < to.y -> 1
                y > to.y -> -1
                else -> 0
            }
            return d
        }

        fun mark(field: Field) = field[y][x]++
    }

    data class Line(val p1: Point, val p2: Point) {
        fun mark(field: Field) {
            var cur = p1
            val (dx, dy) = p1.stepTo(p2)
            while (cur != p2) {
                cur.mark(field)
                cur = Point(cur.x + dx, cur.y + dy)
            }
            p2.mark(field)
        }
    }

    fun buildLines(input: List<String>): List<Line> = input.map { s ->
        val (x1, y1, x2, y2) = s.split(" -> ").map { it.split(",") }.flatten().map { it.toInt() }
        Line(Point(x1, y1), Point(x2, y2))
    }

    fun buildField(input: List<Line>): Field {
        val maxX = input.maxOf { l -> listOf(l.p1, l.p2).maxOf { it.x } }
        val maxY = input.maxOf { l -> listOf(l.p1, l.p2).maxOf { it.y } }
        return Array(maxY + 1) { IntArray(maxX + 1) }
    }

    fun part1(input: List<Line>): Int {
        val field = buildField(input)
        val hAndV = input.filter { it.p1.stepTo(it.p2).contains(0) }
        hAndV.forEach { it.mark(field) }
        return field.sumOf { row -> row.count { it > 1 } }
    }

    fun part2(input: List<Line>): Int {
        val field = buildField(input)
        input.forEach { it.mark(field) }
        return field.sumOf { row -> row.count { it > 1 } }
    }

    val testInput = buildLines(readInput("Day05_test"))
    part1(testInput).also {
        println("Part1 test: $it")
        check(it == 5)
    }
    part2(testInput).also {
        println("Part2 test: $it")
        check(it == 12)
    }

    val input = buildLines(readInput("Day05"))
    println("Part1: ${part1(input)}")
    println("Part2: ${part2(input)}")
}