package day12

import java.io.File

fun countArrangements(map: String, pattern:List<Int>):Int {
    // Case we reach the end of the map and have no more pattern to match
    if (map.isEmpty()) {
        if (pattern.isEmpty()) {
            return 1
        } else {
            return 0
        }
    }

    if (pattern.isEmpty()) {
        if (map.contains('#')) {
            return 0
        } else {
            return 1
        }
    }

    val firstElement = map[0]
    var count = 0

    if (firstElement == '.' || firstElement == '?') {
        count += countArrangements(map.substring(1), pattern)

    }
    if (firstElement == '#' || firstElement == '?') {

        if (pattern[0] <= map.length && !map.substring(0, pattern[0]).contains('.')) {
            if (map.length == pattern[0]) {
                count += countArrangements(map.substring(pattern[0]), pattern.subList(1, pattern.size))
            } else if (map[pattern[0]] != '#'){
                count += countArrangements(map.substring(pattern[0] + 1), pattern.subList(1, pattern.size))
            }
        }

    }
    return count

}
fun main() {
    var res = 0

    File("src/main/kotlin/day12/input.txt").forEachLine { line ->

        val (row, patternString) = line.split(" ")
        val pattern = patternString.split(",").map { it.toInt() }
        res += countArrangements(row, pattern)


    }

    println("Count: $res")





}