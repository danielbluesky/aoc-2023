// https://adventofcode.com/2023/day/1

fun main() {
    val testInput1 = readInput("Day01_test_1")
    val testInput2 = readInput("Day01_test_2")
    val input = readInput("Day01")

    fun part1(input: List<String>) = input
        .sumOf { line ->
            line.filter { it.isDigit() }.let {
                it.first().digitToInt() * 10 + it.last().digitToInt()
            }
        }

    fun part2(input: List<String>) = part1(input.refine())

    fun _part1(input: List<String>) =
        input.sumOf { line -> line.first { it.isDigit() }.digitToInt() }.times(10)
            .plus(input.sumOf { line -> line.last { it.isDigit() }.digitToInt() })

    // test if implementation meets criteria from the description, like:
    // checkResult(part1b(testInput1), 142)
    // checkResult(part2(testInput2), 281)
    checkResult(part1(testInput1), 142)
    checkResult(part1(input), 54561)
    checkResult(part2(testInput2), 281)
    checkResult(part2(input), 54076)
}

fun String.replaceText(mapping: Map<String, String>): String =
    mapping
        .entries
        .fold(this) { text, (old, new) -> text.replace(old, new) }

fun List<String>.refine() = this
    .map { line -> line.replaceText(mappingRules) }
    .toList()

fun List<String>.refine2() = this
    .map { line ->
        var newLine = line
        mappingRules.forEach { (k, v) ->
            newLine = newLine.replace(k, v)
        }
        newLine
    }
    .toList()

val mappingRules = mapOf(
    "one" to "o1e",
    "two" to "t2o",
    "three" to "th3ee",
    "four" to "f4ur",
    "five" to "f5ve",
    "six" to "s6x",
    "seven" to "se7en",
    "eight" to "ei8ht",
    "nine" to "n9ne",
)
