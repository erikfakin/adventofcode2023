package day18

import java.io.File

fun findFieldSize(steps: List<String>): List<Int> {
    var width = 0
    var height = 0

    var maxWidth = 0
    var maxHeight = 0

    var minWidth = 0
    var minHeight = 0

    for (step in steps) {
        val direction = step.split(" ")[0]
        val distance = step.split(" ")[1].toInt()
        when (direction) {
            "R" -> {
                width += distance
                if (width > maxWidth) {
                    maxWidth = width
                }
            }
            "L" -> {
                width -= distance
                if (width < minWidth) {
                    minWidth = width
                }
            }
            "U" -> {
                height -= distance
                if (height < minHeight) {
                    minHeight = height
                }
            }
            "D" -> {
                height += distance
                if (height > maxHeight) {
                    maxHeight = height
                }
            }
        }
    }

    return listOf(minWidth, maxWidth, minHeight, maxHeight)
}

fun digFieldBorders(steps: List<String>): MutableList<String> {
    var (minWidth, maxWidth, minHeight, maxHeight) = findFieldSize(steps)

    val field = MutableList(maxHeight - minHeight + 1) { ".".repeat(maxWidth - minWidth + 1) }

    var currentPosition = mutableListOf(-minWidth, -minHeight)
    steps.forEach { step ->
        val direction = step.split(" ")[0]
        val distance = step.split(" ")[1].toInt()
        var dx = 0
        var dy = 0

        println("Current Position $currentPosition")
        println("step $step")

        when (direction) {
            "R" -> {
                dx = 1
            }
            "L" -> {
                dx = -1
            }
            "U" -> {
                dy = -1
            }
            "D" -> {
                dy = 1
            }
        }

        for (i in 1..distance) {
            currentPosition[0] += dx
            currentPosition[1] += dy


            val sb = StringBuilder(field[currentPosition[1]])
            sb.setCharAt(currentPosition[0], '#')

            field[currentPosition[1]] = sb.toString()
        }
    }

    return field
}


fun main() {
    var result = 0
    val steps = File("src/main/kotlin/day18/input.txt").readLines()
    val field = digFieldBorders(steps)

    for (i in 1..<field.size - 1) {
        var replacedRow = field[i]
        Regex("#{2,}").findAll(field[i]).forEach { match ->
            val start = match.range.first
            val end = match.range.last
            val charAboveStart = field[i - 1].elementAt(start)
            val charAboveEnd = field[i - 1].elementAt(end)
            val charBelowStart = field[i + 1].elementAt(start)
            val charBelowEnd = field[i + 1].elementAt(end)

            if ((charAboveStart == '#' && charAboveEnd == '#') || (charBelowStart == '#' && charBelowEnd == '#')) {
                replacedRow = replacedRow.replaceRange(start..end, "-".repeat(end - start + 1))
            } else {
                replacedRow = replacedRow.replaceRange(start + 1..end, "-".repeat(end - start))
            }
        }

        for (j in 0..<field.first().length) {
            if (replacedRow[j] != '.') {
                continue
            }

            val numberOfWalls = replacedRow.slice(0..j).count { it == '#' }
            if (numberOfWalls % 2 == 1) {
                val sb = StringBuilder(field[i])
                sb.setCharAt(j, '+')
                field[i] = sb.toString()
            }

        }

    }
    field.forEach {
        println(it)
        result += it.count { char -> char != '.' }
    }
    println("Result: $result")
}