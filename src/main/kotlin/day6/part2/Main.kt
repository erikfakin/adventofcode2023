package day6.part2

import java.io.File
import kotlin.io.path.Path
import kotlin.math.*


fun readFile(filename: String): Int {

    val input = File(filename).readLines()
    val time = Regex("(\\d+)").findAll(input[0]).map { it.groupValues.first().toInt() }.joinToString("").toDouble()
    val distance = Regex("(\\d+)").findAll(input[1]).map { it.groupValues.first().toInt() }.joinToString("").toDouble()

    val det = sqrt(time.pow(2) - 4 * distance)
    val x1 = (time - det) / 2
    val x2 = (time + det) / 2

    val startInt = (floor(x1) + 1).toInt()
    val endInt = (ceil(x2) - 1).toInt()

    return endInt - startInt + 1

}


fun main(args: Array<String>) {
    val inputPath = Path("src/day11.day11.main/kotlin/day6/part2/input.txt").toString()
    println("RESULT: ${readFile(inputPath)}")
}