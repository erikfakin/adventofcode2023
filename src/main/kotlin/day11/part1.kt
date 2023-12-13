package day11

import java.io.File
import kotlin.math.absoluteValue
import kotlin.math.sign


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


    emptyColumnsIndex.reversed().forEach { columnIndex ->

        for (i in input.size - 1 downTo 0) {
            input[i] = input[i].substring(0, columnIndex+1).plus(".").plus(input[i].substring(columnIndex +1, input[i].length))
        }
    }

    emptyRowsIndex.reversed().forEach { rowIndex ->
        input.add(rowIndex, ".".repeat(input.first().length))
    }

    input.forEach { println(it) }

    val stars = mutableListOf<IntArray>()
    input.forEachIndexed { rowIndex, row ->
        Regex("#").findAll(row).forEach { result ->
            stars.add(intArrayOf(result.range.first, rowIndex))
        }
    }

    stars.forEach { println("Star at ${it[0]} ${it[1]}") }

    val results = mutableListOf<Int>()

    for (i in 0..<stars.size) {
        for (j in i+1..<stars.size) {
            val star1 = stars[i]
            val star2 = stars[j]
            val dx = (star2[0] - star1[0]).absoluteValue
            val dy = (star2[1] - star1[1]).absoluteValue
            println("Star ${i + 1}: ${star1[0]} ${star1[1]}")
            println("Star ${j + 1}: ${star2[0]} ${star2[1]}")
            println("dx $dx")
            println("dy $dy")
            var steps = dx + dy

//            var currentPosition = intArrayOf(star1[0], star1[1])
//            println("Star 1 at ${currentPosition[0]} ${currentPosition[1]}")
//            println("Star 2 at ${star2[0]} ${star2[1]}")
//            while (currentPosition[0] != star2[0] || currentPosition[1] != star2[1]) {
//                if (currentPosition[0] != star2[0]) {
//                    currentPosition[0] += (star2[0] - currentPosition[0]).sign
//                    steps++
//                    println("Steps $steps")
//                    println("Currently at ${currentPosition[0]} ${currentPosition[1]}")
//                }
//
//                if (currentPosition[1] != star2[1]) {
//                    currentPosition[1] += (star2[1] - currentPosition[1]).sign
//                    steps++
//                    println("Steps $steps")
//                    println("Currently at ${currentPosition[0]} ${currentPosition[1]}")
//                }
//
//
//            }
//            println("Distance between star $i and star $j is $steps")
            results.add(steps)




        }
    }

    println("RESULTS")

    println(results.size)
    println(results.sum())







}