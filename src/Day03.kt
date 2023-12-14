// https://adventofcode.com/2023/day/3

typealias Input = List<String>
typealias Symbols = MutableList<Pair<Int, Int>>
typealias Numbers = MutableList<NumberRange>

data class NumberRange(val number: Int, val row: Int, val start: Int, val end: Int)

fun main() {
    val testInput1 = readInput("Day03_test_1")
    val testInput2 = readInput("Day03_test_2")
    val input = readInput("Day03")

    fun part1(input: List<String>): Int = input
        .parse()
        .findPartNumbers()
        .sum()

    fun part2(input: List<String>) = input
        .parse()
        .findGears()
        .gearRatios()
        .sum()

    // test if implementation meets criteria from the description, like:
    checkResult(part1(testInput1), 4361)
    checkResult(part1(input), 527144)
    checkResult(part2(testInput2), 467835)
    checkResult(part2(input), 81463996)
}

// parsing
fun Input.parse(): Pair<Numbers, Symbols> {
    val symbols: Symbols = mutableListOf()
    val numbers: Numbers = mutableListOf()

    forEachIndexed { row, line ->
        line.forEachIndexed { col, char ->
            if (char.isDigit().not() && char != '.') symbols.add(col to row)
        }
        val numberRegex = Regex("\\d+")
        numberRegex.findAll(line).forEach {
            numbers.add(NumberRange(it.value.toInt(), row, it.range.first, it.range.last))
        }
    }
    return Pair(numbers, symbols)
}

// part 1
fun Pair<Numbers, Symbols>.findPartNumbers(): List<Int> {
    val partNumbers: MutableList<Int> = mutableListOf()
    first.forEach { (number, row, start, end) ->
        second
            .filter { (x, y) -> y in (row - 1..row + 1) && x in (start - 1..end + 1) }
            .forEach { _ -> partNumbers.add(number) }
    }
    return partNumbers.toList()
}

// part 2
fun Pair<Numbers, Symbols>.findGears(): List<List<Int>> {
    val gears = mutableListOf<MutableList<Int>>()
    second.forEach { (x, y) ->
        val numbers = mutableListOf<Int>()
        first
            .filter { it -> it.row in y - 1..y + 1 }
            .forEach { (number, row, start, end) ->
                if (x in (start - 1..end + 1) && y in (row - 1..row + 1)) { numbers.add(number) }
            }
        if (numbers.size == 2) gears += numbers
        if (numbers.size > 2) throw Exception("More than two adjacent numbers")
        }
    return gears.toList()
}

fun List<List<Int>>.gearRatios(): List<Int> = map { (it.first() * it.last()) }
