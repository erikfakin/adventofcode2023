package day21

import java.io.File



fun main() {
    var result = 0

    val maxSteps = 64

    var map = File("src/main/kotlin/day21/input.txt").readLines()
    var stepsQueue = mutableListOf<List<Int>>()

    var visitedPositions = mutableSetOf<Pair<Int, Int>>()

    var startingPos = Pair(0,0)


    for (y in 0..<map.size){
        val x = map[y].indexOf('S')
        if (x != -1) {
           stepsQueue.add(listOf(x, y, 0))
            startingPos = Pair(x, y)
            break
        }
    }

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

        if (currentSteps >= maxSteps) {
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
    val oddOrEven = maxSteps % 2
    result = visitedPositions.count { pos ->
        ((startingPos.first - pos.first) + (startingPos.second - pos.second)) % 2 == oddOrEven
    }


    println("Result: $result")
}