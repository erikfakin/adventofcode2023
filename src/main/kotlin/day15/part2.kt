package day15

import java.io.File

val boxesList = MutableList(256){
    mutableListOf<Pair<String, Int>>()
}

fun hash(label: String): Int {
    var boxNumber = 0
    label.forEach { char ->
        val ascii = char.code
        boxNumber += ascii
        boxNumber *= 17
        boxNumber %= 256
    }
    return boxNumber
}

fun main() {
    var result = 0
    val input = File("src/main/kotlin/day15/input.txt").readText()
    val steps = input.split(",").map { it.trim() }.toList()
    steps.forEach { step ->
        var boxNumber = 0
        var label = ""
        var focalLength = 0
        if (step.contains("-")) {
            label = step.split("-")[0]
            boxNumber = hash(label)
            boxesList[boxNumber].removeIf { it.first == label }
        } else if (step.contains("=")) {
            label = step.split("=")[0]
            focalLength = step.split("=")[1].toInt()
            boxNumber = hash(label)
            val indexOfLens = boxesList[boxNumber].indexOfFirst { it.first == label }
            if (indexOfLens != -1) {
                boxesList[boxNumber][indexOfLens] = Pair(label, focalLength)
            } else {
                boxesList[boxNumber].add(Pair(label, focalLength))
            }
        }
    }
    boxesList.forEachIndexed { boxIndex, box ->
        if (box.isNotEmpty()) {
           box.forEachIndexed { lensPosition, lens ->
               result += (boxIndex + 1) * (lensPosition + 1) * lens.second }
        }
    }
    println("Result: $result")
}