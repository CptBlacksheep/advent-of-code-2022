package com.github.cptblacksheep.adventofcode2022

import com.github.cptblacksheep.adventofcode2022.Direction.*

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

private fun scenicScore(x: Int, y: Int) =
    viewingDistance(LEFT, x, y) * viewingDistance(RIGHT, x, y) * viewingDistance(UP, x, y) * viewingDistance(DOWN, x, y)


private fun viewingDistance(direction: Direction, x: Int, y: Int): Int {
    val range = when (direction) {
        LEFT -> (x - 1 downTo 0)
        RIGHT -> (x + 1 until input[0].size)
        UP -> (y - 1 downTo 0)
        DOWN -> (y + 1 until input.size)
    }
    var blockingTreeMet = false
    return range.takeWhile {
        val tree = when (direction) {
            LEFT, RIGHT -> input[y][it]
            UP, DOWN -> input[it][x]
        }
        if (blockingTreeMet) return@takeWhile false
        if (tree >= input[y][x]) blockingTreeMet = true
        true
    }.count()
}

private enum class Direction { LEFT, RIGHT, UP, DOWN }

private fun main() {
    println(solvePartOne())
    println(solvePartTwo())
}