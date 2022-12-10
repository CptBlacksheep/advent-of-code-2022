package com.github.cptblacksheep.adventofcode2022

private val input = InputParser("input-day9.txt").parseLines()
    .map { it.split(" ") }

private fun solvePartOne() = solve(2)

private fun solvePartTwo() = solve(10)

private fun solve(knotCount: Int): Int {
    val knots = MutableList(knotCount) { Coordinate(0, 0) }
    val tailVisits = mutableSetOf(Coordinate(0, 0))

    input.forEach {
        val direction = it[0]
        val steps = it[1].toInt()
        move(direction, steps, knots, tailVisits)
    }
    return tailVisits.size
}

private fun tailIsInHeadRange(tail: Coordinate, head: Coordinate) =
    head.x - tail.x in (-1..1) && head.y - tail.y in (-1..1)


private fun move(direction: String, steps: Int, knots: MutableList<Coordinate>, tailVisits: MutableSet<Coordinate>) {
    repeat(steps) {
        knots[0] = when (direction) {
            "L" -> knots[0].copy(x = knots[0].x - 1)
            "R" -> knots[0].copy(x = knots[0].x + 1)
            "U" -> knots[0].copy(y = knots[0].y - 1)
            else -> knots[0].copy(y = knots[0].y + 1)
        }

        (0 until knots.lastIndex).forEach {
            if (!tailIsInHeadRange(knots[it + 1], knots[it]))
                knots[it + 1] = moveTail(knots[it], knots[it + 1])
            if (it == knots.lastIndex - 1) tailVisits.add(knots[it + 1])
        }
    }
}

private fun moveTail(head: Coordinate, tail: Coordinate) = when {
    tail.x == head.x -> when {
        tail.y == head.y -> tail
        tail.y > head.y -> tail.copy(y = tail.y - 1)
        else -> tail.copy(y = tail.y + 1)
    }

    tail.x > head.x -> when {
        tail.y == head.y -> tail.copy(x = tail.x - 1)
        tail.y > head.y -> tail.copy(x = tail.x - 1, y = tail.y - 1)
        else -> tail.copy(x = tail.x - 1, y = tail.y + 1)
    }

    else -> when {
        tail.y == head.y -> tail.copy(x = tail.x + 1)
        tail.y > head.y -> tail.copy(x = tail.x + 1, y = tail.y - 1)
        else -> tail.copy(x = tail.x + 1, y = tail.y + 1)
    }
}

data class Coordinate(val x: Int, val y: Int)

private fun main() {
    println(solvePartOne())
    println(solvePartTwo())
}