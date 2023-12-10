package day9.part2

import java.io.File
import kotlin.io.path.Path


fun readFile(filename: String): Long {
    var sum = 0L
    val pyramids = mutableListOf<MutableList<MutableList<Long>>>()
    File(filename).forEachLine { line ->
        pyramids.add(
            mutableListOf<MutableList<Long>>(
                Regex("(-?\\d+)").findAll(line).map { matchResult -> matchResult.groupValues.first().toLong() }
                    .toMutableList()))
    }




    pyramids.forEachIndexed { i, pyramid ->
        var currentRowIndex = 0
        while (true) {
            val currentRow = pyramid[currentRowIndex]
            val nextRow = mutableListOf<Long>()
            for (i in 0..<currentRow.size - 1) {
                nextRow.add(currentRow[i + 1] - currentRow[i])
            }
            pyramid.add(nextRow)
            if (nextRow.all { it == 0L }) break
            currentRowIndex++
        }
        pyramid[pyramid.indices.last].addFirst(0L)

        for (i in 1..<pyramid.size) {

            pyramid[pyramid.size - 1 - i].addFirst(pyramid[pyramid.size - 1 - i].first() - pyramid[pyramid.size - i].first())
        }

    }

    pyramids.forEach { pyramid ->
        sum += pyramid[0].first()

    }
    return sum
}

fun main(args: Array<String>) {
    val inputPath = Path("src/main/kotlin/day9/part2/input.txt").toString()
    println("RESULT: ${readFile(inputPath)}")
}