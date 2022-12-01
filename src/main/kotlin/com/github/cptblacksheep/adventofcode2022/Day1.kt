package com.github.cptblacksheep.adventofcode2022

private val input = InputParser("input-day1.txt").parseIntLinesGrouped()

private fun solvePartOne() {
    println(input.maxOf { it.sum() })
}

private fun solvePartTwo() {
    println(
        input.map { it.sum() }
            .sortedDescending()
            .take(3)
            .sum()
    )
}

private fun main() {
    solvePartOne()
    solvePartTwo()
}