package day12_2

import java.io.File

var cache = mutableMapOf<String, Long>()

fun countArrangements(map: String, pattern:List<Int>):Long {
    // Case we reach the end of the map and have no more pattern to match
    if (map.isEmpty()) {
        return if (pattern.isEmpty()) {
            1
        } else {
            0
        }
    }

    if (pattern.isEmpty()) {
        return if (map.contains('#')) {
            0
        } else {
            1
        }
    }

    val key = "$map%$pattern"

    if (cache.containsKey(key)) {
        return cache[key]!!
    }


    val firstElement = map[0]
    var count = 0L

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
    cache[key] = count

    return count

}
fun main() {
    var res = 0L

    File("src/main/kotlin/day12/input.txt").forEachLine { line ->

        var (row, patternString) = line.split(" ")

        row = (row.plus("?")).repeat(5).dropLast(1)
        patternString = patternString.plus(",").repeat(5).dropLast(1)


        var pattern = patternString.split(",").map { it.toInt() }
        res += countArrangements(row, pattern)


    }

    println("Count: $res")





}