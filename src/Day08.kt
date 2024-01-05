// https://adventofcode.com/2023/day/8

fun main() {
    val testInput1 = readInput("Day08_test_1")
    val testInput2 = readInput("Day08_test_2")
    val input = readInput("Day08")

    fun part1(input: List<String>) = input
        .parse()
        .let { it.second.navigate(it.first) }

    fun part2(input: List<String>) = input
        .size

    // test if implementation meets criteria from the description, like:
    checkResult(part1(testInput1), 6)
    checkResult(part1(input), 17873)
    // checkResult(part2(testInput2), 6)
    // checkResult(part2(input), 0)
}

typealias Input = List<String>
typealias Guide = String
typealias Departure = String
typealias Destinations = Pair<String, String>

const val final = "ZZZ"

fun Input.parse(): Pair<Guide, Map<Departure, Destinations>> = this[0] to this.drop(2).parseNodes()

fun Input.parseNodes(): Map<Departure, Destinations> = this.associate { input ->
    val (departure, destinations) = input.split(" = ")
    departure to destinations.removeSurrounding("(", ")").split(", ").let { it[0] to it[1] }
}

fun Map<Departure, Destinations>.navigate(guide: Guide): Int {
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
