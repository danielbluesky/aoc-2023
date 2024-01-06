// https://adventofcode.com/2023/day/4

/*
import kotlin.math.pow

data class Deck(val currentInstances: Int, val myNumbers: Set<Int>, val benchmark: Set<Int>)
data class Result(val wonInstances: Int, val deckUpdates: Int)
data class Deck2pra(val currentInstances: Int, val deckUpdates: Int)

fun main() {
    val testInput1 = readInput("Day04_test_1")
    val testInput2 = readInput("Day04_test_2")
    val input = readInput("Day04")

    fun part1(input: List<String>) = input
        .parse1()
        .map { sets -> sets.first.intersect(sets.second) }
        .filter { set -> set.isNotEmpty() }
        .sumOf { set -> 2.toDouble().pow(set.size - 1) }
        .toInt()

    fun part2(input: List<String>) = input
        .parse2()
        .play()
        .sumOf { it.currentInstances }

    fun part2pra(input: List<String>) = input
        .parse2pra()
        .play2pra()
        .sumOf { it.currentInstances }

    // test if implementation meets criteria from the description, like:
    checkResult(part1(testInput1), 13)
    checkResult(part1(input), 28750)
    checkResult(part2(testInput2), 30)
    checkResult(part2(input), 10212704)
}

// part 1
fun List<String>.parse1() = this
    .map { line -> line.substringAfter(":").split(" | ").map { it.parseSets() } }
    .map { Pair(it[0], it[1]) }

fun String.parseSets() = this
    .trim()
    .replace("  ", " ")
    .split(" ")
    .map { it.toInt() }
    .toSet()

// part 2
fun List<String>.parse2(): MutableMap<Int, Deck> =
    associate { line ->
        val round = line.substringBefore(":").substringAfter(" ").trim().toInt()
        val (firstSet, secondSet) = line.substringAfter(":").split(" | ").map { it.parseSets() }
        round to Deck(1, firstSet, secondSet)
    }.toMutableMap()

fun List<String>.parse2pra(): MutableMap<Int, Deck2pra> =
    associate { line ->
        val card = line.substringBefore(":").substringAfter(" ").trim().toInt()
        val (firstSet, secondSet) = line.substringAfter(":").split(" | ").map { it.parseSets() }
        card to Deck2pra(1, firstSet.intersect(secondSet).size) // die intersection per card(id) ist immer gleich
    }.toMutableMap()

// not directly working on MutableMap to avoid ConcurrentModificationException
fun MutableMap<Int, Deck>.play(): List<Deck> {
    (1..size).map { round ->
        val thisRound = this.entries.first { it.key == round }
        val result = thisRound.evaluate()
        (1..result.deckUpdates).map { i ->
            val nextRound = thisRound.key + i
            val nextDeck = this[nextRound]!!
            val updatedNextDeck = Deck((nextDeck.currentInstances + result.wonInstances), nextDeck.myNumbers, nextDeck.benchmark)
            this.plusAssign(nextRound to updatedNextDeck)
        }
    }
    return this.values.toList()
}

// not directly working on MutableMap to avoid ConcurrentModificationException
fun MutableMap<Int, Deck2pra>.play2pra(): List<Deck2pra> {
    (1..size).map { card ->
        val thisCard = this.entries.first { it.key == card }
        (1..thisCard.value.deckUpdates).map { i ->
            val nextCard = thisCard.key + i
            val nextDeck = this[nextCard]!!
            this[nextCard] = Deck2pra((nextDeck.currentInstances + thisCard.value.currentInstances), nextDeck.deckUpdates)
        }
    }
    return this.values.toList()
}

fun Map.Entry<Int, Deck>.evaluate(): Result = Result(value.currentInstances, value.myNumbers.intersect(value.benchmark).size)

// replaced with support of Chat GPT
fun List<String>._parse2(): MutableMap<Int, Deck> {
    val parsed = mutableMapOf<Int, Deck>()
    this.map { line ->
        line
        val round = line.substringBefore(":").substringAfter(" ").trim().toInt()
        val deck: Deck = line.substringAfter(":").split(" | ").map { it.parseSets() }.let { Deck(1, it[0], it[1]) }
        parsed.put(round, deck)
    }
    return parsed
}
*/
