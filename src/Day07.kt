// https://adventofcode.com/2023/day/7

fun main() {
    val testInput = readInput("Day07_test")
    val input = readInput("Day07")

    fun part1(input: List<String>, jokerRule: Boolean) = input
        .parse()
        .map { it -> it.evaluateHand(jokerRule) }
        .sort()
        .winnings()
        .sumOf { it }

    fun part2(input: List<String>, jokerRule: Boolean) = part1(input, jokerRule)

    // test if implementation meets criteria from the description, like:
    checkResult(part1(testInput, false), 6440)
    checkResult(part1(input, false), 247815719)
    checkResult(part2(testInput, true), 5905)
    checkResult(part2(input, true), 248747492)
}

typealias Input = List<String>
typealias Bid = Int
typealias Hand = String
typealias Card = Char
typealias Count = Int
typealias EvaluatedHands = List<Int>

fun Input.parse(): List<Pair<Hand, Bid>> = this
    .map { it.substringBefore(" ") to it.substringAfter(" ").toInt() }

fun Pair<Hand, Bid>.evaluateHand(jokerRule: Boolean): Pair<EvaluatedHands, Bid> =
    mutableListOf(first.groupingBy { it }.eachCount().patternValue(first.jokers(jokerRule))) + first.map { it.cardValue(jokerRule) } to second

fun Hand.jokers(jokerRule: Boolean) = if (jokerRule) this.count { it == 'J' } else 0

fun Map<Card, Count>.patternValue(jokers: Int) = when (this.values.sortedDescending()) {
    listOf(5) -> 6 // five of a kind
    listOf(4, 1) -> { if (jokers == 0) 5 else 6 } // four of a kind
    listOf(3, 2) -> { if (jokers == 0) 4 else 6 } // full house
    listOf(3, 1, 1) -> { if (jokers == 0) 3 else 5 } // three of a kind
    listOf(2, 2, 1) -> { if (jokers == 0) 2 else if (jokers == 1) 4 else 5 } // two pairs
    listOf(2, 1, 1, 1) -> { if (jokers == 0) 1 else 3 } // one pair
    else -> { if (jokers == 0) 0 else 1 } // high card
}

fun Card.cardValue(jokerRule: Boolean) = cardValues[this]?.let { if (jokerRule && it == 11) 1 else it } ?: 0

fun List<Pair<EvaluatedHands, Bid>>.sort(): List<Pair<EvaluatedHands, Bid>> = this.sortedWith(
    compareBy({ it.first[0] }, { it.first[1] }, { it.first[2] }, { it.first[3] }, { it.first[4] }, { it.first[5] }),
)

fun List<Pair<EvaluatedHands, Bid>>.winnings() = this
    .mapIndexed { index, pair -> pair.second * (index + 1) }

val cardValues = mapOf(
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
