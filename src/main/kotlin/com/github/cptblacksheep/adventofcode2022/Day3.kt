package com.github.cptblacksheep.adventofcode2022

private val input = InputParser("input-day3.txt").parseLines()
private val priorities = ('a'..'z') + ('A'..'Z')

private fun solvePartOne() = input.sumOf { line ->
    val compartments = line.chunked(line.length / 2)
    val matchingItem = compartments[0].find { compartments[1].contains(it) }!!
    priorities.indexOf(matchingItem) + 1
}

private fun solvePartTwo() = input.chunked(3)
    .sumOf { rucksacks ->
        val matchingItem = rucksacks[0].find { rucksacks[1].contains(it) && rucksacks[2].contains(it) }!!
        priorities.indexOf(matchingItem) + 1
    }

fun main() {
    println(solvePartOne())
    println(solvePartTwo())
}