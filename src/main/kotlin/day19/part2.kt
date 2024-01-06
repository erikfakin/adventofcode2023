package day19_2

import java.io.File

fun count(ranges: Map<Char, Pair<Int, Int>>, workflowLabel: String, workflows: MutableMap<String, List<String>>): Long {

    /**
     * Base cases "R" and "A" to stop the recursion.
     */
    if (workflowLabel == "R") {
        return 0L
    }

    if (workflowLabel == "A") {
        var count = 1L
        ranges.forEach { (key, value) ->
            count *= value.second - value.first + 1
        }
        return count
    }

    val rules = workflows[workflowLabel]!!.subList(0, workflows[workflowLabel]!!.size - 1)
    val fallback = workflows[workflowLabel]!!.last()

    var total = 0L
    var currentRanges = ranges.toMutableMap()

    for (i in 0..<rules.size) {

        val (condition, nextWorkflow) = rules[i].split(":")
        val attribute = condition[0]
        val comparisonType = condition[1]
        val comparisonTo = condition.substring(2).toInt()

        var falsePart: Pair<Int, Int>
        var truePart: Pair<Int, Int>

        if (comparisonType == '<') {
            truePart = Pair(currentRanges[attribute]!!.first, comparisonTo - 1)
            falsePart = Pair(comparisonTo, currentRanges[attribute]!!.second)
        } else {
            falsePart = Pair(currentRanges[attribute]!!.first, comparisonTo)
            truePart = Pair(comparisonTo + 1, currentRanges[attribute]!!.second)
        }
        /**
         * A range that in the comparison results true.
         * We call the next workflow with this range.
         */
        if (truePart.first <= truePart.second) {
            val rangesCopy = currentRanges.toMutableMap()
            rangesCopy[attribute] = truePart
            total += count(rangesCopy, nextWorkflow, workflows)
        }
        /**
         * A range that in the comparison results false.
         * We update the range and pass it to the next rule in this workflow.
         */
        if (falsePart.first <= falsePart.second) {
            currentRanges[attribute] = falsePart
        }
    }
    /**
     * For the ranges that were left after the rules, we call the fallback rule.
     */
    total += count(currentRanges, fallback, workflows)

    return total

}

fun main() {
    var result = 0L
    var workflows = mutableMapOf<String, List<String>>()

    val input = File("src/main/kotlin/day19/input.txt").readLines()

    for (i in 0..<input.size) {
        if (input[i].isBlank()) break
        val (workflowLabel, workflowRules) = input[i].replace("}", "").split("{")
        workflows[workflowLabel] = workflowRules.split(",")
    }

    val initialRanges = mapOf(
        'x' to Pair(1, 4000),
        'm' to Pair(1, 4000),
        'a' to Pair(1, 4000),
        's' to Pair(1, 4000),
    )

    result += count(initialRanges, "in", workflows)

    println("Result: $result")
}