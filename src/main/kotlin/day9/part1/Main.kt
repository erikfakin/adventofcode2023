package day9.part1

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
        pyramid[pyramid.indices.last].add(0)

        for (i in 1..<pyramid.size) {
            pyramid[pyramid.size - 1 - i].add(pyramid[pyramid.size - i].last() + pyramid[pyramid.size - 1 - i].last())
        }

    }

    pyramids.forEach { pyramid ->
        sum += pyramid[0].last()

    }
    return sum
}

fun main(args: Array<String>) {
    val inputPath = Path("src/day11.day11.main/kotlin/day9/part1/input.txt").toString()
    println("RESULT: ${readFile(inputPath)}")
}