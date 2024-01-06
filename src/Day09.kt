// https://adventofcode.com/2023/day/9

fun main() {
    val testInput1 = readInput("Day09_test_1")
    val testInput2 = readInput("Day09_test_1")
    val input = readInput("Day09")

    fun part1(input: List<String>) = input
            .parse()
            .extrapolate()
            .sum()

    fun part2(input: List<String>) = input
        .size

    // test if implementation meets criteria from the description, like:
    checkResult(part1(testInput1), 114)
    checkResult(part1(input), 2005352194)
    // checkResult(part2(testInput2), 0)
    // checkResult(part2(input), 0)
}

typealias Input = List<String>
typealias Parsed = List<List<Int>>

fun Input.parse() = map { it.split(" ").map { it.toInt() } }

fun Parsed.extrapolate(): List<Int> {
    val extrapolatedValues = mutableListOf<Int>()
    this.map { initialList ->
        val deltas = mutableListOf<Int>()
        var deltaList = initialList
        while (deltaList.all { it == 0 }.not()) {
            deltaList = deltaList.zipWithNext().map { it.second - it.first }
            deltas += deltaList.last()
        }
        extrapolatedValues += (initialList.last() + deltas.sum())
    }
    return extrapolatedValues
}
