import java.io.File
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap

class D11 {
    companion object {
        fun run() {
            val scanner = Scanner(File("src/d11.txt"))
            val dataList = scanner.nextLine().split(",")
            val inputMap: HashMap<Int, Long> = HashMap()
            for (i in dataList.indices) {
                inputMap[i] = dataList[i].toLong()
            }

            var input = 1L // Part 1: 0L, Part 2: 1L
            val visitedCoordinates = HashMap<Pair<Int, Int>, Int>() // <Coords , Color (= 0 Black, 1 White)>
            var currentCoordinate = Pair(0, 0) // x,y
            var opCompPos = 0
            var opCompBase = 0
            var direction = 0 // 0: up, 1: right...
            var iterations = 0
            try {
                while (true) {
                    // Color
                    var result = D9.opComputer(inputMap, input, startRelativeBase = opCompBase, startPos = opCompPos)
                    opCompPos = result.first.first
                    opCompBase = result.first.second
                    visitedCoordinates[currentCoordinate] = result.second.toInt() // color

                    // Movement
                    result = D9.opComputer(inputMap, startRelativeBase = opCompBase, startPos = opCompPos)
                    opCompPos = result.first.first
                    opCompBase = result.first.second
                    when (result.second.toInt()) {
                        0 -> direction--
                        1 -> direction++
                    }
                    if (direction < 0)
                        direction = 3
                    else
                        direction %= 4
                    when (direction) {
                        0 -> currentCoordinate = currentCoordinate.copy(second = currentCoordinate.second - 1)
                        1 -> currentCoordinate = currentCoordinate.copy(first = currentCoordinate.first + 1)
                        2 -> currentCoordinate = currentCoordinate.copy(second = currentCoordinate.second + 1)
                        3 -> currentCoordinate = currentCoordinate.copy(first = currentCoordinate.first - 1)
                    }
                    println("At $currentCoordinate | visited: ${visitedCoordinates.size}")

                    // Read new color
                    input = visitedCoordinates.getOrDefault(currentCoordinate, 0).toLong()

                    if (currentCoordinate == Pair(0, 0)) {
                        println("${iterations++} iteration: ${visitedCoordinates.keys.size} visited")
                        if (iterations > 2)
                            break
                    }
                }
            } catch (e: Exception) {
            }
            // Part 2: Show paint
            val maxX = visitedCoordinates.keys.maxBy { pair -> pair.first }!!.first
            val maxY = visitedCoordinates.keys.maxBy { pair -> pair.second }!!.second
            val painting: ArrayList<ArrayList<String>> = ArrayList()
            val emptyRow = " ".repeat(maxX + 1).chunked(1)
            for (y in 0 until maxY + 1) {
                painting.add(ArrayList(emptyRow))
            }

            for (visitedCoordinate in visitedCoordinates) {
                if (visitedCoordinate.value == 1) {
                    val y = visitedCoordinate.key.second
                    val x = visitedCoordinate.key.first
                    painting[y][x] = "#"
                }
            }
            for (row in painting) {
                println(row.joinToString(" "))
            }
        }
    }
}