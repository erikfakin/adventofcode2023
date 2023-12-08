package day4.part1

import java.io.File
import kotlin.io.path.Path
import kotlin.math.pow


fun readFile(filename: String): Int {
    var sum = 0

    File(filename).forEachLine {line ->

        val card = line.split(":")[1]
        val cardNumbers : MutableSet<String> = mutableSetOf()
        val myNumbers : MutableSet<String> = mutableSetOf()
        var numberOfHits = 0

        Regex("(\\d+)").findAll(card.split("|").first()).forEach {
            result ->
            cardNumbers.add(result.groupValues.first())
        }
        Regex("(\\d+)").findAll(card.split("|").last()).forEach {
                result ->
            myNumbers.add(result.groupValues.first())
        }

        cardNumbers.forEach {
            number ->
            if (myNumbers.contains(number)) numberOfHits++

        }

        if (numberOfHits == 0) return@forEachLine
        sum  += 2.0.pow((numberOfHits - 1).toDouble()).toInt()
    }

    return sum

}


fun main(args: Array<String>) {
    val inputPath = Path("src/main/kotlin/day4/part1/input.txt").toString()
    println("RESULT: ${readFile(inputPath)}")
}