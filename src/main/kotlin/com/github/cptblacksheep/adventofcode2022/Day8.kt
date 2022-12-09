package com.github.cptblacksheep.adventofcode2022

private val input = InputParser("input-day8.txt").parseIntGrid()

private fun solvePartOne() = input.mapIndexed { y, line ->
    line.filterIndexed { x, _ ->
        isTreeVisible(x, y)
    }
}.sumOf { it.size }

private fun solvePartTwo() = input.mapIndexed { y, line ->
    List(line.size) { x ->
        scenicScore(x, y)
    }
}.flatten().max()

private fun isTreeVisible(x: Int, y: Int) =
    if (x == 0 || x == input[0].lastIndex || y == 0 || y == input.lastIndex) {
        true
    } else {
        (0 until x).none { input[y][it] >= input[y][x] } ||
                (x + 1 until input[0].size).none { input[y][it] >= input[y][x] } ||
                (0 until y).none { input[it][x] >= input[y][x] } ||
                (y + 1 until input.size).none { input[it][x] >= input[y][x] }
    }

private fun scenicScore(x: Int, y: Int): Int {
    val viewingDistances = mutableListOf<Int>()
    val currentTree = input[y][x]
    var blockingTreeMet = false

    var viewingDistance = (x - 1 downTo 0).takeWhile {
        val tree = input[y][it]
        if (blockingTreeMet) {
            false
        } else {
            if (tree >= currentTree) blockingTreeMet = true
            true
        }
    }.count()
    viewingDistances.add(viewingDistance)
    blockingTreeMet = false

    viewingDistance = (x + 1 until input[0].size).takeWhile {
        val tree = input[y][it]
        if (blockingTreeMet) {
            false
        } else {
            if (tree >= currentTree) blockingTreeMet = true
            true
        }
    }.count()
    viewingDistances.add(viewingDistance)
    blockingTreeMet = false

    viewingDistance = (y - 1 downTo 0).takeWhile {
        val tree = input[it][x]
        if (blockingTreeMet) {
            false
        } else {
            if (tree >= currentTree) blockingTreeMet = true
            true
        }
    }.count()
    viewingDistances.add(viewingDistance)
    blockingTreeMet = false

    viewingDistance = (y + 1 until input.size).takeWhile {
        val tree = input[it][x]
        if (blockingTreeMet) {
            false
        } else {
            if (tree >= currentTree) blockingTreeMet = true
            true
        }
    }.count()
    viewingDistances.add(viewingDistance)

    return viewingDistances.reduce { d1, d2 -> d1 * d2 }
}

private fun main() {
    println(solvePartOne())
    println(solvePartTwo())
}