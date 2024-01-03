// https://adventofcode.com/2023/day/7

fun main() {
    val testInput1 = readInput("Day07_test")
    val testInput2 = readInput("Day07_test")
    val input = readInput("Day07")

    fun part1(input: List<String>) = input
        .parse()
        .map { it -> it.evaluateHand() }
        .sort()
        .winnings()
        .sumOf { it }

    fun part2(input: List<String>) = input
        .size

    // test if implementation meets criteria from the description, like:
    checkResult(part1(testInput1), 6440)
    checkResult(part1(input), 247815719)
    // checkResult(part2(testInput2), 5905)
    // checkResult(part2(input), 0)
}

typealias Bid = Int
typealias Hand = String
typealias EvaluatedHands = List<Int>

fun List<String>.parse(): List<Pair<Hand, Bid>> = this
    .map { it.substringBefore(" ") to it.substringAfter(" ").toInt() }

fun Pair<Hand, Bid>.evaluateHand(): Pair<EvaluatedHands, Bid> =
    mutableListOf(first.groupingBy { it }.eachCount().handValue()) + first.map { mapping[it]!! } to second

fun Map<Char, Int>.handValue() = when (this.values.sortedDescending()) {
    listOf(5) -> 6 // five of a kind
    listOf(4, 1) -> 5 // four of a kind
    listOf(3, 2) -> 4 // full house
    listOf(3, 1, 1) -> 3 // three of a kind
    listOf(2, 2, 1) -> 2 // two pairs
    listOf(2, 1, 1, 1) -> 1 // one pair
    else -> 0 // high card
}

fun List<Pair<EvaluatedHands, Bid>>.sort(): List<Pair<EvaluatedHands, Bid>> = this
    .sortedWith(
        compareBy({ it.first[0] }, { it.first[1] }, { it.first[2] }, { it.first[3] }, { it.first[4] }, { it.first[5] }),
    )

fun List<Pair<EvaluatedHands, Bid>>.winnings() = this
    .mapIndexed { index, pair -> pair.second * (index + 1) }

val mapping = mapOf(
    'A' to 14,
    'K' to 13,
    'Q' to 12,
    'J' to 11,
    'T' to 10,
    '9' to 9,
    '8' to 8,
    '7' to 7,
    '6' to 6,
    '5' to 5,
    '4' to 4,
    '3' to 3,
    '2' to 2,
)
