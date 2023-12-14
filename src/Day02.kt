// https://adventofcode.com/2023/day/2
/*
typealias InputMap = Map<Int, List<Result>>
typealias Input = List<String>
typealias CubeSet = Map<Color, Int>

enum class Color(
    val value: String,
) {
    RED("red"), BLUE("blue"), GREEN("green");
    companion object { infix fun from(value: String): Color? = Color.values().firstOrNull { it.value == value } }
}
data class Result(val color: Color, val number: Int)

val benchmark = mapOf(
    Color.RED to 12,
    Color.BLUE to 14,
    Color.GREEN to 13,
)

fun main() {
    val testInput1 = readInput("Day02_test_1")
    val testInput2 = readInput("Day02_test_2")
    val input = readInput("Day02")

    fun part1(input: List<String>) = input
        .parse()
        .calculate()

    fun part2(input: List<String>) = input
        .parse()
        .map { line -> line.cubeSet().cubePower() }
        .sum()

    // test if implementation meets criteria from the description, like:
    checkResult(part1(testInput1), 8)
    checkResult(part1(input), 2447)
    checkResult(part2(testInput2), 2286)
    checkResult(part2(input), 56322)
}

// parsing
fun Input.parse(): Map<Int, List<Result>> {
    val parsed = mutableMapOf<Int, List<Result>>()
    this.map { line -> parsed.put(line.parseGame(), line.parseResult()) }
    return parsed
}

fun String.parseGame(): Int = this
    .substringBefore(":")
    .substringAfter(" ")
    .toInt()

fun String.parseResult(): List<Result> = this
    .substringAfter(": ")
    .replace(";", ",")
    .split(",")
    .map { it.trim() }
    .map { it -> Result((Color from it.substringAfter(" "))!!, it.substringBefore(" ").toInt()) }

// part 1
fun InputMap.calculate(): Int {
    return this
        .entries
        .map { game -> if (game.value.isPossible()) game.key else 0 }
        .sumOf { it }
}

fun List<Result>.isPossible(): Boolean {
    this.map { result -> if (result.number > benchmark[result.color]!!) return false }
    return true
}

// part 2
fun Map.Entry<Int, List<Result>>.cubeSet(): CubeSet {
    val cubes = mutableMapOf(Color.RED to 0, Color.GREEN to 0, Color.BLUE to 0)
    this.value.map { (c, n) -> if (n > cubes[c]!!) cubes[c] = n }
    return cubes.toMap()
}

fun CubeSet.cubePower() = this[Color.RED]!! * this[Color.BLUE]!! * this[Color.GREEN]!!

// replaced stuff
/*
fun InputMap._cubeSet2(): CubeSet {
    val cubes = mutableMapOf(Color.RED to 0, Color.GREEN to 0, Color.BLUE to 0)
    this.map { (_, v) ->
        println(v)
        v.map { result ->
            if (result.number > cubes[result.color]!!) cubes[result.color] = result.number
        }
    }
    println(cubes)
    return cubes
}

fun InputMap._calculate2(): Int {
    val finalResult = mutableListOf<Int>()
    this.map { game ->
        val cubes = mutableMapOf(Color.RED to 0, Color.GREEN to 0, Color.BLUE to 0)
        game.value.map { result ->
            if (result.number > cubes[result.color]!!) cubes.plusAssign(result.color to result.number)
        }
        finalResult.add(cubes[Color.RED]!! * cubes[Color.BLUE]!! * cubes[Color.GREEN]!!)
    }
    return finalResult.sum()
}
*/
*/
