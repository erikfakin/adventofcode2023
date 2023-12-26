package day15

import java.io.File

fun main() {
    var result = 0
    val input = File("src/main/kotlin/day15/input.txt").readText()
    val steps = input.split(",").map { it.trim() }.toList()
    steps.forEach { step ->
        var stepValue = 0
        step.forEach { char ->
            val ascii = char.code
            stepValue += ascii
            stepValue *= 17
            stepValue %= 256
        }
        result += stepValue
    }
    println("Result: $result")
}