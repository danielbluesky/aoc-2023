import java.io.File

fun main() {
    val testFile = "Day01_test"
    val file = "Day01"

    fun part1(input: List<String>): Int = input
        .sumOf { line -> line.first { it.isDigit() }.digitToInt() }
        .times(10)
        .plus(input.sumOf { line -> line.last { it.isDigit() }.digitToInt() })

    // fun part2(input: List<String>): Int {
    //    return input.size
    // }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput(testFile)
    checkResult(part1(testInput), 142)

    val input = readInput(file)
    checkResult(part1(input), 54561)
    // checkResult(part2(input), 0)
}
