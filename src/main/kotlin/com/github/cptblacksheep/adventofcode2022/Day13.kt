package com.github.cptblacksheep.adventofcode2022

private val input = InputParser("input-day13.txt").parseLinesGrouped()
    .map { pair -> pair.map { it.removeSurrounding("[", "]") } }

private fun solvePartOne() = parsePackets().mapIndexedNotNull { i, pair ->
    if (pairIsInRightOrder(pair[0], pair[1]) == 1) i + 1 else null
}.sum()

private fun solvePartTwo(): Int {
    var dividerOneIndex = 1
    var dividerTwoIndex = 2
    input.flatten().forEach { packet ->
        when (Regex("\\d+|\\[]").find(packet)?.value) {
            null, "[]", "0", "1" -> {
                dividerOneIndex++
                dividerTwoIndex++
            }

            "2", "3", "4", "5" -> {
                dividerTwoIndex++
            }
        }
    }
    return dividerOneIndex * dividerTwoIndex
}

private fun parsePackets() = input.map { pair -> pair.map { parsePacket(it).first } }

private fun parsePacket(packet: String, startIndex: Int = 0): Pair<List<Any>, Int> {
    val result = mutableListOf<Any>()
    var i = startIndex
    val currentNumber = StringBuilder()

    while (i <= packet.lastIndex) {
        when (val currentChar = packet[i]) {
            '[' -> {
                val subPacket = parsePacket(packet, i + 1)
                result.add(subPacket.first)
                i = subPacket.second
            }

            ']' -> {
                if (currentNumber.isNotEmpty()) result.add(currentNumber.toString().toInt())
                return Pair(result, i + 1)
            }

            ',' -> {
                if (currentNumber.isNotEmpty()) result.add(currentNumber.toString().toInt())
                currentNumber.clear()
                i++
            }

            else -> {
                currentNumber.append(currentChar)
                i++
            }
        }
    }
    if (currentNumber.isNotEmpty()) result.add(currentNumber.toString().toInt())
    return Pair(result, i)
}

private fun pairIsInRightOrder(left: List<Any>, right: List<Any>): Int {
    val indices = if (left.size > right.size) left.indices else right.indices

    indices.forEach { i ->
        val leftE = left.getOrNull(i) ?: return 1
        val rightE = right.getOrNull(i) ?: return 0

        if (leftE is Int && rightE is Int) when {
            leftE < rightE -> return 1
            leftE == rightE -> return@forEach
            else -> return 0
        }

        @Suppress("UNCHECKED_CAST")
        val subResult = when {
            leftE is List<*> && rightE is List<*> -> pairIsInRightOrder(leftE as List<Any>, rightE as List<Any>)
            leftE is Int -> pairIsInRightOrder(listOf(leftE), rightE as List<Any>)
            else -> pairIsInRightOrder(leftE as List<Any>, listOf(rightE))
        }
        when (subResult) {
            1 -> return 1
            0 -> return 0
            -1 -> return@forEach
        }
    }
    return -1
}

private fun main() {
    println(solvePartOne())
    println(solvePartTwo())
}