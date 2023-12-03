// https://adventofcode.com/2023/day/2

typealias InputMap = Map<Int, List<Pair<String, Int>>>
typealias GameOutcome = List<Pair<String, Int>>
typealias Input = List<String>

val benchmark = mapOf(
    "red" to 12,
    "green" to 13,
    "blue" to 14,
)

fun main() {
    val testInput1 = readInput("Day02_test_1")
    val testInput2 = readInput("Day02_test_2")
    val input = readInput("Day02")

    fun part1(input: List<String>) = input
        .parse()
        .calculate()

    fun part2(input: List<String>) = input
        .size

    // test if implementation meets criteria from the description, like:
    checkResult(part1(testInput1), 8)
    checkResult(part1(input), 2447)
    // checkResult(part2(testInput2), 0)
    // checkResult(part2(input), 0)
}

fun InputMap.calculate(): Int {
    return this
        .entries
        .map { game -> if (game.value.isPossible()) game.key else 0 }
        .sumOf { it }
}

fun GameOutcome.isPossible(): Boolean {
    this.map { color -> if (color.second > benchmark[color.first]!!) return false }
    return true
}

fun Input.parse(): Map<Int, List<Pair<String, Int>>> {
    val parsed = mutableMapOf<Int, List<Pair<String, Int>>>()
    this.map { line -> parsed[line.parseGame()] = line.parseResult() }
    return parsed
}

fun String.parseGame(): Int = this
    .substringBefore(":")
    .substringAfter(" ")
    .toInt()

fun String.parseResult(): List<Pair<String, Int>> = this
    .substringAfter(": ")
    .replace(";", ",")
    .split(",")
    .map { it.trim() }
    .map { it -> Pair(it.substringAfter(" "), it.substringBefore(" ").toInt()) }
