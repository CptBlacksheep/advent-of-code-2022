package com.github.cptblacksheep.adventofcode2022

private val input = InputParser("input-day12.txt").parseLines()

private val startCoordinate = input.flatMapIndexed { y, line ->
    line.mapIndexedNotNull { x, col ->
        if (col == 'S') Pair(x, y) else null
    }
}.first()

private val endCoordinate = input.flatMapIndexed { y, line ->
    line.mapIndexedNotNull { x, col ->
        if (col == 'E') Pair(x, y) else null
    }
}.first()

private fun solvePartOne() = searchShortestPath()

private fun solvePartTwo() = searchShortestPath(true)

private fun searchShortestPath(partTwo: Boolean = false): Int {
    val startCoordinate = if (partTwo) endCoordinate else startCoordinate
    val endHeight = if (partTwo) 'a' else 'E'
    val queue = ArrayDeque<Pair<Int, Int>>()
    val steps = ArrayDeque<Int>()
    val visited = mutableSetOf<Pair<Int, Int>>()
    queue.add(startCoordinate)
    steps.add(0)

    while (queue.isNotEmpty()) {
        val currentCoordinate = queue.removeFirst()
        val currentSteps = steps.removeFirst()
        if (currentCoordinate in visited) continue
        visited.add(currentCoordinate)

        var currentHeight = input[currentCoordinate.second][currentCoordinate.first]
        if (currentHeight == endHeight) return currentSteps
        if (currentHeight == 'S') currentHeight = 'a'

        getAdjacentCoordinates(currentCoordinate).forEach { adj ->
            val adjHeight = input[adj.second][adj.first]
            if (partTwo) {
                if (adjHeight >= currentHeight - 1 && adj !in visited) {
                    queue.addLast(adj)
                    steps.addLast(currentSteps + 1)
                }
            } else {
                if (adjHeight <= currentHeight + 1 && adj !in visited) {
                    queue.addLast(adj)
                    steps.addLast(currentSteps + 1)
                }
            }
        }
    }
    return -1
}

private fun getAdjacentCoordinates(coordinate: Pair<Int, Int>): List<Pair<Int, Int>> =
    mutableListOf<Pair<Int, Int>>().apply {
        val x = coordinate.first
        val y = coordinate.second
        if (input[y].getOrNull(x - 1) != null) add(Pair(x - 1, y))
        if (input[y].getOrNull(x + 1) != null) add(Pair(x + 1, y))
        if (input.getOrNull(y - 1) != null) add(Pair(x, y - 1))
        if (input.getOrNull(y + 1) != null) add(Pair(x, y + 1))
    }

private fun main() {
    println(solvePartOne())
    println(solvePartTwo())
}