// https://adventofcode.com/2023/day/3

typealias Input = List<String>
typealias SymbolCoords = MutableList<Pair<Int, Int>>
typealias NumberRanges = MutableList<Quadruple>

data class Quadruple(val first: Int, val second: Int, val third: Int, val fourth: Int)

fun main() {
    val testInput1 = readInput("Day03_test_1")
    val testInput2 = readInput("Day03_test_2")
    val input = readInput("Day03")

    fun part1(input: List<String>): Int = input
        .parse()
        .findPartNumbers()
        .sum()

    fun part2(input: List<String>): Int {
        val x = input
            .parse()
            .findGears()
            .gearRatios()
            .sum()
        println(x)
        return x
    }

    // test if implementation meets criteria from the description, like:
    checkResult(part1(testInput1), 4361)
    checkResult(part1(input), 527144)
    checkResult(part2(testInput2), 467835)
    checkResult(part2(input), 81463996)
}

fun Input.parse(): Pair<NumberRanges, SymbolCoords> {
    val symbols: SymbolCoords = mutableListOf()
    val numbers: NumberRanges = mutableListOf()

    this.forEachIndexed { row, line ->
        line.forEachIndexed { col, char ->
            if (char.isDigit().not() && char != '.') symbols.add(col to row)
        }
        val numberRegex = Regex("\\d+")
        numberRegex.findAll(line).forEach {
            numbers.add(Quadruple(it.value.toInt(), row, it.range.first, it.range.last))
        }
    }
    return Pair(numbers, symbols)
}

fun Pair<NumberRanges, SymbolCoords>.findPartNumbers(): List<Int> {
    val partNumbers: MutableList<Int> = mutableListOf()
    this.first.forEach { (number, line, start, end) ->
        if (this.second.any { (x, y) ->
            y in (line - 1..line + 1) && x in (start - 1..end + 1)
        }
        ) {
            partNumbers.add(number)
        }
    }
    return partNumbers.toList()
}

fun Pair<NumberRanges, SymbolCoords>.findGears(): Map<Pair<Int, Int>, MutableList<Int>> {
    val gears = mutableMapOf<Pair<Int, Int>, MutableList<Int>>()
    this.second.forEach { (x, y) ->
        val numbers = mutableListOf<Int>()
        this.first.forEach { (number, line, start, end) ->
            if (x in (start - 1..end + 1) && y in (line - 1..line + 1)) {
                numbers.add(number)
            }
        }
        gears[Pair(x, y)] = numbers
        }
    return gears.toMap()
}

fun Map<Pair<Int, Int>, List<Int>>.gearRatios(): List<Int> {
    val gearRatios = mutableListOf<Int>()
    this.forEach {
        if (it.value.size == 2) gearRatios.add(it.value.first() * it.value.last())
    }
    return gearRatios.toList()
}
