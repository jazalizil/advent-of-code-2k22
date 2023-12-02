package day01

import println
import readInput

fun List<String>.groupByCalories(): IntArray =
    this.fold(intArrayOf(0)) { acc, item ->
        if (item != "") {
            acc[acc.size - 1] += item.toInt()
            acc
        }
        else
            acc.plus(0)
    }

fun main() {

    fun part1(input: List<String>): Int {
        return input.groupByCalories().max()
    }

    fun part2(input: List<String>): Int {
        return input.groupByCalories().sorted().reversed().take(3).sum()
    }

    val testInput1 = readInput("day01/Day01_test")
    check(part1(testInput1) == 24000)

    val testInput2 = readInput("day01/Day01_test")
    check(part2(testInput2) == 45000)

    val input = readInput("day01/Day01")
    part1(input).println()
    part2(input).println()
}