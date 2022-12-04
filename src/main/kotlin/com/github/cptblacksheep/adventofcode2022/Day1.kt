package com.github.cptblacksheep.adventofcode2022

private val input = InputParser("input-day1.txt").parseIntLinesGrouped()

private fun solvePartOne() = input.maxOf { it.sum() }

private fun solvePartTwo() = input.map { it.sum() }
    .sortedDescending()
    .take(3)
    .sum()

private fun main() {
    println(solvePartOne())
    println(solvePartTwo())
}