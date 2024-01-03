// https://adventofcode.com/2023/day/6

import kotlin.math.ceil
import kotlin.math.floor
import kotlin.math.pow
import kotlin.math.sqrt

fun main() {
    val testInput1 = readInput("Day06_test")
    val testInput2 = readInput("Day06_test")
    val input = readInput("Day06")

    fun part1(input: List<String>) = input
        .parse1()
        .solve1()
        .multiply()

    fun part2(input: List<String>) = input
        .parse2()
        .solve2()

    // test if implementation meets criteria from the description, like:
    checkResult(part1(testInput1), 288)
    checkResult(part1(input), 2612736)
    checkResult(part2(testInput2), 71503)
    checkResult(part2(input), 29891250)
}

fun List<String>.parse1() = Pair(
    this[0].substringAfter(":").split(" ").mapNotNull { it.toIntOrNull() },
    this[1].substringAfter(":").split(" ").mapNotNull { it.toIntOrNull() },
    )

fun List<String>.parse2() = Pair(
    this[0].filter { it.isDigit() }.toDouble(),
    this[1].filter { it.isDigit() }.toDouble(),
)

/**
currentRecord = distance = raceTime * speed
winning races: raceTime * speed > distance

distance = Input.Distance = c
totalTime = Input.Time = b
loadingTime = x
raceTime = Input.Time - loadingTime = b - x
speed = loadingTime = x

winning races:
    (        raceTime        ) *     speed   >    distance
    (Input.Time - loadingTime) * loadingTime > Input.Distance
    (     b     -      x     ) *      x      >      c

equation:
    (b - x) * x > c
    bx - x^2 > c
    x^2 - bx + c > 0

solve:
    x1 = (-b + sqrt(b^2 - 4ac)) / 2a
    x2 = (-b - sqrt(b^2 - 4ac)) / 2a
 */
fun Pair<List<Int>, List<Int>>.solve1() =
     first
         .map { it.toDouble() * -1 }
         .zip(second.map { it.toDouble() })
         .map { (b, c) ->
             ceil((-b + sqrt(b.pow(2) - 4 * c)) / 2).toInt() - floor((-b - sqrt(b.pow(2) - 4 * c)) / 2).toInt() - 1
    }

fun Pair<Double, Double>.solve2() = ceil((-first + sqrt(first.pow(2) - 4 * second)) / 2).toInt() - floor((-first - sqrt(first.pow(2) - 4 * second)) / 2).toInt() - 1

fun List<Int>.multiply() = this.reduce { acc, i -> acc * i }

// my function, before refactoring it with ChatGPT
fun Pair<List<Int>, List<Int>>._solve(): List<Int> {
    val b = first.map { it.toDouble() * -1 }
    val c = second.map { it.toDouble() }
    val winningRaces = mutableListOf<Int>()

    for (i in b.indices) {
        val x1 = (-b[i] + sqrt((b[i]).pow(2) - 4 * 1 * c[i])) / 2
        val x2 = (-b[i] - sqrt((b[i]).pow(2) - 4 * 1 * c[i])) / 2
        winningRaces += ceil(x1).toInt() - floor(x2).toInt() - 1
        println(winningRaces)
    }
    return winningRaces
}
