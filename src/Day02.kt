// https://adventofcode.com/2023/day/2

fun main() {
    val testInput1 = readInput("Day02_test_1")
    val testInput2 = readInput("Day02_test_2")
    val input = readInput("Day02")

    fun part1(input: List<String>) = input
        .size

    fun part2(input: List<String>) = input
        .size

    // test if implementation meets criteria from the description, like:
    checkResult(part1(testInput1), 0)
    checkResult(part1(input), 0)
    checkResult(part2(testInput2), 0)
    checkResult(part2(input), 0)
}
