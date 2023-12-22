package day14

import java.io.File
import kotlin.math.min

fun findNextPosition(row: String, currentPosition: Int) : Int {
    var steps = 1
    while (true) {

        if (row[currentPosition - steps] != '.') {
            return currentPosition - steps + 1
        }
        if (currentPosition - steps <= 0)  return currentPosition - steps

        steps++
    }
}
fun main() {
    var result = 0
    val input = File("src/main/kotlin/day14/input.txt").readLines()

    println(input)

    val platform = mutableListOf<String>()

    for (i in 0..<input.first().length) {
        var line = ""
        for (j in 0 ..< input.size ) {
            line = line.plus(input[j][i])

        }
        platform.add(line)

    }

    val finalPlatform = mutableListOf<String>()
    val finalPositions = mutableListOf<Int>()
    println(platform)

    platform.forEachIndexed{index, row ->
        finalPlatform.add(row)
        for (i in 1..<row.length) {
            if (finalPlatform[index][i] == 'O' && finalPlatform[index][i - 1] == '.'){
                val nextPosition = findNextPosition(finalPlatform[index], i)
                finalPlatform[index] =finalPlatform[index].replaceRange(i, i + 1, ".")
                finalPlatform[index] = finalPlatform[index].replaceRange(nextPosition, nextPosition + 1, "O")

            }
        }
        finalPositions.addAll(Regex("O").findAll(finalPlatform[index]).map { finalPlatform[index].length - it.range.first }.toMutableList())
    }

    println(finalPlatform)
    println(finalPositions)

    result = finalPositions.sum()
    println("Result: $result")


}