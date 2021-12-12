// naive implementation

fun main() {
    val day = "12"

    fun caves(input: List<String>): Map<String, Set<String>> {
        val caves = mutableMapOf<String, MutableSet<String>>()
        input.forEach {
            val (from, to) = it.split("-")
            caves.getOrPut(from) { mutableSetOf() }
            caves[from]!!.add(to)
            caves.getOrPut(to) { mutableSetOf() }
            caves[to]!!.add(from)
        }
        return caves
    }

    fun String.once() = this.lowercase() == this

    fun part1(input: List<String>): Int {
        val caves = caves(input)


        fun traverse(node: String, seen: MutableSet<String>): Set<String> {
            if (node == "end") return setOf(node)
            if (node.once()) seen += node

            val results = mutableSetOf(node)
            for (c in caves[node]!!.filter { it !in seen && it != "start" }) {
                val seenCopy = mutableSetOf<String>()
                seenCopy.addAll(seen)
                traverse(c, seenCopy).forEach {
                    results.add("$node-$it")
                }
            }
            return results
        }

        val allPaths = traverse("start", mutableSetOf())
        val paths = allPaths.filter { it.endsWith("end") }

        return paths.size
    }

    fun part2(input: List<String>): Int {
        val caves = caves(input)

        fun traverse(node: String, seen: MutableList<String>, twice: String): Set<String> {
            if (node == "end") return setOf(node)
            if (node.once()) seen += node

            val results = mutableSetOf(node)
            for (c in caves[node]!!.filter { (it !in seen && it != "start") || (it == twice && seen.count { s -> s==it } == 1) }) {
                val seenCopy = mutableListOf<String>()
                seenCopy.addAll(seen)
                traverse(c, seenCopy, twice).forEach { results.add("$node-$it") }
            }
            return results
        }

        val paths = traverse("start", mutableListOf(), "").toMutableSet()
        caves.keys.filter { it.once() && it != "end" && it != "start"}.forEach {
            paths.addAll(traverse("start", mutableListOf(), it))
        }
        val found = paths.filter { it.endsWith("end") }

        return found.size
    }



    part1(readInput("Day${day}_test1")).also {
        println("Part1-1 test: $it")
        check(it == 10)
    }
    part1(readInput("Day${day}_test2")).also {
        println("Part1-2 test: $it")
        check(it == 19)
    }
    part1(readInput("Day${day}_test3")).also {
        println("Part1-3 test: $it")
        check(it == 226)
    }
    part2(readInput("Day${day}_test1")).also {
        println("Part2-1 test: $it")
        check(it == 36)
    }
    part2(readInput("Day${day}_test2")).also {
        println("Part2-2 test: $it")
        check(it == 103)
    }
    part2(readInput("Day${day}_test3")).also {
        println("Part2-3 test: $it")
        check(it == 3509)
    }

    println("Part1: ${part1(readInput("Day$day"))}")
    println("Part2: ${part2(readInput("Day$day"))}")
}