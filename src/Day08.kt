// https://adventofcode.com/2023/day/8

fun main() {
    val testInput1 = readInput("Day08_test_1")
    val testInput2 = readInput("Day08_test_1")
    val input = readInput("Day08")

    fun part1(input: List<String>) = input
        .parse()
        .let { it.second.navigate(it.first) }

    fun part2(input: List<String>) = input
        .size

    // test if implementation meets criteria from the description, like:
    checkResult(part1(testInput1), 6)
    checkResult(part1(input), 17873)
    // checkResult(part2(testInput2), 0)
    // checkResult(part2(input), 0)
}

typealias Input = List<String>
typealias Guide = String
typealias Start = String
typealias Destinations = Pair<String, String>

const val final = "ZZZ"

fun Input.parse(): Pair<Guide, Map<Start, Destinations>> = this[0] to this.drop(2).parseInstructions()

fun Input.parseInstructions(): Map<Start, Destinations> = this.associate { input ->
    val (start, destinations) = input.split(" = ")
    start to destinations.removeSurrounding("(", ")").split(", ").let { it[0] to it[1] }
}

fun Map<Start, Destinations>.navigate(guide: Guide): Int {
    var directions = guide
    var current = "AAA"
    var step = 0
    while (current != final) {
        current = if (directions.first() == 'L') this[current]!!.first else this[current]!!.second
        directions = directions.drop(1) + directions.first()
        step++
    }
    return step
}
