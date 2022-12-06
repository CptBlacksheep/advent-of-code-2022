package com.github.cptblacksheep.adventofcode2022

class InputParser(resourceName: String) {

    private val res = javaClass.classLoader.getResource(resourceName)!!

    fun parseLines() = res.readText()
        .split("\n")
        .dropLastWhile { it.isEmpty() }

    fun parseIntLines() = parseLines().map { it.toInt() }

    fun parseLinesGrouped() = mutableListOf<List<String>>().apply {
        val group = mutableListOf<String>()
        parseLines().forEach { line ->
            if (line.isEmpty()) {
                add(group.toList())
                group.clear()
            } else {
                group.add(line)
            }
        }
        add(group.toList())
    }

    fun parseIntLinesGrouped() = parseLinesGrouped().map { group -> group.map { it.toInt() } }

    fun parseString(lineSeparator: String = "") = parseLines().joinToString(lineSeparator) { it }

    fun parseChars() = parseString().toList()
}