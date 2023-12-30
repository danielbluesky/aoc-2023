// https://adventofcode.com/2023/day/5

fun main() {
    val testInput1 = readInput("Day05_test_1")
    val testInput2 = readInput("Day05_test_1")
    val input = readInput("Day05")

    fun part1(input: List<String>): Int = input
        .prepare()
        .parse()
        .locations()
        .min()
        .toInt()

    fun part2(input: List<String>): Int = input
        .prepare()
        .parse2()
        .locations2()
        .getMinimum()
        .toInt()

    // test if implementation meets criteria from the description, like:
    // checkResult(part1(testInput1), 35)
    // checkResult(part1(input), 525792406)
    // checkResult(part2(testInput2), 46)
    checkResult(part2(input), 79004094)
}

typealias Input = List<List<String>>
typealias Almanac = Pair<Set<Long>, Map<String, Set<Instruction>>>
typealias Almanac2 = Pair<Set<Pair<Long, Long>>, Map<String, Set<Instruction>>>

data class Instruction(val range: Pair<Long, Long>, val offset: Long)

// exercise 1
fun List<String>.prepare() = this
    .joinToString("\n")
    .split("\n\n")
    .map { it.split("\n") }

fun Input.parse() = parseSeeds() to parseInstructions()

fun Input.parseSeeds() = this[0][0]
    .substringAfter(" ")
    .split(" ")
    .map { it.toLong() }
    .toSet()

fun Input.parseInstructions() = this
    .drop(1)
    .associate { inputInstructions ->
        val step = inputInstructions[0]
        val instruction = inputInstructions
            .drop(1)
            .map { it.split(" ").map { it.toLong() } }
            .map { it -> Instruction(it[1] to it[1] + it[2] - 1, it[0] - it[1]) }
            .sortedBy { it.range.first }
            .toSet()
        step to instruction
    }

fun Almanac.locations(): Set<Long> {
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

// exercise 2
fun Input.parse2() = parseSeeds2() to parseInstructions()

fun Input.parseSeeds2() = this[0][0]
    .substringAfter(" ")
    .split(" ")
    .map { it.toLong() }
    .chunked(2)
    .map { it[0] to it[0] + it[1] - 1 }
    .sortedBy { it.first }
    .toSet()

fun Almanac2.locations2(): Locations {
    val toAdd = mutableSetOf<Seed>()
    val toRemove = mutableSetOf<Seed>()
    val newSeeds = mutableSetOf<Seed>()
    newSeeds.plusAssign(this.first)
    this.second.map { step ->
        newSeeds
        .map { seed ->
            step.value
                .filter { instr -> instr.range.second >= seed.first && instr.range.first <= seed.second }
                .forEach { instr ->
                    toRemove += seed
                    toAdd += seed.offset(instr)
                }
        }.let {
            newSeeds.apply {
                removeAll(toRemove)
                toRemove.clear()
                addAll(toAdd)
                toAdd.clear()
            }
        }
    }
    return newSeeds
}

typealias Seed = Pair<Long, Long>
typealias Locations = Set<Pair<Long, Long>>

fun Seed.offset(i: Instruction): Set<Pair<Long, Long>> =
    setOf(
        maxOf(i.range.first + i.offset, this.first + i.offset) to
        minOf(i.range.second + i.offset, this.second + i.offset),
    )

fun Locations.getMinimum() = this.minBy { it.first }.first
