package com.github.cptblacksheep.adventofcode2022

import kotlin.math.max
import kotlin.math.min

private val input = InputParser("input-day14.txt").parseLines()
    .map { line ->
        line.split(" -> ").map {
            val coordinate = it.split(",")
            Pair(coordinate[0].toInt(), coordinate[1].toInt())
        }
    }

private val rocks = mutableSetOf<Pair<Int, Int>>().apply {
    input.forEach { line ->
        line.forEachIndexed() { i, coordinate ->
            add(coordinate)
            val nextCoordinate = line.getOrNull(i + 1) ?: return@forEach
            if (coordinate.first != nextCoordinate.first) {
                (min(coordinate.first, nextCoordinate.first)..max(coordinate.first, nextCoordinate.first)).forEach {
                    add(Pair(it, coordinate.second))
                }
            } else {
                (min(coordinate.second, nextCoordinate.second)..max(coordinate.second, nextCoordinate.second)).forEach {
                    add(Pair(coordinate.first, it))
                }
            }
        }
    }
}.toSet()

private val lowestRockY = rocks.maxBy { it.second }.second
private val floorY = lowestRockY + 2

private fun solvePartOne() = solve()

private fun solvePartTwo() = solve(true)

private fun solve(partTwo: Boolean = false): Int {
    val sand = mutableSetOf<Pair<Int, Int>>()
    while (true) {
        if (!addSand(sand, partTwo)) break
    }
    return sand.size
}

private fun addSand(existingSand: MutableSet<Pair<Int, Int>>, hasFloor: Boolean): Boolean {
    var currentPos = Pair(500, 0)
    while (true) {
        val oldPos = currentPos
        currentPos = moveSand(existingSand, currentPos)
        when {
            currentPos == oldPos -> break
            !hasFloor && currentPos.second == lowestRockY -> return false
            hasFloor && currentPos.second == floorY - 1 -> break
        }
    }
    existingSand.add(currentPos)
    if (hasFloor && currentPos == Pair(500, 0)) return false
    return true
}

private fun moveSand(existingSand: Set<Pair<Int, Int>>, currentSandPos: Pair<Int, Int>): Pair<Int, Int> {
    val (x, y) = currentSandPos
    return when {
        Pair(x, y + 1) !in rocks && Pair(x, y + 1) !in existingSand -> Pair(x, y + 1)
        Pair(x - 1, y + 1) !in rocks && Pair(x - 1, y + 1) !in existingSand -> Pair(x - 1, y + 1)
        Pair(x + 1, y + 1) !in rocks && Pair(x + 1, y + 1) !in existingSand -> Pair(x + 1, y + 1)
        else -> currentSandPos
    }
}

private fun main() {
    println(solvePartOne())
    println(solvePartTwo())
}