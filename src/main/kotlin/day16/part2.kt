package day16_2

import java.io.File

val visited = mutableSetOf<Pair<Int, Int>>()
var tempCache = mutableListOf<String>()

fun march(direction: Pair<Int, Int>, position: Pair<Int, Int>, map: List<String>) {

    var currentPosition = position.copy()

    while (true) {
        val newPosition = Pair(currentPosition.first + direction.first, currentPosition.second + direction.second)
        if (newPosition.first >= map.first().length || newPosition.first < 0) {
            return
        }
        if (newPosition.second >= map.size || newPosition.second < 0) {
            return
        }
        if (tempCache.contains("$newPosition$direction")) {
            return
        }
        tempCache.add("$newPosition$direction")
        visited.add(newPosition)


        when (map[newPosition.second][newPosition.first]) {
            '/' -> {
                if (direction.first != 0) {
                    march(Pair(0, -direction.first), newPosition, map)
                    return
                } else {
                    march(Pair(-direction.second, 0), newPosition, map)
                    return
                }
            }
            '\\' -> {
                if (direction.first != 0) {
                    march(Pair(0, direction.first), newPosition, map)
                    return
                } else {
                    march(Pair(direction.second, 0), newPosition, map)
                    return
                }
            }
            '-' -> {
                if (direction.first == 0 && direction.second != 0) {
                    march(Pair(-1, 0), newPosition, map)
                    march(Pair(1, 0), newPosition, map)
                    return
                }
            }
            '|' -> {
                if (direction.second == 0) {
                    march(Pair(0, -1), newPosition, map)
                    march(Pair(0, 1), newPosition, map)
                    return
                }
            }
        }
        currentPosition = newPosition
    }


}

fun main() {
    var result = 0
    val input = File("src/main/kotlin/day16/input.txt").readLines()
    input.forEach {
        println(it)
    }

    val startingPositions = mutableListOf<Pair<Int, Int>>()

    for (i in 0..<input.size) {

            startingPositions.add(Pair(-1, i))

    }

    startingPositions.forEach { startingPosition ->
        var initialDirection = Pair(1, 0)
        tempCache = mutableListOf()
        visited.clear()
        march(initialDirection, startingPosition, input)
        if (result < visited.size) {
            result = visited.size
        }

        initialDirection = Pair(-1, 0)
        tempCache = mutableListOf()
        visited.clear()
        march(initialDirection, Pair(input[0].length, startingPosition.second), input)
        if (result < visited.size) {
            result = visited.size
        }
    }

    startingPositions.clear()

    for (i in 0..<input[0].length) {
        startingPositions.add(Pair(i, -1))
    }

    startingPositions.forEach { startingPosition ->
        var initialDirection = Pair(0, 1)
        tempCache = mutableListOf()
        visited.clear()
        march(initialDirection, startingPosition, input)
        if (result < visited.size) {
            result = visited.size
        }

        initialDirection = Pair(0, -1)
        tempCache = mutableListOf()
        visited.clear()
        march(initialDirection, Pair(startingPosition.first, input.size), input)
        if (result < visited.size) {
            result = visited.size
        }
    }

    println("Result: $result")
}