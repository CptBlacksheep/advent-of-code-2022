package com.github.cptblacksheep.adventofcode2022

class InputParser(resourceName: String) {

    private val res = javaClass.classLoader.getResource(resourceName)!!

    fun parseInputLines(): List<String> {
        return res.readText().split("\n")
    }
}