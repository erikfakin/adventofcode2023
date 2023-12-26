package day14_2

import java.io.File

var cache = mutableMapOf<String, MutableList<String>>()
val numberOfCicles = 1000000000
var repeatingFrom = 1
var repeatingTo = 1

var platformsSet = mutableSetOf<MutableList<String>>()

fun moveRocks(platform: MutableList<String>, direction: String): MutableList<String> {

    var newPlatform = platform
    var rangeX: IntProgression = 0..0
    var rangeY: IntProgression = 0..0
    var rollingY = 0
    var rollingX = 0

    when (direction) {
        "north" -> {
            rangeX = 0..<platform.first().length
            rangeY = 1..<platform.size
            rollingY = -1
        }
        "east" -> {
            rangeX = platform.first().length - 2 downTo 0
            rangeY = 0..<platform.size
            rollingX = 1
        }
        "south" -> {
            rangeX = 0 ..< platform.first().length
            rangeY = platform.size - 2 downTo 0
            rollingY = 1

        }
        "west" -> {
            rangeX = 1..<platform.first().length
            rangeY = 0..<platform.size
            rollingX = -1
        }
    }

    if (rollingY != 0) {
        println("going $direction")
        for (x in rangeX) {
            for (y in rangeY) {
                if (newPlatform[y][x] == 'O') {
                    var steps = 1
                    while (y + steps * rollingY >= 0 &&
                        y + steps * rollingY < newPlatform.size &&
                        newPlatform[y + steps * rollingY][x] == '.'
                    ) {
                        steps++
                    }
                    newPlatform[y] = newPlatform[y].replaceRange(x, x + 1, ".")
                    newPlatform[y + (steps - 1) * rollingY] =
                        newPlatform[y + (steps - 1) * rollingY].replaceRange(x, x + 1, "O")
                }
            }
        }
    } else {
        for (y in rangeY) {
            for (x in rangeX) {
                if (newPlatform[y][x] == 'O') {
                    var steps = 1
                    while (x + steps * rollingX >= 0 &&
                        x + steps * rollingX < newPlatform.first().length &&
                        newPlatform[y][x + steps * rollingX] == '.'
                    ) {
                        steps++
                    }
                    newPlatform[y] = newPlatform[y].replaceRange(x, x + 1, ".")
                    newPlatform[y] =
                        newPlatform[y].replaceRange(x + (steps - 1) * rollingX, x + (steps - 1) * rollingX + 1, "O")
                }
            }
        }
    }

    return newPlatform
}

fun cycle(platform: MutableList<String>): MutableList<String> {

    var newPlatform = platform.toMutableList()
    newPlatform = moveRocks(newPlatform, "north")
    newPlatform = moveRocks(newPlatform, "west")
    newPlatform = moveRocks(newPlatform, "south")
    newPlatform = moveRocks(newPlatform, "east")

    return newPlatform
}

fun main() {
    var result = 0
    val input = File("src/main/kotlin/day14/input.txt").readLines()
    var platform = input.toMutableList()

    for (i in 1..numberOfCicles) {
        if (cache.containsKey(platform.toString())) {
            if (repeatingTo == repeatingFrom) {
                repeatingFrom = platformsSet.indexOf(cache[platform.toString()]!!)
                repeatingTo = i - 2
                break
            }
        }
        val oldPlatform = platform.toMutableList()
        platform = cycle(platform)
        cache[oldPlatform.toString()] = platform
        platformsSet.add(platform)
    }

    val finalPlatformIndex = (numberOfCicles - repeatingFrom) % (repeatingTo - repeatingFrom + 1) + repeatingFrom
    val finalPlatform = platformsSet.elementAt(finalPlatformIndex - 1)

    finalPlatform.forEachIndexed { index, row ->
        val rocks = row.count { c -> c == 'O' }
        result += rocks * (platform.size - index)
    }

    println("Result: $result")
}