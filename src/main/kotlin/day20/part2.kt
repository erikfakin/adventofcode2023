package day20_2

import java.io.File


class Component(val id: String, val type: String, val destinationIds: List<String>) {
    var state = ""
    var signalFrom = mutableMapOf<String, String>()

    init {
        state = if (type == "%") "off" else "low"
    }

    override fun toString(): String {
        return "Id: $id, Type: $type, Destinations: $destinationIds, State: $state, Signal from : $signalFrom"
    }

}


fun main() {
    var result = 1L
    val components = mutableMapOf<String, Component>()
    File("src/main/kotlin/day20/input.txt").forEachLine {
        val (componentInfo, destinationInfo) = it.split(" -> ")
        val componentId = if (componentInfo == "broadcaster") "broadcaster" else componentInfo.substring(1)
        val componentType = if (componentInfo == "broadcaster") "broadcaster" else componentInfo.first().toString()
        val destinationIds = destinationInfo.split(", ")
        components[componentId] = Component(componentId, componentType, destinationIds)
    }


    components.forEach { component ->
        component.value.destinationIds.forEach {
            if (!components.containsKey(it)) return@forEach
            components[it]!!.signalFrom[component.value.id] = "low"
        }
    }


    /**
     * We have only one component going to rx of type &.
     * If we record the number cycles for each input to send a high signal to this component, we can assume that all
     * the inputs will give a high signal to it every c1 * c2 * ... *cn cycles, where n is the total number of components
     * connected to our component of interest (of type &)
     */

    val numberOfImpulsesToHigh = mutableMapOf<String, Int>()

    val componentToRx = components.filter { component ->
        component.value.destinationIds.contains("rx")
    }.toList().first().second

    componentToRx.signalFrom.forEach {
        numberOfImpulsesToHigh[it.key] = 0
    }

    var startedEngine = false

 var counter = 0

    while (!startedEngine) {
        // Signal [type, from, to]
        var signalQueue = mutableListOf<MutableList<String>>()
        components["broadcaster"]!!.destinationIds.forEach {
            signalQueue.add(mutableListOf("low", "broadcaster", it))
        }
        counter++
        while (signalQueue.isNotEmpty()) {

            val currentSignal = signalQueue.removeAt(0)
            val signalType = currentSignal[0]
            val signalFrom = currentSignal[1]
            val currentComponent = components[currentSignal[2]]

            if (currentComponent == componentToRx) {
                if (signalType == "high") {
                    if (numberOfImpulsesToHigh[signalFrom] == 0) {
                        numberOfImpulsesToHigh[signalFrom] = counter
                    }
                    if (numberOfImpulsesToHigh.all { it.value != 0 }) {
                        startedEngine = true
                        break
                    }
                }

            }


            if (currentComponent == null) {
                continue
            }

            if (currentComponent!!.type == "%") {
                if (currentSignal.first() == "high") continue
                if (currentComponent.state == "off") {
                    currentComponent.state = "on"
                    currentComponent.destinationIds.forEach {
                        signalQueue.add(mutableListOf("high", currentComponent.id ,it))
                    }
                } else {
                    currentComponent.state = "off"
                    currentComponent.destinationIds.forEach {
                        signalQueue.add(mutableListOf("low", currentComponent.id, it))
                    }
                }
            }

            if (currentComponent!!.type == "&") {
                currentComponent.signalFrom[signalFrom] = signalType
                val allHigh = currentComponent.signalFrom.all {it.value == "high"}
                if (allHigh) {
                    currentComponent.destinationIds.forEach {
                        signalQueue.add(mutableListOf("low", currentComponent.id, it))
                    }
                } else {
                    currentComponent.destinationIds.forEach {
                        signalQueue.add(mutableListOf("high", currentComponent.id, it))
                    }
                }
            }
        }
    }

    numberOfImpulsesToHigh.forEach { result*=it.value  }


    println("Result: $result")
}