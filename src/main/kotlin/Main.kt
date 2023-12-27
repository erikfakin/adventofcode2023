import java.io.File
import kotlin.math.sign

class Part(val number: Int, val y: Int, val startX: Int, val endX: Int){

    override fun toString(): String {
        return "Part number: $number, position Y: $y, Position X: start $startX end $endX"
    }
}

class Signal(val type: String,val x: Int, val y: Int) {

    val neighbours : MutableSet<Part> = mutableSetOf()
    override fun toString(): String {
        return "$type signal at $x $y"
    }

    fun addNeighbour(part: Part) {
        neighbours.add(part)
    }

    fun getParts(): MutableSet<Part> {
        return neighbours
    }
}
fun readFile(filename: String): Int {
    var sum = 0
    var lineNumber = 0

    var parts : MutableList<Part> = mutableListOf()
    var adjParts : MutableSet<Part> = mutableSetOf()
    var signals : MutableList<Signal> = mutableListOf()


    File(filename).forEachLine {
        val partMatches = Regex("(\\d+)").findAll(it).forEach { m ->
            val part = Part(m.groups.last()!!.value.toInt(), lineNumber, m.groups.last()?.range!!.first, m.groups.last()?.range!!.last)
            parts.add(part)

        }

        val signalMatches = Regex("[^0-9.a-zA-Z]").findAll(it).forEach { m ->
            println("GR ${m.groups}")
            val signal = Signal(m.groups.last()!!.value, m.groups.last()?.range!!.first, lineNumber)
            signals.add(signal)


        }

        lineNumber++





    }


    signals.forEach{signal ->
        if(signal.type != "*") return@forEach

        parts.forEach { part ->
            if (
            (part.y >= signal.y - 1 && part.y <= signal.y + 1) &&
                ((part.startX >= signal.x - 1 && part.startX <= signal.x + 1)
                        || (part.endX >= signal.x - 1 && part.endX <= signal.x + 1)
                        )  )  {
                signal.addNeighbour(part)

            }
        }


            //((part.startX >= signal.x - 1 && part.startX <= signal.x + 1) ||(part.endX >= signal.x - 1 && part.endX <= signal.x + 1))  }

    }


    signals.forEach {
        if (it.getParts().size == 2) {
            sum += it.getParts().first().number *   it.getParts().last().number
        }
    }


    return sum


}


fun main(args: Array<String>) {

    println("RESULT: ${readFile("src/day11.day11.main/kotlin/text.txt")}")


}