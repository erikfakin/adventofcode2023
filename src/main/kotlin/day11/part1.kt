package day11

import java.io.File
import kotlin.math.absoluteValue
import kotlin.math.max
import kotlin.math.min


fun main() {


    var emptyRowsIndex = mutableListOf<Int>()
    var emptyColumnsIndex = mutableListOf<Int>()

    var tmpIndex = 0
    val input = mutableListOf<String>()
    File("src/main/kotlin/day11/input.txt").forEachLine { line ->
        if (line == ".".repeat(line.length)) emptyRowsIndex.add(tmpIndex)
        tmpIndex++
        input.add(line)
    }

    for (i in 0..<input.first().length) {
        for (j in 0..<input.size) {
            if (input[j][i] != '.') break
            if (j == input.size - 1) emptyColumnsIndex.add(i)
        }
    }

    val stars = mutableListOf<IntArray>()
    input.forEachIndexed { rowIndex, row ->
        Regex("#").findAll(row).forEach { result ->
            stars.add(intArrayOf(result.range.first, rowIndex))
        }
    }

    stars.forEach { println("Star at ${it[0]} ${it[1]}") }

    val results = mutableListOf<Int>()

    for (i in 0..<stars.size) {
        for (j in i + 1..<stars.size) {
            val star1 = stars[i]
            val star2 = stars[j]
            val emptyColsInBetween =
                emptyColumnsIndex.count { col -> col > min(star1[0], star2[0]) && col < max(star1[0], star2[0]) }
            val emptyRowsInBetween =
                emptyRowsIndex.count { row -> row > min(star1[1], star2[1]) && row < max(star1[1], star2[1]) }
            val dx = (star2[0] - star1[0]).absoluteValue + emptyColsInBetween
            val dy = (star2[1] - star1[1]).absoluteValue + emptyRowsInBetween
            val steps = dx + dy
            results.add(steps)
        }
    }

    println("RESULT")

    println(results.sum())

}