package com.github.cptblacksheep.adventofcode2022

private val input = InputParser("input-day5.txt").parseLinesGrouped()

private val crates
    get() = (0 until input[0].maxOf { it.length }).map { x ->
        ArrayDeque(
            (input[0].indices).map { y -> input[0][y].getOrElse(x) { ' ' } }
                .filter { !"[] ".contains(it) }
                .dropLast(1)
                .reversed()
        )
    }.filter { it.isNotEmpty() }
        .toMutableList()

private val instructions = input[1].map { line ->
    Regex("\\d+").findAll(line)
        .map { it.value.toInt() }
        .toList()
}

private fun solvePartOne() = crates.let { crates ->
    instructions.forEach { instruction ->
        val origin = crates[instruction[1] - 1]
        val destination = crates[instruction[2] - 1]
        repeat(instruction[0]) {
            destination.addLast(origin.removeLast())
        }
    }
    buildString {
        crates.forEach { append(it.last()) }
    }
}

private fun solvePartTwo() = crates.let { crates ->
    instructions.forEach { instruction ->
        val quantity = instruction[0]
        val origin = crates[instruction[1] - 1]
        val destination = crates[instruction[2] - 1]

        val cratesToMove = origin.takeLast(quantity)
        repeat(quantity) {
            origin.removeLast()
        }
        destination.addAll(cratesToMove)
    }
    buildString {
        crates.forEach { append(it.last()) }
    }
}

private fun main() {
    println(solvePartOne())
    println(solvePartTwo())
}