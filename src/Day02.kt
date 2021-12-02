import java.lang.Exception

private enum class Direction {
    Forward,
    Down,
    Up,
}

private data class Instruction(val d: Direction, val num: Int)

fun main() {

    fun part1(input: List<Instruction>): Int {
        var depth = 0
        var length = 0
        input.forEach {
            when (it.d) {
                Direction.Forward -> length += it.num
                Direction.Down -> depth += it.num
                Direction.Up -> depth -= it.num
            }
        }
        return depth * length
    }

    fun part2(input: List<Instruction>): Int {
        var depth = 0
        var length = 0
        var aim = 0
        input.forEach {
            when (it.d) {
                Direction.Forward -> {
                    length += it.num
                    depth += aim * it.num
                }
                Direction.Down -> aim += it.num
                Direction.Up -> aim -= it.num
            }
        }
        return depth * length
    }

    fun List<String>.instructions(): List<Instruction> = map { it.split(" ") }.map { (dir, n) ->
        val num = n.toInt()
        when (dir) {
            "forward" -> Instruction(Direction.Forward, num)
            "down" -> Instruction(Direction.Down, num)
            "up" -> Instruction(Direction.Up, num)
            else -> throw Exception("invalid instruction $dir")
        }
    }


    val testInput = readInput("Day02_test").instructions()
    check(part1(testInput) == 150)
    check(part2(testInput) == 900)

    val input = readInput("Day02").instructions()
    println(part1(input))
    println(part2(input))
}