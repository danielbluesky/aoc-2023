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
fun Almanac2.locations2(): Set<Pair<Long, Long>> {
    val toAdd = mutableSetOf<Pair<Long, Long>>()
    val toRemove = mutableSetOf<Pair<Long, Long>>()
    val newSeeds = mutableSetOf<Pair<Long, Long>>()
    newSeeds.plusAssign(this.first)
    this.second.map { step ->
        println("loop 1: this is step ${step.key} with comparison range instructions ${step.value}")
        println("  seed ranges: $newSeeds")
        newSeeds.map { seed ->
            println("    loop 2: analyse the following seed range: $seed")
            step.value.map { instr ->
                if ((instr.range.second < seed.first || instr.range.first > seed.second).not()) {
                    toRemove.plusAssign(seed)
                    toAdd.plusAssign(seed.split(instr))
                }
            }
        }
        println("    loop 2: wrap up...")
        newSeeds.removeAll(toRemove)
        println("    - toRemove: $toRemove")
        newSeeds.addAll(toAdd)
        newSeeds.sortedBy { it.first }
        println("    - toAdd   : $toAdd")
        toRemove.clear()
        toAdd.clear()
    }
    println("----------------------------")
    println("final set of seeds: $newSeeds")
    return newSeeds
}

fun Pair<Long, Long>.split(instruction: Instruction?): Set<Pair<Long, Long>> {
    if (instruction != null) {
        val seed = this
        val range = instruction.range
        val offset = instruction.offset
        println("      split the seed ranges")
        println("      - seed  : $seed")
        println("      - range : $range")
        println("      - offset: $offset")
        println("      - analyse type...")
        val seedIsEnclosed = range.first <= this.first && range.second >= this.second
        val seedOverlapsLeft = range.first <= this.first && range.second > this.first && range.second < this.second
        val seedOverlapsRight = range.second > this.second && range.first <= this.second && range.first > this.first
        val seedIsEnclosing = range.first > this.first && range.second < this.second

        if (seedIsEnclosed) {
            val overlap = Pair(seed.first + offset, seed.second + offset)
            val result = setOf(overlap)
            println("        - seedIsEnclosed: $seedIsEnclosed")
            println("        - result after offset: $result")
            return result
        }
        if (seedOverlapsLeft) {
            val overlap = Pair(seed.first + offset, range.second + offset)
            // val outsideRight = Pair(range.second + 1, seed.second)
            // val result = setOf(overlap, outsideRight)
            val result = setOf(overlap)
            println("        - seedOverlapsLeft: $seedOverlapsLeft")
            println("        - result after offset: $result")
            return result
        }
        if (seedOverlapsRight) {
            // val outsideLeft = Pair(seed.first, range.first - 1)
            val overlap = Pair(range.first + offset, seed.second + offset)
            // val result = setOf(outsideLeft, overlap)
            val result = setOf(overlap)
            println("        - seedOverlapsRight: $seedOverlapsRight")
            println("        - result after offset: $result")
            return result
        }
        if (seedIsEnclosing) {
            // val outsideLeft = Pair(seed.first, range.first - 1)
            val overlap = Pair(range.first + offset, range.second + offset)
            // val outsideRight = Pair(range.second + 1, seed.second)
            // val result = setOf(outsideLeft, overlap, outsideRight)
            val result = setOf(overlap)
            println("        - seedIsEnclosing: $seedIsEnclosing")
            println("        - result after offset: $result")
            return result
        }
    }
    println("        - unchanged set: ${setOf(this)}")
    return setOf(this) // no split
}

fun Set<Pair<Long, Long>>.getMinimum() = this.minBy { it.first }.first

// did not work for performance reasons
fun Almanac2._locations2(): Set<Long> {
    val locations = mutableSetOf<Long>()
    for (pair in this.first) {
        println("pair: $pair")
        val seed = pair.first
        println("seed: $seed")
        for (i in 0 until pair.second) {
            println("pair.first: ${pair.first}")
            var number = seed + i
            println("number: $number")
            second.map { (_, instruction) ->
                println(instruction.forEach { println("range: ${it.range.first} - ${it.range.second}") })
                number += (instruction.find { it.range.first <= number && number <= it.range.second }?.offset ?: 0)
                println("instruction: $instruction")
                println("number: $number")
            }
            locations.plusAssign(number)
            println("locations: $locations")
        }
    }
    return locations.toSet()
}
