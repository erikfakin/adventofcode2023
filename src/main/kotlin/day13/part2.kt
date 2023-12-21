package day13_2

import java.io.File
import kotlin.math.min

fun isMirroredHorizontally(pattern: MutableList<String>, index: Int): Boolean {
    val maxOffset = min(index, pattern.size - (index + 2))
    for (i in 0..maxOffset) {
        if (pattern[index - i] != pattern[index + i + 1]) {
            println("${pattern[index - i]} and ${pattern[index + i + 1]} are not equal" )
            return false
        }
    }
    return true

}
fun isMirroredVertically(pattern: MutableList<String>, index: Int): Boolean {
    val maxOffset = min(index, pattern.first().length - (index + 2))
    for (i in 0..maxOffset) {
        for (j in 0 ..< pattern.size) {
            if (pattern[j][index - i] != pattern[j][index + i + 1]) {
                println("${pattern[j][index - i]} and ${pattern[j][index + i + 1]} are not equal" )

                return false
            }
        }
    }
    return true

}

fun findSmudge(pattern: MutableList<String>, index: Int): IntArray{



}
fun main() {
    var patterns = mutableListOf(mutableListOf<String>())
    var patternCounter = 0
    val input = File("src/main/kotlin/day13/input.txt").forEachLine { line ->
        if (line =="") {
            patternCounter++
            patterns.add(mutableListOf<String>())
        }   else {
            patterns[patternCounter].add(line)
        }
    }

    var result = 0

    var smudgesLocations = mutableListOf(intArrayOf())

    patterns.forEachIndexed{ index, pattern ->

        println("Index $index smudge at ${findSmudge(pattern).first()} ${findSmudge(pattern).last()}")

    }


    patterns.forEachIndexed { index, pattern ->
        for (i in 0..<pattern.size - 1) {

            if (pattern[i] == pattern[i+1]) {

                if (isMirroredHorizontally(pattern, i)) result += (i + 1) * 100

            }
        }

        for (i in 0..<pattern.first().length - 1) {
            for (j in 0..<pattern.size - 1) {
                if (pattern[j][i] != pattern[j][i+1]) {
                    break
                }
                if (j == pattern.size - 2) {

                    if(isMirroredVertically(pattern, i)) {result += i + 1}

                }
            }
        }


    }

    println("Result: $result")






}