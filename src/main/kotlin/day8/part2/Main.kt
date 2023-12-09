package day8.part2

import java.io.File
import kotlin.io.path.Path

class Node(val label: String, val leftNodeLabel: String, val rightNodeLabel: String) {
    override fun toString(): String {
        return "Node $label - left node : $leftNodeLabel, right node $rightNodeLabel | "
    }
}

fun findLCM(inputArray: IntArray): Long {
    var lcm: Long = 1
    var divisor = 2
    while (true) {
        var counter = 0
        var divisible = false
        for (i in inputArray.indices) {

            // lcm_of_array_elements (n1, n2, ... 0) = 0.
            // For negative number we convert into
            // positive and calculate lcm_of_array_elements.
            if (inputArray[i] == 0) {
                return 0
            } else if (inputArray[i] < 0) {
                inputArray[i] = inputArray[i] * -1
            }
            if (inputArray[i] == 1) {
                counter++
            }

            // Divide element_array by devisor if complete
            // division i.e. without remainder then replace
            // number with quotient; used for find next factor
            if (inputArray[i] % divisor == 0) {
                divisible = true
                inputArray[i] = inputArray[i] / divisor
            }
        }

        // If divisor able to completely divide any number
        // from array multiply with lcm_of_array_elements
        // and store into lcm_of_array_elements and continue
        // to same divisor for next factor finding.
        // else increment divisor
        if (divisible) {
            lcm *= divisor
        } else {
            divisor++
        }

        // Check if all element_array is 1 indicate
        // we found all factors and terminate while loop.
        if (counter == inputArray.size) {
            return lcm
        }
    }
}



fun readFile(filename: String): Long {
    var sum = 0
    val input = File(filename).readLines()
    val instructions = input[0]
    val nodesList: MutableMap<String, List<String>> = mutableMapOf()

    println(instructions)

    input.subList(2, input.size).forEach { line ->
        val results = Regex("(\\w{3})").findAll(line).toList()
        val nodeLabel = results[0].groupValues.first()
        val leftNodeLabel = results[1].groupValues.first()
        val rightNodeLabel = results[2].groupValues.first()
        nodesList[nodeLabel] = listOf(leftNodeLabel, rightNodeLabel)
    }



    var startingNodes = nodesList.filterKeys { key -> key.endsWith('a', true) }
    println("Starint from $startingNodes")
    var currentInstructionIndex = 0

    println("All nodes $nodesList")

    /***find the numbers of steps to reach  the end for every path,
    we know that every path after it reaches the end starts at the begginign ***/

    val stepsPerNode: MutableList<Int> = MutableList(startingNodes.size) { 0 }
    var index = 0
    startingNodes.forEach{startingNode ->
        println(startingNode)
        currentInstructionIndex = 0
        var currentNode = startingNode
        var nextNode = currentNode
        while (true) {
            val instruction = instructions[currentInstructionIndex]

            if (instruction == 'L') {
               nextNode = nodesList.filterKeys { key -> key == currentNode.value[0] }.entries.first()
            } else {
                nextNode = nodesList.filterKeys { key -> key == currentNode.value[1] }.entries.first()
            }

            if(nextNode.key.endsWith('z', true)) {
                stepsPerNode[index]++
                break
            }



            currentInstructionIndex = (currentInstructionIndex + 1) % instructions.length
            currentNode = nextNode
            stepsPerNode[index]++

        }
        index++
    }

    /***
     * Find lcm of the steps for each path
     */
    println(stepsPerNode)
    var result = findLCM(stepsPerNode.toIntArray())
    return result

}

fun main(args: Array<String>) {
    val inputPath = Path("src/main/kotlin/day8/part2/input.txt").toString()
    println("RESULT: ${readFile(inputPath)}")
}