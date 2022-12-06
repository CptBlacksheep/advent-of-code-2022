package com.github.cptblacksheep.adventofcode2022

private val input = InputParser("input-day6.txt").parseChars()

private fun solvePartOne() = solve(4)

private fun solvePartTwo() = solve(14)

private fun solve(distinctChars: Int): Int {
    var result = distinctChars
    run {
        input.windowed(distinctChars).forEach {
            if (it.distinct().size == distinctChars) {
                return@run
            }
            result++
        }
    }
    return result
}

fun main() {
    println(solvePartOne())
    println(solvePartTwo())
}