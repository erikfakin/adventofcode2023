package day17

import java.io.File




fun main() {
    var result = 0
    val input = File("src/main/kotlin/day17/input.txt").readLines()
    input.forEach {
        println(it)
    }
    val seen = mutableSetOf<MutableList<Int>>()
    val priorityQueue = mutableListOf<MutableList<Int>>()
    priorityQueue.add(mutableListOf(0, 0, 0, 0, 0, 0))

    while (priorityQueue.isNotEmpty()) {
        priorityQueue.sortBy { it[0] }

        val current = priorityQueue.removeAt(0)
        val heatLoss = current[0]
        val column = current[1]
        val row = current[2]
        val dirColumn = current[3]
        val dirRow = current[4]
        val sameDirectionCount = current[5]

        if (row == input.size - 1 && column == input.first().length - 1) {
            result = heatLoss
            break
        }

        if (mutableListOf(column, row, dirColumn, dirRow, sameDirectionCount) in seen) {
            continue
        }
        seen.add(mutableListOf(column, row, dirColumn, dirRow, sameDirectionCount))

        val newColumn = column + dirColumn
        val newRow = row + dirRow

        if (newColumn < 0 || newColumn >= input.first().length || newRow < 0 || newRow >= input.size) {
            continue
        }
        var newHeatLoss = heatLoss + input[newRow][newColumn].toString().toInt()

        if (dirColumn == 0 && dirRow == 0 && newColumn == 0 && newRow == 0){
            newHeatLoss= 0
        }



        if (sameDirectionCount < 3 ) {
                priorityQueue.add(mutableListOf(newHeatLoss, newColumn, newRow, dirColumn, dirRow, sameDirectionCount + 1))
        }

        if (dirRow == 0) {
            priorityQueue.add(mutableListOf(newHeatLoss, newColumn, newRow, 0, 1, 1))
            priorityQueue.add(mutableListOf(newHeatLoss, newColumn, newRow, 0, -1, 1))
        }
        if (dirColumn == 0){
            priorityQueue.add(mutableListOf(newHeatLoss, newColumn, newRow, 1, 0, 1))
            priorityQueue.add(mutableListOf(newHeatLoss, newColumn, newRow, -1, 0, 1))
        }
    }

    println("Result: $result")
}