// https://adventofcode.com/2023/day/8

fun main() {
    val testInput1 = readInput("Day08_test_1")
    val testInput2 = readInput("Day08_test_2")
    val input = readInput("Day08")

    fun part1(input: List<String>) = input
        .parse()
        .let { it.second.navigate(it.first) }

    fun part2(input: List<String>) = input
        .parse()
        .let { it.second.navigate2(it.first) }
        .lcm()
        .toString()

    // test if implementation meets criteria from the description, like:
    checkResult(part1(testInput1), 6)
    checkResult(part1(input), 17873)
    checkResult(part2(testInput2), "6")
    checkResult(part2(input), "15746133679061")
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

// Exercise 1
fun Map<Departure, Destinations>.navigate(guide: Guide): Int {
    var directions = guide
    var position = "AAA"
    var step = 0
    while (position != final) {
        position = if (directions.first() == 'L') this[position]!!.first else this[position]!!.second
        directions = directions.drop(1) + directions.first()
        step++
    }
    return step
}

// Exercise 2
fun Map<Departure, Destinations>.departures() = keys.filter { it -> it.last() == 'A' }

fun Map<Departure, Destinations>.navigate2(guide: Guide): List<Long> {
    var directions = guide
    val steps = mutableListOf<Long>()
    departures().map { it ->
        var step = 0
        var position = it
        while (position.last() != 'Z') {
            position = if (directions.first() == 'L') this[position]!!.first else this[position]!!.second
            directions = directions.drop(1) + directions.first()
            step++
        }
        steps += step.toLong()
    }
    return steps
}

// Lowest common multiple
fun List<Long>.lcm(): Long = fold(1L) { acc, i -> (acc * i) / gcd(acc, i) }

// Greatest common divisor
fun gcd(a: Long, b: Long): Long = if (b == 0L) a else gcd(b, a % b)

// This works on example data, but it seems to take very long on the real data set
fun Map<Departure, Destinations>._navigate2(guide: Guide): Int {
    var directions = guide
    var positions = departures()
    var step = 0
    while (positions.finalDestinations().not()) {
        positions = positions.map { it -> if (directions.first() == 'L') this[it]!!.first else this[it]!!.second }
        directions = directions.drop(1) + directions.first()
        step++
    }
    return step
}

fun List<Departure>.finalDestinations() = filter { it -> it.last() == 'Z' }.size == size
