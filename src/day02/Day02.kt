package day02

import println
import readInput
import java.lang.Exception

enum class Score(val score: Int) {
    LOST(0), DRAW(3), WIN(6);
    operator fun plus(value: Int) :Int = score + value
}

interface IShape {
    val score: Int
    fun lost(): Int {
        return Score.LOST + score
    }
    fun draw(): Int {
        return Score.DRAW + score
    }
    fun win(): Int {
        return Score.WIN + score
    }

    fun play(shape: IShape): Int
    fun getResponse(result: Score): IShape
}

data class Rock(
    override val score: Int = 1,
) : IShape {
    override fun play(shape: IShape): Int {
        return when (shape) {
            is Scissors -> win()
            is Paper -> lost()
            else -> draw()
        }
    }

    override fun getResponse(result: Score): IShape {
        return when (result) {
            Score.LOST -> Scissors()
            Score.DRAW -> Rock()
            Score.WIN -> Paper()
        }
    }
}
data class Paper(
    override val score: Int = 2,
) : IShape {
    override fun play(shape: IShape): Int {
        return when (shape) {
            is Rock -> win()
            is Scissors -> lost()
            else -> draw()
        }
    }
    override fun getResponse(result: Score): IShape {
        return when (result) {
            Score.LOST -> Rock()
            Score.DRAW -> Paper()
            Score.WIN -> Scissors()
        }
    }
}
data class Scissors(
    override val score: Int = 3,
) : IShape {
    override fun play(shape: IShape): Int {
        return when (shape) {
            is Paper -> win()
            is Rock -> lost()
            else -> draw()
        }
    }
    override fun getResponse(result: Score): IShape {
        return when (result) {
            Score.LOST -> Paper()
            Score.DRAW -> Scissors()
            Score.WIN -> Rock()
        }
    }
}

class ShapeBuilder {
    fun opponent(character: Char): IShape {
        return when (character) {
            'A' -> Rock()
            'B' -> Paper()
            'C' -> Scissors()
            else -> throw Exception("Opponent not found")
        }
    }
    fun response(character: Char): IShape {
        return when(character) {
            'X' -> Rock()
            'Y' -> Paper()
            'Z' -> Scissors()
            else -> throw Exception("Response not found")
        }
    }
}

class ScoreBuilder {
    fun build(character: Char): Score {
        return when (character) {
            'X' -> Score.LOST
            'Y' -> Score.DRAW
            'Z' -> Score.WIN
            else -> throw Exception("Score not found")
        }
    }
}

fun main() {

    fun part1(input: List<String>): Int {
        var builder = ShapeBuilder()
        return input.sumOf { round ->
            val plays = round.split(' ')
            val opponentShape = builder.opponent(plays.first().first())
            val responseShape = builder.response(plays.get(1).first())
            responseShape.play(opponentShape)
        }
    }

    fun part2(input: List<String>): Int {
        var shapeBuilder = ShapeBuilder()
        var scoreBuilder = ScoreBuilder()
        return input.sumOf { round ->
            var plays = round.split(' ')
            val opponentShape = shapeBuilder.opponent(plays.first().first())
            val score = scoreBuilder.build(plays.get(1).first())
            val responseShape = opponentShape.getResponse(score)
            var result = responseShape.play(opponentShape)
            result
        }
    }

    val testInput1 = readInput("day02/Day02_test")
    check(part1(testInput1) == 15)

    val testInput2 = readInput("day02/Day02_test")
    check(part2(testInput2) == 12)

    val input = readInput("day02/Day02")
    part1(input).println()
    part2(input).println()
}
