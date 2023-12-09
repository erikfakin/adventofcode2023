package day8.part1

import java.io.File
import kotlin.io.path.Path

class Node(val label: String, val leftNodeLabel: String, val rightNodeLabel: String) {
    override fun toString(): String {
        return "Node $label - left node : $leftNodeLabel, right node $rightNodeLabel"
    }
}



fun readFile(filename: String): Int {
    var sum = 0
    val input = File(filename).readLines()
    val instructions = input[0]
    val nodesList = mutableListOf<Node>()

    println(instructions)

    input.subList(2, input.size).forEach { line ->
        val results = Regex("(\\w{3})").findAll(line).toList()
        val nodeLabel = results[0].groupValues.first()
        val leftNodeLabel = results[1].groupValues.first()
        val rightNodeLabel = results[2].groupValues.first()
        val node = Node(nodeLabel, leftNodeLabel, rightNodeLabel)
        nodesList.add(node)
    }

    var currentNode = nodesList.find { node -> node.label == "AAA" }
    var currentInstructionIndex = 0

    println(nodesList)

    while (currentNode!!.label != "ZZZ") {
        val instruction = instructions[currentInstructionIndex]
        var node : Node
        if (instruction == 'L') {
            node = nodesList.find { node -> node.label == currentNode!!.leftNodeLabel }!!

        } else {
            node = nodesList.find { node -> node.label == currentNode!!.rightNodeLabel }!!
        }
        currentInstructionIndex = (currentInstructionIndex + 1) % instructions.length
        currentNode = node
        println(currentNode)
        sum++
    }

    return sum
}

fun main(args: Array<String>) {
    val inputPath = Path("src/main/kotlin/day8/part1/input.txt").toString()
    println("RESULT: ${readFile(inputPath)}")
}