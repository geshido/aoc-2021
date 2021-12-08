import java.lang.Exception

private enum class Pos {
    Top,
    TopLeft,
    TopRight,
    Middle,
    BottomLeft,
    BottomRight,
    Bottom,
}

private data class Entry(val digits: List<String>, val output: List<String>)

private fun String.toCharSet() = toCharArray().toSet()
private fun List<String>.toItems() = map {
    val (digits, output) = it.trim().split(" | ")
    Entry(digits.split(" "), output.split(" "))
}

private typealias Word = MutableMap<Char, Pos?>

private fun Word.chars() = keys.toSet()
private fun Word.unknownChars() = filter { (_, pos) -> pos == null }.map { (char) -> char }.toSet()
private fun Word.countUnknown() = values.count { it == null }
private fun List<Word>.setPos(c: Char, p: Pos) = forEach {
    if (it.containsKey(c)) {
        it[c] = p
    }
}


fun main() {
    val day = "08"

    fun part1(input: List<Entry>): Int {
        val uniqSegmentCounts = listOf(2, 3, 4, 7)
        return input.sumOf { item ->
            item.output.count { it.length in uniqSegmentCounts }
        }
    }

    fun part2(input: List<Entry>): Int {
        val segmentPositions = listOf(
            setOf(Pos.Top, Pos.TopRight, Pos.BottomRight, Pos.Bottom, Pos.BottomLeft, Pos.TopLeft),//0
            setOf(Pos.TopRight, Pos.BottomRight),//1
            setOf(Pos.Top, Pos.TopRight, Pos.Middle, Pos.BottomLeft, Pos.Bottom),//2
            setOf(Pos.Top, Pos.TopRight, Pos.Middle, Pos.BottomRight, Pos.Bottom),//3
            setOf(Pos.TopLeft, Pos.Middle, Pos.TopRight, Pos.BottomRight),//4
            setOf(Pos.Top, Pos.TopLeft, Pos.Middle, Pos.BottomRight, Pos.Bottom),//5
            setOf(Pos.Top, Pos.TopLeft, Pos.Middle, Pos.BottomLeft, Pos.Bottom, Pos.BottomRight),//6
            setOf(Pos.Top, Pos.TopRight, Pos.BottomRight), //7
            setOf(Pos.Top, Pos.TopRight, Pos.TopLeft, Pos.Middle, Pos.BottomRight, Pos.BottomLeft, Pos.Bottom), //8
            setOf(Pos.Top, Pos.TopRight, Pos.TopLeft, Pos.Middle, Pos.BottomRight, Pos.Bottom), //9
        )

        fun String.makeDigit(words: List<Word>): Int {
            val segments = toCharArray().map { char ->
                words.firstNotNullOf { it[char] }
            }.toSet()
            val idx = segmentPositions.indexOf(segments)
            if (idx == -1) {
                println(words)
                println(segments)
                throw Exception("digit not found for word: $this")
            }
            return idx
        }

        return input.sumOf { entry ->
            val words: List<Word> = entry.digits.map { word ->
                word.toCharSet().associateWith { null }.toMutableMap()
            }

            val one = entry.digits.find { it.length == 2 }!!.toCharSet()
            val four = entry.digits.find { it.length == 4 }!!.toCharSet()
            val seven = entry.digits.find { it.length == 3 }!!.toCharSet()
            val eight = entry.digits.find { it.length == 7 }!!.toCharSet()

            words.setPos(seven.subtract(one).first(), Pos.Top)
            words.filter { word -> word.size == 6 }.forEach {
                val diff = eight.subtract(it.chars()).first()
                val pos = when {
                    !one.contains(diff) && !four.contains(diff) -> Pos.BottomLeft
                    !one.contains(diff) && four.contains(diff) -> Pos.Middle
                    else -> Pos.TopRight
                }
                words.setPos(diff, pos)
            }
            words.first { it.size == 5 && it.countUnknown() == 1 }.let { two ->
                words.setPos(two.unknownChars().first(), Pos.Bottom)
            }
            words.first { it.size == 5 && it.countUnknown() == 1 }.let { three ->
                words.setPos(three.unknownChars().first(), Pos.BottomRight)
            }
            words.first { it.size == 7 }.let { _eight ->
                words.setPos(_eight.unknownChars().first(), Pos.TopLeft)
            }

            entry.output.joinToString("") { it.makeDigit(words).toString() }.toInt()
        }
    }

    val testInput = readInput("Day${day}_test").toItems()
    part1(testInput).also {
        println("Part1 test: $it")
        check(it == 26)
    }
    part2(testInput).also {
        println("Part2 test: $it")
        check(it == 61229)
    }

    val input = readInput("Day$day").toItems()
    println(part1(input))
    println(part2(input))
}