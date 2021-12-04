fun main() {
    val seen = -1000
    val splitter = """\s+""".toRegex()

    data class Input(val digits: List<Int>, val cards: List<List<MutableList<Int>>>)

    fun processInput(input: String): Input {
        val parts = input.split("\n\n")
        val digits = parts[0].trim().split(",").map { it.toInt() }
        val cards = parts.drop(1).map { part ->
            part.lines().map { line -> line.trim().split(splitter).map { it.toInt() }.toMutableList() }
        }
        return Input(digits, cards)
    }

    fun List<MutableList<Int>>.mark(turn: Int) = forEach { line ->
        line.replaceAll { if (it == turn) seen else it }
    }

    fun List<MutableList<Int>>.win(): Boolean = when {
        any { row -> row.all { it == seen } } -> true
        first().indices.any { idx -> all { it[idx] == seen } } -> true
        else -> false
    }
    fun List<MutableList<Int>>.sumUnseen() = flatten().filterNot { it == seen }.sum()

    fun part1(input: String): Int {
        val (digits, cards) = processInput(input)

        for (turn in digits) {
            cards.forEach { card ->
                card.mark(turn)
                if (card.win()) {
                    return turn * card.sumUnseen()
                }
            }
        }
        return 0
    }

    fun part2(input: String): Int {
        val (digits, cards) = processInput(input)

        var cardsInGame = cards
        for (turn in digits) {
            for (card in cardsInGame) {
                card.mark(turn)
            }
            if (cardsInGame.size == 1 && cardsInGame.first().win()) {
                return turn * cardsInGame.first().sumUnseen()
            }
            cardsInGame = cards.filterNot { it.win() }
        }
        return 0
    }

    val testInput = readText("Day04_test")
    val out1 = part1(testInput)
    println("Test part1: $out1")
    check(out1 == 4512)
    val out2 = part2(testInput)
    println("Test part2: $out2")
    check(out2 == 1924)

    val input = readText("Day04")
    println("Part1: ${part1(input)}")
    println("Part2: ${part2(input)}")
}