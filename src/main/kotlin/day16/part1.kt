package day16

import java.io.File

val visited = mutableSetOf<Pair<Int, Int>>()
var cache = mutableListOf<String>()

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
        if (cache.contains("$newPosition$direction")) {
            return
        }
        cache.add("$newPosition$direction")
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

    val initialPosition = Pair(-1, 0)
    val initialDirection = Pair(1, 0)

    cache.add("$initialPosition$initialDirection")

    march(initialDirection, initialPosition, input)

    result = visited.size
    println("Result: $result")
}