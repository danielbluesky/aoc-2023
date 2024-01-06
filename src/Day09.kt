// https://adventofcode.com/2023/day/9

fun main() {
    val testInput1 = readInput("Day09_test")
    val testInput2 = readInput("Day09_test")
    val input = readInput("Day09")

    fun part1(input: List<String>, reversed: Boolean) = input
        .parse()
        .extrapolate(reversed)
        .sum()

    fun part2(input: List<String>, reversed: Boolean) = part1(input, reversed)

    // test if implementation meets criteria from the description, like:
    checkResult(part1(testInput1, false), 114)
    checkResult(part1(input, false), 2005352194)
    checkResult(part2(testInput2, true), 2)
    checkResult(part2(input, true), 1077)
}

typealias Input = List<String>
typealias Parsed = List<List<Int>>

fun Input.parse() = map { it.split(" ").map { it.toInt() } }

fun Parsed.extrapolate(reversed: Boolean): List<Int> {
    val extrapolatedValues = mutableListOf<Int>()
    this.map { input ->
        val deltas = mutableListOf<Int>()
        val initialList = if (reversed) input.reversed() else input
        var deltaList = initialList
        while (deltaList.all { it == 0 }.not()) {
            deltaList = deltaList.zipWithNext().map { it.second - it.first }
            deltas += deltaList.last()
        }
        extrapolatedValues += (initialList.last() + deltas.sum())
    }
    return extrapolatedValues
}
