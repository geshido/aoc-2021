fun main() {
    fun part1(input: List<String>): Int {
        var num =0
        var prev = 0
        input.map { it.toInt() }.forEach {
            if (prev != 0 && it > prev) {
                num++
            }
            prev = it
        }
        return num
    }

    fun part2(input: List<String>): Int {
        val ints = input.map { it.toInt() }

        var num = 0
        val windows = ints.windowed(3, 1, true)
        windows.forEachIndexed { index, cur ->
            if (index>0) {
                if (cur.sum() > windows[index-1].sum()) {
                    num++
                }
            }
        }
        return num
    }

    fun part2manual(input: List<String>): Int {
        val ints = input.map { it.toInt() }
        var i = 0
        var j = 2
        var num = 0
        while (j < ints.size) {
            if (i > 0) {
                if (ints.subList(i-1, j).sum() < ints.subList(i, j+1).sum()) {
                    num++
                }
            }
            i++
            j++
        }
        return num
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day01_test")
    println(part2manual(testInput))

    val input = readInput("Day01")
//    println(part1(input))
    println(part2(input))
    println(part2manual(input))
}
