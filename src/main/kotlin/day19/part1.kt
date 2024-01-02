package day19

import java.io.File


fun main() {
    var result = 0
    var workflows = mutableMapOf<String, String>()
    var parts: MutableList<MutableMap<Char, Int>> = mutableListOf()
    var acceptedParts = parts.toMutableList()
    var inputBlock = "workflows"
    File("src/main/kotlin/day19/input.txt").forEachLine {
        if (it.isBlank()) {
            inputBlock = "parts"
            return@forEachLine
        }
        if (inputBlock == "workflows") {
            val workflowLabel = it.split("{")[0]
            workflows[workflowLabel] = Regex("\\{.+").find(it)!!.value
        } else if (inputBlock == "parts") {
            val part = mutableMapOf<Char, Int>()
            it.replace(Regex("[\\{\\}]"), "").split(",").forEach { r ->
                part[r.split("=").first().first()] = r.split("=").last().toInt()
            }
            parts.add(part)
        }
    }

    parts.forEach { part ->
        var currentWork = workflows["in"]
        while (true) {
            val conditions = currentWork!!.replace(Regex("[\\{\\}]"), "").split(",")
            for (i in 0..<conditions.size) {

                println(conditions[i])

                val nextStep = conditions[i].split(":").last()
                val condition = conditions[i].split(":").first()

                if (nextStep == condition) {
                    if (nextStep == "A") {
                        acceptedParts.add(part)
                        return@forEach
                    }
                    if (nextStep == "R") {
                        return@forEach
                    }
                    currentWork = workflows[nextStep]
                }

                if (condition.contains("<")) {
                    val attribute = condition.split("<").first().first()
                    val maxValue = condition.split("<").last().toInt()
                    println("Checking $attribute < $maxValue")
                    if (part[attribute]!! < maxValue) {
                        if (nextStep == "A") {
                            acceptedParts.add(part)
                            return@forEach
                        }
                        if (nextStep == "R") {
                            return@forEach
                        }
                        currentWork = workflows[nextStep]
                        break
                    }
                } else if (condition.contains(">")) {
                    val attribute = condition.split(">").first().first()
                    val minValue = condition.split(">").last().toInt()
                    println("Checking $attribute > $minValue")
                    if (part[attribute]!! > minValue) {
                        if (nextStep == "A") {
                            acceptedParts.add(part)
                            return@forEach
                        }
                        if (nextStep == "R") {
                            return@forEach
                        }
                        currentWork = workflows[nextStep]
                        println("Going to ${workflows[nextStep]}")
                        break
                    }
                }
            }
        }
    }

    acceptedParts.forEach { part ->
        part.forEach {
            result += it.value
        }
    }

    println("Result: $result")
}