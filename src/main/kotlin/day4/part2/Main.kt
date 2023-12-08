package day4.part2

import java.io.File
import kotlin.io.path.Path
import kotlin.math.pow


fun readFile(filename: String): Int {
    var sum = 0

    val cards: MutableList<String> = File(filename).readLines().toMutableList()
    val numberOfCardCopies = MutableList<Int>(cards.size) { 1 }

    println(numberOfCardCopies)

    cards.forEach { cardLine ->

        val card = cardLine.split(":").last()
        val cardId = Regex("(\\d+)").findAll(cardLine.split(":").first()).first().value



        val cardNumbers: MutableSet<String> = mutableSetOf()
        val myNumbers: MutableSet<String> = mutableSetOf()
        var numberOfHits = 0

        Regex("(\\d+)").findAll(card.split("|").first()).forEach { result ->
            cardNumbers.add(result.groupValues.first())
        }
        Regex("(\\d+)").findAll(card.split("|").last()).forEach { result ->
            myNumbers.add(result.groupValues.first())
        }

        cardNumbers.forEach { number ->
            if (myNumbers.contains(number)) numberOfHits++

        }

        println("The card with id $cardId has $numberOfHits matches")

        if (numberOfHits == 0) return@forEach

        val startIndex = cardId.toInt()
        val endIndex = cardId.toInt()  + numberOfHits - 1

        for (i in startIndex..endIndex) {
            println("current card id $cardId")
            println("i am adding ${numberOfCardCopies[startIndex - 1]} copies of card with id $i")
            numberOfCardCopies[i] = numberOfCardCopies[i] + numberOfCardCopies[startIndex - 1]
        }

        println(numberOfHits)


    }

    numberOfCardCopies.forEach { n -> sum+=n }

    println(numberOfCardCopies)

    return sum


}


fun main(args: Array<String>) {
    val inputPath = Path("src/main/kotlin/day4/part2/input.txt").toString()
    println("RESULT: ${readFile(inputPath)}")
}