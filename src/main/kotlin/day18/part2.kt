package day18_2

import java.io.File
import kotlin.math.abs

fun getVertices(steps: List<String>): List<Pair<Long, Long>> {
    var vertices = mutableListOf<Pair<Long, Long>>()
    vertices.add(Pair(0, 0))
    steps.forEach { instruction ->
        val distance = instruction.split(" ")[2].substring(2, 7).toLong(radix = 16)
        val direction = instruction.split(" ")[2][7]
        val prevVer = vertices.last()

        when (direction) {
            '0' -> {
                vertices.add(Pair(prevVer.first + distance, prevVer.second))
            }
            '1' -> {
                vertices.add(Pair(prevVer.first, prevVer.second + distance))
            }
            '2' -> {
                vertices.add(Pair(prevVer.first - distance, prevVer.second))
            }
            '3' -> {
                vertices.add(Pair(prevVer.first, prevVer.second - distance))
            }
        }
    }

    return vertices.toList()
}


fun main() {
    var internalArea = 0L
    val steps = File("src/main/kotlin/day18/input.txt").readLines()
    val vertices = getVertices(steps)
    var perimeter = 0L

    for (i in 0..<vertices.size - 1) {
        perimeter += abs(vertices[i].first - vertices[i + 1].first) + abs(vertices[i].second - vertices[i + 1].second)
        internalArea += (vertices[i].first + vertices[i + 1].first) * (vertices[i].second - vertices[i + 1].second)
    }

    /**
     * The area above considers the central coordinate of the dug unit, the center of the dug square unit.
     * We miss the external half on the dig unit.
     * We can use Pick's theorem to get the area of the polygon inspide, counting the
     * number of points inside the polygon and on the border of the polygon.
     * Then we can add the number of boundary points to the result.
     */

    /**
     * Using Pick's theorem on the result to get the area of the polygon
     * https://en.wikipedia.org/wiki/Pick%27s_theorem
     * A = i + b/2 - 1
     * i = number of points inside the polygon
     * b = number of points on the border of the polygon
     * A = area of the polygon
     */

    internalArea = (abs(internalArea) * 0.5).toLong()
    val i = internalArea - (perimeter / 2) + 1
    val result = i + perimeter

    println("Result: $result")
}