package day10.part2

import java.io.File
import kotlin.io.path.Path


fun isEnclosed(
    pointPosition: IntArray,
    map: MutableList<MutableList<Char>>,
    notPathPoints: MutableList<IntArray>
): Boolean {
    /***
     * for each dot go right and then up. If we cross some pipes an odd number of times in each direction,
     * the dot is inside. If the number of times a pipe is crossed is even or 0, we are outside.
     */
    if (pointPosition[0] == map.first().size - 1) return false

    var counter = 0
    var bend = ""

    for (i in 0..<map.first().size - pointPosition[0]) {
        if (notPathPoints.none { point -> point[0] == pointPosition[0] + i && point[1] == pointPosition[1] }) {
            println("Path crossed at ${pointPosition[0] + i} ${pointPosition[1]} ")
            when (map[pointPosition[1]][pointPosition[0] + i]) {
                'F' -> if (bend == "") {
                    bend = "F"
                    continue
                }

                'J' -> if (bend == "F") {
                    counter++
                    bend = ""
                    continue
                }  else if (bend == "L") {
                    bend = ""
                    continue
                }

                'L' -> if (bend == "") {
                    bend = "L"
                    continue
                }

                '7' -> if (bend == "L") {
                    counter++
                    bend = ""
                    continue
                } else if (bend == "F" ) {
                    bend = ""
                    continue
                }
                'S' -> {
                    var isCharAboveConnectedToStart = false
                    var isCharBelowConnectedToStart = false
                    if (pointPosition[1] - 1 >= 0 && pointPosition[1] + 1 < map.size) {
                        val charBelow = map[pointPosition[1] + 1][pointPosition[0] + i]
                        val charAbove = map[pointPosition[1] - 1][pointPosition[0] + i]
                        val isCharBelowInPath =
                            notPathPoints.none { point -> point[0] == pointPosition[0] + i && point[1] == pointPosition[1] + 1 }
                        val isCharAboveInPath =
                            notPathPoints.none { point -> point[0] == pointPosition[0] + i && point[1] == pointPosition[1] - 1 }
                        isCharAboveConnectedToStart =
                            charArrayOf('|', '7', 'F').contains(charAbove) && isCharAboveInPath
                        isCharBelowConnectedToStart =
                            charArrayOf('|', 'J', 'L').contains(charBelow) && isCharBelowInPath
                    }



                    if(isCharAboveConnectedToStart && isCharBelowConnectedToStart){
                        // char is |
                        println("S is |")
                        counter++
                        continue
                    } else if (isCharAboveConnectedToStart) {
                        // either J or L
                        if (bend=="") {
                            println("S is L")
                            bend = "L"
                        } else {
                            println("S is J")
                            bend= "J"
                        }
                        continue
                    } else if (isCharBelowConnectedToStart) {
                        // either 7 or F
                        if (bend=="") {
                            println("S is F")
                            bend = "F"
                        } else {
                            println("S is 7")
                            bend = "7"
                        }
                        continue
                    }
                }

                '-' -> continue
            }

            println("sadf")


            counter++


        }

    }

    println(counter)

    return counter % 2 != 0
}

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


fun readFile(filename: String): Int {

    val map = mutableListOf<MutableList<Char>>()
    var index = 0
    var position = intArrayOf(0, 0)
    val currentDirections = mutableListOf<IntArray>()

    val notPathPoints = mutableListOf<IntArray>()
    val enclosedPoints = mutableListOf<IntArray>()




    File(filename).forEachLine { line ->
        map.add(line.toMutableList())
        if (line.contains('S', true)) {
            position = intArrayOf(line.indexOf('S'), index)
        }

        index++
    }

    map.forEachIndexed { rowIndex, row ->
        row.forEachIndexed { columnIndex, column ->
            notPathPoints.add(intArrayOf(columnIndex, rowIndex))
        }
    }

    directions.forEach { direction ->
        if (validateMove(position, direction, map)) currentDirections.add(direction)
    }

    val currentPositions = mutableListOf<IntArray>(position, position)
    notPathPoints.removeIf { point -> point[0] == currentPositions[0][0] && point[1] == currentPositions[0][1] }

    while (true) {

        currentDirections[0] = nextDirection(currentPositions[0], currentDirections[0], map)
        currentDirections[1] = nextDirection(currentPositions[1], currentDirections[1], map)

        currentPositions[0] = intArrayOf(
            currentPositions[0][0] + currentDirections[0][0],
            currentPositions[0][1] + currentDirections[0][1]
        )
        currentPositions[1] = intArrayOf(
            currentPositions[1][0] + currentDirections[1][0],
            currentPositions[1][1] + currentDirections[1][1]
        )


        notPathPoints.removeIf { point -> point[0] == currentPositions[0][0] && point[1] == currentPositions[0][1] }
        notPathPoints.removeIf { point -> point[0] == currentPositions[1][0] && point[1] == currentPositions[1][1] }

        if (currentPositions[0].contentEquals(currentPositions[1])) break

    }


    notPathPoints.forEach { println("not path at ${it[0]} ${it[1]}") }

    notPathPoints.forEach { point ->
        if (isEnclosed(point, map, notPathPoints)) enclosedPoints.add(point)
    }

    enclosedPoints.forEach { println("ECNLOSED POINT AT ${it[0]} ${it[1]}") }

    return enclosedPoints.size

}

fun main(args: Array<String>) {
    val inputPath = Path("src/day11.day11.main/kotlin/day10/part2/input.txt").toString()
    println("RESULT: ${readFile(inputPath)}")

}