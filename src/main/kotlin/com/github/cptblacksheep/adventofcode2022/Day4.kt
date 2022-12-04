package com.github.cptblacksheep.adventofcode2022

private val input = InputParser("input-day4.txt").parseLines()
    .map { line ->
        Regex("\\d+").findAll(line).chunked(2).map {
            (it[0].value.toInt()..it[1].value.toInt())
        }.toList()
    }

private fun solvePartOne() = input.count { sections ->
    sections[0].all { it in sections[1] } || sections[1].all { it in sections[0] }
}

private fun solvePartTwo() = input.count { sections ->
    sections[0].any { it in sections[1] } || sections[1].any { it in sections[0] }
}

private fun main() {
    println(solvePartOne())
    println(solvePartTwo())
}