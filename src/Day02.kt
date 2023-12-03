// https://adventofcode.com/2023/day/2

typealias InputMap = Map<Int, List<Pair<String, Int>>>
typealias GameOutcome = List<Pair<String, Int>>
typealias Input = List<String>

fun main() {
    val testInput1 = readInput("Day02_test_1")
    val testInput2 = readInput("Day02_test_2")
    val input = readInput("Day02")

    fun part1(input: List<String>): Int {
        return input
            .parse()
            .calculate(benchmark)
    }

    fun part2(input: List<String>) = input
        .size

    // test if implementation meets criteria from the description, like:
    checkResult(part1(testInput1), 8)
    checkResult(part1(input), 2447)
    // checkResult(part2(testInput2), 0)
    // checkResult(part2(input), 0)
}

fun InputMap.calculate(benchmark: Map<String, Int>): Int {
    return this
        .entries
        .map { game ->
            println(game.value)
            if (game.value.isPossible(benchmark)) game.key else 0
        }
        .sumOf { it }
}

fun Input.parse(): Map<Int, List<Pair<String, Int>>> {
    val parsed = mutableMapOf<Int, List<Pair<String, Int>>>()
    this
        .map { it ->
            val game = it
                .substringBefore(":")
                .substringAfter(" ")
                .toInt()
            val results = it
                .substringAfter(": ")
                .replace(";", ",")
                .split(",")
                .map { it.trim() }
                .map { it -> Pair(it.substringAfter(" "), it.substringBefore(" ").toInt()) }
            parsed[game] = results
            println(parsed)
        }
    return parsed
}

fun GameOutcome.isPossible(benchmark: Map<String, Int>): Boolean {
    this.map { color ->
        if (color.second > benchmark[color.first]!!) return false
    }
    return true
}

val benchmark = mapOf(
    "red" to 12,
    "green" to 13,
    "blue" to 14,
)

// used before parsing
val myTestInput = mutableMapOf(
    1 to listOf(
        "b" to 3,
        "r" to 4,
        "r" to 1,
        "g" to 2,
        "b" to 6,
        "g" to 2,
    ),
    2 to listOf(
        "b" to 1,
        "g" to 2,
        "g" to 3,
        "b" to 4,
        "r" to 1,
        "g" to 1,
        "b" to 1,
    ),
    3 to listOf(
        "g" to 8,
        "b" to 6,
        "r" to 20,
        "b" to 5,
        "r" to 4,
        "g" to 13,
        "g" to 5,
        "r" to 1,
    ),
    4 to listOf(
        "g" to 1,
        "r" to 3,
        "b" to 6,
        "g" to 3,
        "r" to 6,
        "g" to 3,
        "b" to 15,
        "r" to 14,
    ),
    5 to listOf(
        "r" to 6,
        "b" to 1,
        "g" to 3,
        "b" to 2,
        "r" to 1,
        "g" to 2,
    ),
)
