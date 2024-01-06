package day20

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
    var result = 0
    val buttonPushes = 1000
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
    var lowSignals = 0
    var highSignals = 0


    for (i in 0..<buttonPushes) {
        // Signal [type, from, to]
        var signalQueue = mutableListOf<MutableList<String>>()
        components["broadcaster"]!!.destinationIds.forEach {
            signalQueue.add(mutableListOf("low", "broadcaster", it))
        }
        lowSignals++
        while (signalQueue.isNotEmpty()) {
            val currentSignal = signalQueue.removeAt(0)
            val signalFrom = currentSignal[1]
            val currentComponent = components[currentSignal[2]]
            val signalType = currentSignal[0]
            if (signalType == "low") {
                lowSignals++
            } else {
                highSignals++
            }

            println("Current component $currentComponent")

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




    components.forEach {
        println(it)
    }

    println("Low: $lowSignals")
    println("High: $highSignals")

    result = lowSignals * highSignals




    println("Result: $result")
}