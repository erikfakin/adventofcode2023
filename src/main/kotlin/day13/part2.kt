package day13_2

import java.io.File
import kotlin.math.min

fun findSmudgeHorizontally(pattern: MutableList<String>, index: Int): MutableList<MutableList<Int>> {
    val maxOffset = min(index, pattern.size - (index + 2))
    var differencesIndex = mutableListOf(mutableListOf<Int>())

    for (i in 0..maxOffset) {
        for (j in 0..<pattern.first().length) {
            if (pattern[index - i][j] != pattern[index + i + 1][j]) {
                differencesIndex.add(mutableListOf(j, index - i))
            }
        }
    }

    return differencesIndex
}

fun findSmudgeVertically(pattern: MutableList<String>, index: Int): MutableList<MutableList<Int>> {
    val maxOffset = min(index, pattern.first().length - (index + 2))
    var differencesIndex = mutableListOf(mutableListOf<Int>())

    for (i in 0..maxOffset) {
        for (j in 0..<pattern.size) {
            if (pattern[j][index - i] != pattern[j][index + i + 1]) {
                differencesIndex.add(mutableListOf(index - i, j))
            }
        }
    }

    return differencesIndex
}

fun main() {
    var patterns = mutableListOf(mutableListOf<String>())
    var patternCounter = 0
    File("src/main/kotlin/day13/input.txt").forEachLine { line ->
        if (line == "") {
            patternCounter++
            patterns.add(mutableListOf<String>())
        } else {
            patterns[patternCounter].add(line)
        }
    }

    var result = 0


    var horizontalLines = mutableListOf<Int>()
    var verticalLines = mutableListOf<Int>()

    patterns.forEach { pattern ->

        for (i in 0..<pattern.size - 1) {
            val smudges = findSmudgeHorizontally(pattern, i)
            if (smudges.size - 1 == 1) {
                horizontalLines.add(i + 1)
                return@forEach
            }
        }

        for (i in 0..<pattern.first().length - 1) {
            for (j in 0..<pattern.size - 1) {
                val smudges = findSmudgeVertically(pattern, i)
                if (smudges.size - 1 == 1) {
                    verticalLines.add(i + 1)
                    return@forEach
                }
            }
        }

    }

    result += horizontalLines.sum() * 100
    result += verticalLines.sum()

    println("Result $result")

}