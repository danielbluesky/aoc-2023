import java.math.BigInteger
import java.security.MessageDigest
import kotlin.io.path.Path
import kotlin.io.path.readLines

/**
 * Reads lines from the given input txt file.
 */
fun readInput(name: String) = Path("resources/$name.txt").readLines()

/**
 * Converts string to md5 hash.
 */
fun String.md5() = BigInteger(1, MessageDigest.getInstance("MD5").digest(toByteArray()))
    .toString(16)
    .padStart(32, '0')

/**
 * The cleaner shorthand for printing output.
 */
fun Any?.println() = println(this)

fun checkResult(result: Int, expectedValue: Int) {
    println(result)
    check(result == expectedValue)
}

fun checkResult(result: String, expectedValue: String) {
    println(result)
    check(result == expectedValue)
}

fun check(result: Int) {
    println(result)
}

fun check(result: String) {
    println(result)
}
