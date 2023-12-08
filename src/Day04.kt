// https://adventofcode.com/2023/day/4
import kotlin.math.pow

fun main() {
    val testInput1 = readInput("Day04_test_1")
    val testInput2 = readInput("Day04_test_2")
    val input = readInput("Day04")

    fun part1(input: List<String>) = input
        .parse()
        .map { sets -> sets[0].intersect(sets[1]) }
        .filter { set -> set.isNotEmpty() }
        .sumOf { set -> 2.toDouble().pow(set.size - 1) }
        .toInt()

    // fun part2(input: List<String>) = input
    //     .size

    // test if implementation meets criteria from the description, like:
    checkResult(part1(testInput1), 13)
    checkResult(part1(input), 28750)
    // checkResult(part2(testInput2), 0)
    // checkResult(part2(input), 0)
}

fun List<String>.parse() = this
    .map { line ->
        line
        .substringAfter(":")
        .split(" | ")
        .map { list ->
            list
            .trim()
            .replace("  ", " ")
            .split(" ")
            .map { it.toInt() }
            .toSet()
        }
    }
