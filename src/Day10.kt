// https://adventofcode.com/2023/day/10

fun main() {
    val testInput1 = readInput("Day10_test_1")
    val testInput2 = readInput("Day10_test_1")
    val input = readInput("Day10")

    fun part1(input: List<String>): Int {
        input
            .parse()
            .let {
                it.navigate(it.goToStart(), it.overridePipe(it.goToStart())) / 2
            }
    }

    fun part2(input: List<String>) = input
        .size

    // test if implementation meets criteria from the description, like:
    checkResult(part1(testInput1), 8)
    checkResult(part1(input), 6733)
    // checkResult(part2(testInput2), 0)
    // checkResult(part2(input), 0)
}

val goNorth = setOf('7', '|', 'F')
val goSouth = setOf('J', '|', 'L')
val goWest = setOf('L', '-', 'F')
val goEast = setOf('J', '-', '7')

enum class Direction { NORTH, EAST, SOUTH, WEST }

typealias Input = List<String>

data class Coords(val x: Int, val y: Int)
data class Move(val x: Int, val y: Int)

/**
 * This function translates the input into coordinates of (x, y), where (1, 1) represents the maps lower left corner
 */
fun Input.parse() = this
    .asReversed()
    .flatMapIndexed { i, row -> row.mapIndexed { j, char -> Pair(Coords(j + 1, i + 1), char) } }
    .toMap()

/**
 * This function finds the start coordinates
 */
fun Map<Coords, Char>.goToStart() = filterValues { it == 'S' }.keys.first()

/**
 * This function substitutes the start symbol (S) by the correct pipe symbol
 */
fun Map<Coords, Char>.overridePipe(s: Coords): Pair<Char, Direction> {
    return if (this[s.copy(y = s.y + 1)] in goNorth && this[s.copy(x = s.x + 1)] in goEast) { 'L' to Direction.NORTH } else {
        if (this[s.copy(y = s.y + 1)] in goNorth && this[s.copy(y = s.y - 1)] in goSouth) { '|' to Direction.NORTH } else {
            if (this[s.copy(y = s.y + 1)] in goNorth && this[s.copy(x = s.x - 1)] in goWest) { 'J' to Direction.NORTH } else {
                if (this[s.copy(x = s.x + 1)] in goEast && this[s.copy(y = s.y - 1)] in goSouth) { 'F' to Direction.EAST } else {
                    if (this[s.copy(x = s.x + 1)] in goEast && this[s.copy(x = s.x - 1)] in goWest) { '-' to Direction.EAST } else {
                        if (this[s.copy(y = s.y - 1)] in goSouth && this[s.copy(x = s.x - 1)] in goWest) { '7' to Direction.SOUTH } else {
                            throw IllegalArgumentException("Invalid character")
                        }
                    }
                }
            }
        }
    }
}

/**
 * This function navigates through the grid
 */
fun Map<Coords, Char>.navigate(start: Coords, override: Pair<Char, Direction>) =
    generateSequence(Triple(start, override.first, override.second)) { (current, pipe, direction) ->
        val move = pipe.nextMove(direction)
        Triple(
            Coords(current.x + move.first.x, current.y + move.first.y),
            this[Coords(current.x + move.first.x, current.y + move.first.y)]!!,
            move.second,
        )
    }
        .takeWhile { it.second != 'S' }
        .count()

/**
 * This function returns the next move and the direction we are coming from
 */
fun Char.nextMove(from: Direction): Pair<Move, Direction> {
    return when (from) {
        Direction.NORTH -> when (this) {
            'J' -> Move(-1, 0) to Direction.EAST
            '|' -> Move(0, -1) to Direction.NORTH
            'L' -> Move(1, -0) to Direction.WEST
            else -> throw IllegalArgumentException("Invalid character")
        }
        Direction.EAST -> when (this) {
            'L' -> Move(0, 1) to Direction.SOUTH
            '-' -> Move(-1, -0) to Direction.EAST
            'F' -> Move(0, -1) to Direction.NORTH
            else -> throw IllegalArgumentException("Invalid character")
        }
        Direction.SOUTH -> when (this) {
            '7' -> Move(-1, 0) to Direction.EAST
            '|' -> Move(0, 1) to Direction.SOUTH
            'F' -> Move(1, 0) to Direction.WEST
            else -> throw IllegalArgumentException("Invalid character")
        }
        Direction.WEST -> when (this) {
            'J' -> Move(0, 1) to Direction.SOUTH
            '-' -> Move(1, 0) to Direction.WEST
            '7' -> Move(0, -1) to Direction.NORTH
            else -> throw IllegalArgumentException("Invalid character")
        }
    }
}

/**
 * This is the initial version of the navigate function
 */
fun Map<Coords, Char>._navigate(start: Coords, initialSubstitute: Char, initialDirection: Direction): Int {
    var pipe = initialSubstitute
    var direction = initialDirection
    var current = start
    var count = 0

    while (pipe != 'S') {
        val move = pipe.nextMove(direction).first
        current = Coords(current.x + move.x, current.y + move.y)
        direction = pipe.nextMove(direction).second
        pipe = this[current]!!
        count += 1
    }
    return count
}
