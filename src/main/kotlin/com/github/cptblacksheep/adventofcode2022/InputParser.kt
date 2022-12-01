package com.github.cptblacksheep.adventofcode2022

class InputParser(resourceName: String) {

    private val res = javaClass.classLoader.getResource(resourceName)!!

    fun parseLines(): List<String> {
        return res.readText().split("\n")
    }

    fun parseIntLines(): List<Int> {
        return parseLines().map { it.toInt() }
    }

    fun parseLinesGrouped(): List<List<String>> {
        return mutableListOf<List<String>>().apply {
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
    }

    fun parseIntLinesGrouped(): List<List<Int>> {
        return parseLinesGrouped().map { group ->
            group.map { it.toInt() }
        }
    }

}