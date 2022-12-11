package com.github.cptblacksheep.adventofcode2022

private val input = InputParser("input-day11.txt").parseLinesGrouped()

private fun solvePartOne() = solve(20, true)

private fun solvePartTwo() = solve(10_000, false)

private fun solve(rounds: Int, divideWorryLevels: Boolean): Long {
    val monkeys = createMonkeys()
    val lowestCommonMultiple = monkeys.map { it.testValue }.reduce { v1, v2 -> v1 * v2 }
    repeat(rounds) {
        monkeys.forEach { monkey ->
            monkey.doRound(monkeys, divideWorryLevels, lowestCommonMultiple)
        }
    }
    val inspections = monkeys.map { it.inspections }.sortedDescending()
    return inspections[0] * inspections[1]
}

private fun createMonkeys() = input.map { notes ->
    val items = Regex("\\d+").findAll(notes[1])
        .map { it.value.toLong() }
        .toList()

    val operation = Regex("\\d+|old|[*+]").findAll(notes[2])
        .map { it.value }
        .toList()

    val testValue = notes[3].split(" ").last().toLong()
    val trueMonkey = notes[4].split(" ").last().toInt()
    val falseMonkey = notes[5].split(" ").last().toInt()

    Monkey(items, operation[1], operation[2], testValue, trueMonkey, falseMonkey)
}

private class Monkey(
    items: List<Long>,
    val operator: String,
    operationValue: String,
    val testValue: Long,
    val trueMonkey: Int,
    val falseMonkey: Int
) {
    val operationValue = if (operationValue != "old") operationValue.toLong() else null
    val items = items.toMutableList()
    var inspections = 0L

    fun doRound(monkeys: List<Monkey>, divideWorryLevels: Boolean, lowestCommonMultiple: Long) {
        items.forEach { itemWorryLevel ->
            inspections++
            throwItem(monkeys, calculateWorryLevel(itemWorryLevel, divideWorryLevels, lowestCommonMultiple))
        }
        items.clear()
    }

    private fun calculateWorryLevel(item: Long, divideWorryLevels: Boolean, lowestCommonMultiple: Long): Long {
        val newWorryLevel = if (operator == "*") {
            (item * (operationValue ?: item)) % lowestCommonMultiple
        } else {
            (item + (operationValue ?: item)) % lowestCommonMultiple
        }

        return if (divideWorryLevels) newWorryLevel / 3L
        else newWorryLevel
    }

    private fun throwItem(monkeys: List<Monkey>, item: Long) =
        if (item % testValue == 0L) {
            monkeys[trueMonkey].items.add(item)
        } else {
            monkeys[falseMonkey].items.add(item)
        }
}

private fun main() {
    println(solvePartOne())
    println(solvePartTwo())
}