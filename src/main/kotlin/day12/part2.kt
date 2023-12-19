package day12

import java.io.File
import kotlin.math.pow


fun main() {

    var result = 0

    val patterns = mutableListOf<MutableList<Int>>()
    val rows = mutableListOf<String>()

    File("src/main/kotlin/day12/input.txt").forEachLine { line ->
        rows.add(line.split(" ")[0])
        patterns.add(line.split(" ")[1].split(",").map { it.toInt() }.toMutableList())

    }

    val tempRows = mutableListOf<String>()

    rows.forEach { row ->
        tempRows.add(
            row.replace(Regex("^\\.+"), "").replace(Regex("\\.+$"), "")
                .replace(Regex("\\.+"), ".")

        )
    }

    tempRows.forEachIndexed {rowIndex, row ->
        val unknownPositions = Regex("\\?").findAll(row).map { it.range.first }.toList()
        val numberOfPossibleArrangements = 2f.pow(unknownPositions.size).toInt()
        println("Possible combinations: $numberOfPossibleArrangements")
        for (i in 0..<numberOfPossibleArrangements) {
            val combination = i.toString(2).padStart(unknownPositions.size, '0')
            var tempString = row
            /*** Map the combination 0 and 1 to the right ? in order, 0 means ., 1 means # */
            combination.forEachIndexed { index, c ->
                tempString = if (c == '1') {
                    tempString.replaceRange(unknownPositions[index], unknownPositions[index] + 1, "#")
                } else {
                    tempString.replaceRange(unknownPositions[index], unknownPositions[index] + 1, ".")
                }
            }
            val results = Regex("#+").findAll(tempString).map { it.range.last - it.range.first + 1 }.toList()

            if (results == patterns[rowIndex]) result++

        }

    }

    println("Result: $result")




}