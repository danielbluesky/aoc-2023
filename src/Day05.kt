// https://adventofcode.com/2023/day/5

fun main() {
    val testInput1 = readInput("Day05_test_1")
    // val testInput2 = readInput("Day05_test_2")
    val input = readInput("Day05")

    fun part1(input: List<String>): Int {
        return input
        .parse()
        .locations()
        .min()
        .toInt()
    }

    fun part2(input: List<String>) = input
        .size

    // test if implementation meets criteria from the description, like:
    checkResult(part1(testInput1), 35)
    checkResult(part1(input), 525792406)
    // checkResult(part2(testInput2), 0)
    // checkResult(part2(input), 0)
}

data class Conversion(val range: Pair<Long, Long>, val offset: Long)

fun List<String>.parse(): Pair<Set<Long>, Map<String, Set<Conversion>>> {
    val input = this.joinToString("\n").split("\n\n").map { it.split("\n") }
    val seeds = input[0][0].substringAfter(" ").split(" ").map { it.toLong() }.toSet()
    val conversions = input.drop(1).associate { inputInstructions ->
        val step = inputInstructions[0]
        val instruction = inputInstructions
            .drop(1)
            .map { it.split(" ").map { it.toLong() } }
            .map { it -> Conversion(it[1] to it[1] + it[2] - 1, it[0] - it[1]) }
            .toSet()
        step to instruction
    }
    return seeds to conversions
}

fun Pair<Set<Long>, Map<String, Set<Conversion>>>.locations(): Set<Long> {
    val locations = mutableSetOf<Long>()
    first.map { seed ->
        var number = seed
        second.map { (_, instruction) ->
            number += (instruction.find { it.range.first <= number && number <= it.range.second }?.offset ?: 0)
        }
        locations.plusAssign(number)
    }.toSet()
    return locations
}
