// https://adventofcode.com/2023/day/4
import kotlin.math.pow

typealias Card = Int
typealias Sets = Triple<Int, Set<Int>, Set<Int>>

data class Results(val analysedCard: Int, val cardInstances: Int, val matches: Int)

fun main() {
    val testInput1 = readInput("Day04_test_1")
    val testInput2 = readInput("Day04_test_2")
    val input = readInput("Day04")

    fun part1(input: List<String>) = input
        .parse()
        .map { sets -> sets.first.intersect(sets.second) }
        .filter { set -> set.isNotEmpty() }
        .sumOf { set -> 2.toDouble().pow(set.size - 1) }
        .toInt()

    fun part2(input: List<String>): Int {
        input
            .parse2()
            .let { it ->
                for (i in 1..it.size) { // to avoid ConcurrentModificationException
                    val game = it.entries.first { it.key == i }
                    val result = game.evaluate()
                    for (j in 1..result.matches) {
                        val nextCard = game.key + j
                        val nextSet = it[nextCard]!!
                        val updatedSet = Triple(nextSet.first + result.cardInstances, nextSet.second, nextSet.third)
                        it.plusAssign(nextCard to updatedSet)
                    }
                }
                return it.values.sumOf { it.first }
            }
    }

    // test if implementation meets criteria from the description, like:
    checkResult(part1(testInput1), 13)
    checkResult(part1(input), 28750)
    checkResult(part2(testInput2), 30)
    checkResult(part2(input), 10212704)
}

// part 1
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
    .map { Pair(it[0], it[1]) }

// part 2
fun List<String>.parse2(): MutableMap<Int, Triple<Int, Set<Int>, Set<Int>>> {
    val parsed = mutableMapOf<Card, Sets>()
    this.map { line ->
        line
        val game: Card = line.substringBefore(":").substringAfter(" ").trim().toInt()
        val sets: Sets = line
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
            .let { Triple(1, it[0], it[1]) }
        parsed.put(game, sets)
    }
    return parsed
}

fun Map.Entry<Int, Triple<Int, Set<Int>, Set<Int>>>.evaluate() =
    Results(
        analysedCard = this.key,
        cardInstances = this.value.first,
        matches = this.value.second.intersect(this.value.third).size,
    )

fun Results.collect() {
}
