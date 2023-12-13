package day10.part1

import java.io.File
import kotlin.io.path.Path

val directions = listOf(
    intArrayOf(1, 0),
//    intArrayOf(1, -1),
    intArrayOf(0, -1),
//    intArrayOf(-1, -1),
    intArrayOf(-1, 0),
//    intArrayOf(-1, 1),
    intArrayOf(0, 1),
//    intArrayOf(1, 1),
)


fun nextDirection(position: IntArray, direction: IntArray, map: MutableList<MutableList<Char>>): IntArray {
    val tile = map[position.last()][position.first()]
    var nextDirection = direction

    when (tile) {
        '-' -> nextDirection = direction
        '|' -> nextDirection = direction
        'L' -> if (direction[1] == 1)
            nextDirection = intArrayOf(1, 0) else if (direction[0] == -1) nextDirection = intArrayOf(0, -1)

        'J' -> if (direction[1] == 1)
            nextDirection = intArrayOf(-1, 0) else if (direction[0] == 1) nextDirection = intArrayOf(0, -1)

        '7' -> if (direction[1] == -1)
            nextDirection = intArrayOf(-1, 0) else if (direction[0] == 1) nextDirection = intArrayOf(0, 1)

        'F' -> if (direction[1] == -1)
            nextDirection = intArrayOf(1, 0) else if (direction[0] == -1) nextDirection = intArrayOf(0, 1)
    }
    println("Next direction ${nextDirection[0]}  ${nextDirection[1]}")
    return nextDirection
}

fun validateMove(position: IntArray, direction: IntArray, map: MutableList<MutableList<Char>>): Boolean {
    if ((position.last() + direction.last() < 0 || position.last() + direction.last() > map.size - 1) ||
        position.first() + direction.first() < 0 || position.first() + direction.first() > map[0].size - 1
    ) return false
    val tile = map[position.last() + direction.last()][position.first() + direction.first()]
    var valid = false
    when (tile) {
        '-' -> if (direction[1] == 0) valid = true
        '|' -> if (direction[0] == 0) valid = true
        'L' -> if (direction[1] == 1 || direction[0] == -1) valid = true
        'J' -> if (direction[1] == 1 || direction[0] == 1) valid = true
        '7' -> if (direction[1] == -1 || direction[0] == 1) valid = true
        'F' -> if (direction[1] == -1 || direction[0] == -1) valid = true
    }
    println("$tile -> $valid")
    return valid
}


fun readFile(filename: String): Long {

    val map = mutableListOf<MutableList<Char>>()
    var index = 0
    var position = intArrayOf(0, 0)
    var currentDirections = mutableListOf<IntArray>()
    File(filename).forEachLine { line ->
        map.add(line.toMutableList())
        if (line.contains('S', true)) {
            position = intArrayOf(line.indexOf('S'), index)
        }

        index++
    }

    directions.forEach { direction ->
        if (validateMove(position, direction, map)) currentDirections.add(direction)
    }

    var currentPositions = mutableListOf<IntArray>(position, position)

    var steps = 0L
    while (true) {

        currentDirections[0] = nextDirection(currentPositions[0], currentDirections[0], map)
        currentDirections[1] = nextDirection(currentPositions[1], currentDirections[1], map)

        currentPositions[0] = intArrayOf(currentPositions[0][0] + currentDirections[0][0], currentPositions[0][1] + currentDirections[0][1])
        currentPositions[1] = intArrayOf(currentPositions[1][0] + currentDirections[1][0], currentPositions[1][1] + currentDirections[1][1])

        steps++

        if (currentPositions[0].contentEquals(currentPositions[1])) break

    }

    return steps
}

fun main(args: Array<String>) {
    val inputPath = Path("src/main/kotlin/day10/part1/input.txt").toString()
    println("RESULT: ${readFile(inputPath)}")
}