import java.io.File
import java.math.BigInteger
import java.security.MessageDigest

/**
 * Reads lines from the given input txt file.
 */
fun readInput(name: String) = File("src", "$name.txt").readLines()
fun readText(name: String) = File("src", "$name.txt").readText()

/**
 * Converts string to md5 hash.
 */
fun String.md5(): String = BigInteger(1, MessageDigest.getInstance("MD5").digest(toByteArray())).toString(16)

operator fun <T> List<List<T>>.get(pos: Pair<Int, Int>) = this[pos.first][pos.second]
operator fun <T> List<MutableList<T>>.set(pos: Pair<Int, Int>, value: T) {
    this[pos.first][pos.second] = value
}

fun <T> List<List<T>>.positions() = indices.map { row -> this[row].indices.map { col -> row to col } }.flatten()