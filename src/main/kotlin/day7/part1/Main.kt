package day7.part1

import java.io.File
import kotlin.io.path.Path
import kotlin.math.*


fun readFile(filename: String): Int {
    var sum = 1

    val input = File(filename).readLines()
    val timesList = Regex("(\\d+)").findAll(input[0]).map { it.groupValues.first().toInt() }.toList()
    val distancesList = Regex("(\\d+)").findAll(input[1]).map { it.groupValues.first().toInt() }.toList()

    distancesList.forEachIndexed{index, distance ->

        val det = sqrt(timesList[index].toDouble().pow(2) - 4*distance)

        val x1 = (timesList[index] - det)/2
        val x2 = (timesList[index] + det)/2

        val startInt = (floor(x1) + 1).toInt()
        val endInt = (ceil(x2) - 1).toInt()
//        if (x2 > timesList[index]) x2 = timesList[index].toDouble()
        println("X1 $x1 X2 $x2")
        println("startInt $startInt endInt $endInt")
        println("${floor(x2) - ceil(x1)}")
        sum *= endInt - startInt + 1

    }






    return sum

}


fun main(args: Array<String>) {
    val inputPath = Path("src/main/kotlin/day7/part1/input.txt").toString()
    println("RESULT: ${readFile(inputPath)}")
}