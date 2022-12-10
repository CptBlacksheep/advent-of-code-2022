package com.github.cptblacksheep.adventofcode2022

private val input = InputParser("input-day10.txt").parseLines()
    .map { it.split(" ") }

private fun solvePartOne(): Int {
    val valuesInProgress = ArrayDeque<Int>()
    val signalStrengths = mutableListOf<Int>()
    var xRegister = 1

    (1..220).forEach { cycle ->
        when (cycle) {
            20, 60, 100, 140, 180, 220 -> signalStrengths.add(cycle * xRegister)
        }
        queueInputIfAvailable(cycle, valuesInProgress)
        xRegister += valuesInProgress.removeFirst()
    }
    return signalStrengths.sum()
}

private fun solvePartTwo(): String {
    val valuesInProgress = ArrayDeque<Int>()
    val result = MutableList(6) { " ".repeat(40) }
    var xRegister = 1

    (1..240).forEach { cycle ->
        if ((cycle - 1) % 40 in (xRegister - 1..xRegister + 1)) {
            result[(cycle - 1) / 40] = result[(cycle - 1) / 40]
                .replaceRange((cycle - 1) % 40, (cycle - 1) % 40 + 1, "#")
        }
        queueInputIfAvailable(cycle, valuesInProgress)
        xRegister += valuesInProgress.removeFirst()
    }
    return result.joinToString("\n")
}

private fun queueInputIfAvailable(cycle: Int, queue: ArrayDeque<Int>) {
    if (cycle <= input.size) {
        queue.addLast(0)
        if (input[cycle - 1][0] == "addx") {
            queue.addLast(input[cycle - 1][1].toInt())
        }
    }
}

private fun main() {
    println(solvePartOne())
    println(solvePartTwo())
}