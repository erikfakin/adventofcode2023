package day5.part1

import java.io.File
import kotlin.io.path.Path


class MappedRange(val destinationRangeStart: Long, val sourceRangeStart: Long, val rangeLength: Long) {
    fun isSourceRangeMapped(value: Long): Boolean {
        if (value >= sourceRangeStart && value < sourceRangeStart + rangeLength) {
            println("isSourceRangeMapped value $value in range $sourceRangeStart ${sourceRangeStart + rangeLength}")
            return true
        }
        return false
    }

    fun covertSourceToDestination(value: Long): Long {
        if (!isSourceRangeMapped(value)) return value
        return (value - sourceRangeStart) + destinationRangeStart
    }

    override fun toString(): String {
        return "Mapped range: destinationRangeStart-$destinationRangeStart; sourceRangeStart-$sourceRangeStart; rangeLength-$rangeLength"
    }


}

fun readFile(filename: String): Long {

    val maps: MutableList<MutableList<MappedRange>> = mutableListOf()
    val seeds: MutableList<Long> = mutableListOf()

    val tempRangeList: MutableList<MappedRange> = mutableListOf()


    File(filename).forEachLine { line ->

        if (line.startsWith("seeds:")) {
            Regex("(\\d+)").findAll(line).forEach { result -> seeds.add((result.groupValues.first()).toLong()) }
            return@forEachLine
        }


        if (line.contains("map:")) {
            if (tempRangeList.isNotEmpty()) {
                maps.add(tempRangeList.toMutableList())
                tempRangeList.clear()
            }
        }

        val results = Regex("(\\d+)").findAll(line)
//        if (results.count() < 3) return@forEachLine

        var destinationRangeStart = 0L
        var sourceRangeStart = 0L
        var rangeLength = 0L
        results.forEachIndexed { index, matchResult ->
            if (index == 0) destinationRangeStart = matchResult.groupValues.first().toLong()
            if (index == 1) sourceRangeStart = matchResult.groupValues.first().toLong()
            if (index == 2) rangeLength = matchResult.groupValues.first().toLong()
        }

        val range = MappedRange(destinationRangeStart, sourceRangeStart, rangeLength)
        tempRangeList.add(range)

    }

    maps.add(tempRangeList.toMutableList())
    tempRangeList.clear()





    var minLocation = Long.MAX_VALUE

    seeds.forEach { seed ->
        var value = seed

        maps.forEachIndexed { i, map ->
            var index = 0
            for (mappedRange in map){
                index++
                if (mappedRange.isSourceRangeMapped(value)) {
                    val old = value

                    value = mappedRange.covertSourceToDestination(value)
                    println("CONVERTED FROM $old TO $value in coversion $i of the ranged map $index")
                    break
                }
            }
        }

        println()

        if (minLocation > value) minLocation=value


    }

    return minLocation

}


fun main(args: Array<String>) {
    val inputPath = Path("src/day11.day11.main/kotlin/day5/part1/input.txt").toString()
    println("RESULT: ${readFile(inputPath)}")
}