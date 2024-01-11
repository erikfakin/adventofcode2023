package day21

import java.io.File
import kotlin.math.*


fun march(startPosition: Pair<Int, Int>, numberOfSteps: Int, map: List<String>):Long {
    var stepsQueue = mutableListOf<List<Int>>()
    var visitedPositions = mutableSetOf<Pair<Int, Int>>()

    stepsQueue.add(listOf(startPosition.first, startPosition.second, 0))


    val possibleDirections = listOf(
        Pair(0, -1),
        Pair(0, 1),
        Pair(-1, 0),
        Pair(1, 0)
    )

    while (stepsQueue.isNotEmpty()) {
        val (currentX, currentY, currentSteps) = stepsQueue.removeAt(0)

        if (visitedPositions.contains(Pair(currentX, currentY))) {
            continue
        }

        visitedPositions.add(Pair(currentX, currentY))

        if (currentSteps >= numberOfSteps) {
            continue
        }

        for ((dx, dy) in possibleDirections) {
            val newX = currentX + dx
            val newY = currentY + dy
            if (newX < 0 || newY < 0 || newX >= map.first().length || newY >= map.size) {
                continue
            }
            if (map[newY][newX] == '#') {
                continue
            }
            stepsQueue.add(listOf(newX, newY, currentSteps + 1))
        }

    }

    /**
     * If we make n steps where n is even or odd then we difference between our pos and the staring pos must be even
     * or odd respectively.
     */
    val oddOrEven = numberOfSteps % 2
    return visitedPositions.count { pos ->
        (abs(pos.first - startPosition.first) + abs(pos.second - startPosition.second)) % 2 == oddOrEven
    }.toLong()

}


fun main() {
    var result = 0L

    val steps = 26501365

    var map = File("src/main/kotlin/day21/input.txt").readLines()

    var startingPos = Pair(0,0)


    for (y in 0..<map.size){
        val x = map[y].indexOf('S')
        if (x != -1) {
            startingPos = Pair(x, y)
            break
        }
    }

    val numberOfMapOddPositions = march(startingPos, map.size * 2 + 1, map)
    val numberOfMapEvenPositions = march(startingPos, map.size * 2, map)

    /**
     * numberOfMapsMarched marching in a single direction. Ex. only right from the start
     */
    val numberOfMapsMarched = (steps - (map.size - 1) / 2) / map.size

    /**
     * We now consider only the even and odd full maps marched, every possible position on that map is reached.
     * We discard the border of maps not full.
    **/

    val oddMaps = (floor(((numberOfMapsMarched - 1) / 2).toDouble())*2 + 1).pow(2).toLong()
    val evenMaps = (floor(((numberOfMapsMarched) / 2).toDouble())*2).pow(2).toLong()

    println("Odd maps $oddMaps")
    println("Even maps $evenMaps")



    println("Number of positions odd $numberOfMapOddPositions")
    println("Number of positions even $numberOfMapEvenPositions")
    println("Number of maps marched $numberOfMapsMarched")



    val topCorner = march(Pair(startingPos.first, map.size - 1), map.size - 1, map)
    val rightCorner = march(Pair(0, startingPos.second), map.size - 1, map)
    val bottomCorner = march(Pair(startingPos.first, 0), map.size - 1, map)
    val leftCorner = march(Pair(map.size - 1, startingPos.second), map.size - 1, map)

    /**
     * Border diagonal pieces of two types, one almost ful, the other almost empty.
     * We have different variants based on the position top-right, bottom-right, bottom-left, top-left.
     */
    val stepsSmallVariant = floor(map.size.toFloat() / 2).toInt() - 1
    val smallTopRight = march(Pair(0, map.size - 1), stepsSmallVariant, map)
    val smallBottomRight = march(Pair(0, 0), stepsSmallVariant, map)
    val smallBottomLeft = march(Pair(map.size - 1, 0), stepsSmallVariant, map)
    val smallTopLeft = march(Pair(map.size - 1, map.size - 1), stepsSmallVariant, map)


    val stepsLargeVariant =  stepsSmallVariant + map.size

    println("steps small variant $stepsSmallVariant")
    println("steps large variant $stepsLargeVariant")

    val largeTopRight = march(Pair(0, map.size - 1), stepsLargeVariant, map)
    val largeBottomRight = march(Pair(0, 0), stepsLargeVariant, map)
    val largeBottomLeft = march(Pair(map.size - 1, 0), stepsLargeVariant, map)
    val largeTopLeft = march(Pair(map.size - 1, map.size - 1), stepsLargeVariant, map)



    result = (numberOfMapOddPositions * oddMaps +
            numberOfMapEvenPositions * evenMaps)+
            topCorner + rightCorner + bottomCorner + leftCorner +
            (numberOfMapsMarched ) * (smallTopLeft + smallTopRight + smallBottomLeft + smallBottomRight) +
            (numberOfMapsMarched -1 ) * (largeTopLeft + largeTopRight + largeBottomLeft + largeBottomRight)







    println("Result: $result")
}