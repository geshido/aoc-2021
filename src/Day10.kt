fun main() {
    val day = "10"
    val pairs = mapOf(
        '(' to ')',
        '[' to ']',
        '{' to '}',
        '<' to '>',
    )

    fun getStack(s: String, onInvalidBrace: (Char) -> Unit): ArrayDeque<Char> {
        val stack = ArrayDeque<Char>()
        for (char in s) {
            if (pairs.keys.contains(char)) {
                stack.add(char)
                continue
            }
            if (pairs.values.contains(char)) {
                if (stack.isEmpty() || pairs[stack.last()] != char) {
                    onInvalidBrace(char)
                    break
                }
                stack.removeLast()
            }
        }
        return stack
    }

    fun part1(input: List<String>): Int {
        val points = mapOf(
            ')' to 3,
            ']' to 57,
            '}' to 1197,
            '>' to 25137,
        )
        return input.sumOf { line ->
            var p = 0
            getStack(line) { p = points[it]!! }
            p
        }
    }

    fun part2(input: List<String>): Long {
        val points = mapOf(
            ')' to 1,
            ']' to 2,
            '}' to 3,
            '>' to 4,
        )
        val scores = input.map { line ->
            var ok = true
            val stack = getStack(line) {
                ok = false
            }
            when {
                ok -> stack.reversed().map { pairs[it] }.fold(0L) { totalScore, char ->
                    totalScore * 5 + points[char]!!
                }
                else -> 0
            }
        }.filterNot { it == 0L }.sorted()

        return scores[(scores.size - 1) / 2]
    }

    val testInput = readInput("Day${day}_test")
    part1(testInput).also {
        println("Part1 test: $it")
        check(it == 26397)
    }
    part2(testInput).also {
        println("Part2 test: $it")
        check(it == 288957L)
    }

    val input = readInput("Day$day")
    println(part1(input))
    println(part2(input))
}