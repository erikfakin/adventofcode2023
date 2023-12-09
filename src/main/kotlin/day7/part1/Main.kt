package day7.part1

import java.awt.List
import java.io.File
import java.util.Arrays
import kotlin.io.path.Path
import kotlin.math.*


val cardsScore = "123456789TJQKA"

class Hand(val cards: String, val bid: Int) {
    var score: String = "0000"

    init {
        calculateScore()
    }

    private fun calculateScore() {
        val sortedCards = cards.toCharArray().sorted()
        val cardsMap: MutableMap<Char, Int> = mutableMapOf()
        for (char in sortedCards) {
            if (cardsMap[char] == 0) continue
            cardsMap[char] = sortedCards.count { c: Char -> c == char }
        }
        for (i in 2..5) {
            score = score.replaceRange(i - 2, i - 1, cardsMap.values.count { occurances -> occurances == i }.toString())

        }
        score = score.reversed()
    }

    override fun toString(): String {
        return "Cards: $cards Score: $score Bid: $bid | "
    }

}

fun readFile(filename: String): Int {
    var sum = 0
    var handsList: MutableList<Hand> = mutableListOf()

    File(filename).forEachLine { line ->
        val cards = line.split(" ").first()
        val bid = line.split(" ").last().toInt()
        val hand = Hand(cards, bid)
        handsList.add(hand)
    }

    val cardsComparator = Comparator<Hand> { hand1, hand2 ->
        val cards1 = hand1.cards.toCharArray()
        val cards2 = hand2.cards.toCharArray()
        var index = 0
        while (index < cards1.size && index < cards2.size) {
            val str1Char = cardsScore.indexOf(cards1[index])
            val str2Char = cardsScore.indexOf(cards2[index])

            if (str1Char < str2Char) {
                return@Comparator -1
            } else if (str1Char > str2Char) {
                return@Comparator 1
            }

            index++
        }
        return@Comparator cards1.size - cards2.size
    }

    handsList = handsList.sortedWith(compareBy<Hand> { it.score }.then(cardsComparator)).toMutableList()
    handsList.forEachIndexed { index, hand ->
        println("POSITION: ${index + 1} hand $hand")
        sum += (index + 1) * hand.bid
    }
    return sum
}

fun main(args: Array<String>) {
    val inputPath = Path("src/main/kotlin/day7/part1/input.txt").toString()
    println("RESULT: ${readFile(inputPath)}")
}