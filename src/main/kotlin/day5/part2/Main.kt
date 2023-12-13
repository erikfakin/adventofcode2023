package day5.part2

import java.io.File
import kotlin.io.path.Path


class MappedRange(val destinationRangeStart: Long, val sourceRangeStart: Long, val rangeLength: Long) {
    fun isSourceRangeMapped(value: Long): Boolean {
        return value >= sourceRangeStart && value < sourceRangeStart + rangeLength
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
            val tempSeeds: MutableList<Long> = mutableListOf()
            Regex("(\\d+)").findAll(line).forEach {
                seeds.add(it.groupValues.first().toLong())
//                result -> seeds.add((result.groupValues.first()).toLong())
                }

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

    for (i in 0..<seeds.size step 2 ) {

        for (j in 0..<seeds[i+1]) {
            var value = seeds[i] + j
            maps.forEach { map ->

                for (mappedRange in map){
                    if (mappedRange.isSourceRangeMapped(value)) {
                        value = mappedRange.covertSourceToDestination(value)
                        break
                    }
                }
            }
            if (minLocation > value) minLocation=value
        }










    }


    return minLocation

}


fun main(args: Array<String>) {
    val inputPath = Path("src/day11.day11.main/kotlin/day5/part2/input.txt").toString()
    println("RESULT: ${readFile(inputPath)}")
}