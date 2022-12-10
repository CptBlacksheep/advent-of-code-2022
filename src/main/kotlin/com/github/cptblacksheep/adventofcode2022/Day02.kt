package com.github.cptblacksheep.adventofcode2022

import com.github.cptblacksheep.adventofcode2022.Choice.*
import com.github.cptblacksheep.adventofcode2022.Outcome.*

private val input = InputParser("input-day2.txt").parseLines()
    .map { it.split(" ") }

private fun solvePartOne() = input.sumOf {
    val opponentChoice = Choice.fromInput(it[0])
    val playerChoice = Choice.fromInput(it[1])
    playerChoice.score + rockPaperScissors(opponentChoice, playerChoice).score
}

private fun solvePartTwo() = input.sumOf {
    val opponentChoice = Choice.fromInput(it[0])
    val outcome = Outcome.fromInput(it[1])
    outcome.score + Choice.fromOpponentChoiceAndOutcome(opponentChoice, outcome).score
}

private fun rockPaperScissors(opponentChoice: Choice, playerChoice: Choice) = when {
    opponentChoice == playerChoice -> DRAW

    opponentChoice == ROCK && playerChoice == PAPER ||
            opponentChoice == PAPER && playerChoice == SCISSORS ||
            opponentChoice == SCISSORS && playerChoice == ROCK -> WIN

    else -> LOSE
}

private enum class Choice(val score: Int) {
    ROCK(1), PAPER(2), SCISSORS(3);

    companion object {
        fun fromInput(input: String) = when (input) {
            "A", "X" -> ROCK
            "B", "Y" -> PAPER
            else -> SCISSORS
        }

        fun fromOpponentChoiceAndOutcome(opponentChoice: Choice, outcome: Outcome) = when (outcome) {
            LOSE -> {
                when (opponentChoice) {
                    ROCK -> SCISSORS
                    PAPER -> ROCK
                    SCISSORS -> PAPER
                }
            }

            DRAW -> opponentChoice

            WIN -> {
                when (opponentChoice) {
                    ROCK -> PAPER
                    PAPER -> SCISSORS
                    SCISSORS -> ROCK
                }
            }
        }
    }
}

private enum class Outcome(val score: Int) {
    LOSE(0), DRAW(3), WIN(6);

    companion object {
        fun fromInput(input: String) = when (input) {
            "X" -> LOSE
            "Y" -> DRAW
            else -> WIN
        }
    }
}

private fun main() {
    println(solvePartOne())
    println(solvePartTwo())
}