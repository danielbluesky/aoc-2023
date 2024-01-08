// https://adventofcode.com/2023/day/10

fun main() {
    val testInput1 = readInput("Day10_test_1")
    val testInput2 = readInput("Day10_test_1")
    val input = readInput("Day10")

    fun part1(input: List<String>) = input
            .parse()
            .let {
                val start = it.goToStart()
                val pipe = it.startingPipe(start)
                it.navigate(start, pipe, pipe.startingDirection()) / 2
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
 * This function substitutes the start symbol (S) by the correct pipe symbol, based on the adjacent pipes,
 * in order to feed it to the navigate function
 */
fun Map<Coords, Char>.startingPipe(s: Coords) =
     if (this[s.copy(y = s.y + 1)] in goNorth && this[s.copy(x = s.x + 1)] in goEast) { 'L' } else {
        if (this[s.copy(y = s.y + 1)] in goNorth && this[s.copy(y = s.y - 1)] in goSouth) { '|' } else {
            if (this[s.copy(y = s.y + 1)] in goNorth && this[s.copy(x = s.x - 1)] in goWest) { 'J' } else {
                if (this[s.copy(x = s.x + 1)] in goEast && this[s.copy(y = s.y - 1)] in goSouth) { 'F' } else {
                    if (this[s.copy(x = s.x + 1)] in goEast && this[s.copy(x = s.x - 1)] in goWest) { '-' } else {
                        if (this[s.copy(y = s.y - 1)] in goSouth && this[s.copy(x = s.x - 1)] in goWest) { '7' } else {
                            throw IllegalArgumentException("Invalid character")
                        }
                    }
                }
            }
        }
    }

/**
 * This function returns the (theoretical) incoming direction to derive the direction at the start from
 */
fun Char.startingDirection() = when {
    setOf('L', '|', 'J').contains(this) -> Direction.NORTH
    setOf('F', '-').contains(this) -> Direction.EAST
    setOf('7').contains(this) -> Direction.SOUTH
    else -> throw IllegalArgumentException("Invalid character")
}

/**
 * This function navigates through the grid and counts the moves
 */
fun Map<Coords, Char>.navigate(start: Coords, pipe: Char, direction: Direction) =
    generateSequence(Triple(start, pipe, direction)) { (current, pipe, direction) ->
        val move = pipe.nextMove(direction)
        Triple(
            Coords(current.x + move.x, current.y + move.y),
            this[Coords(current.x + move.x, current.y + move.y)]!!,
            move.incomingDirection(),
        )
    }.takeWhile { it.second != 'S' }.count()

/**
 * This function returns the next move
 */
fun Char.nextMove(from: Direction) = when (from) {
    Direction.NORTH -> when (this) {
        'J' -> Move(-1, 0)
        '|' -> Move(0, -1)
        'L' -> Move(1, 0)
        else -> throw IllegalArgumentException("Invalid character")
    }
    Direction.EAST -> when (this) {
        'L' -> Move(0, 1)
        '-' -> Move(-1, 0)
        'F' -> Move(0, -1)
        else -> throw IllegalArgumentException("Invalid character")
    }
    Direction.SOUTH -> when (this) {
        '7' -> Move(-1, 0)
        '|' -> Move(0, 1)
        'F' -> Move(1, 0)
        else -> throw IllegalArgumentException("Invalid character")
    }
    Direction.WEST -> when (this) {
        'J' -> Move(0, 1)
        '-' -> Move(1, 0)
        '7' -> Move(0, -1)
        else -> throw IllegalArgumentException("Invalid character")
    }
}

/**
 * This function returns the incoming direction from the (next) move's coordinates
 */
fun Move.incomingDirection() = when (this) {
    Move(0, -1) -> Direction.NORTH
    Move(0, 1) -> Direction.SOUTH
    Move(-1, 0) -> Direction.EAST
    Move(1, 0) -> Direction.WEST
    else -> throw IllegalArgumentException("Invalid coordinates")
}
