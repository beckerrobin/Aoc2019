import java.io.File
import java.lang.Integer.max
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashSet

class D10 {
    companion object {
        fun run() {
            val stringsBoard = ArrayList<ArrayList<String>>()

            val scanner = Scanner(File("src/d10.txt"))
            while (scanner.hasNextLine()) {
                stringsBoard.add(ArrayList(scanner.nextLine().chunked(1)))
            }

            val stars = ArrayList<ArrayList<Int>>()
            // Iteration
            for (y in 0 until stringsBoard.size) {
                val line = stringsBoard[y]
                stars.add(ArrayList())
                for (x in 0 until line.size) {
                    val pos = line[x]
                    // Inner loop
                    if (pos.equals("#")) stars[y].add(x)
                }
            }

            var max = 0
            for (y in 0 until stars.size) {
                for (xPos in 0 until stars[y].size) {
                    val x = stars[y][xPos]
                    var counter = 0
                    // One neighbour-set for each quadrant
                    val neighbours: ArrayList<HashSet<Double>> = ArrayList(listOf(HashSet(), HashSet(), HashSet(), HashSet()))
                    var hasAbove = false
                    var hasBelow = false
                    // To the left
                    if (stars[y].filter { c -> c < x }.toIntArray().size >= 1) counter++
                    // To the right
                    if (stars[y].filter { c -> c > x }.toIntArray().size >= 1) counter++
                    for (subY in 0 until y) {
                        for (subXPos in 0 until stars[subY].size) {
                            val subX = stars[subY][subXPos]
                            if (subY != y && subX == x)
                                hasAbove = true
                            else {
                                val relativeX = x - subX
                                val relativeY = y - subY
                                val ratio = relativeX.toDouble() / relativeY
                                if (subX < x) {
                                    neighbours[0].add(ratio)
                                } else {
                                    neighbours[1].add(ratio)
                                }
                            }
                        }
                    }
                    for (subY in y + 1 until stars.size) {
                        for (subXPos in 0 until stars[subY].size) {
                            val subX = stars[subY][subXPos]
                            if (subY != y && subX == x)
                                hasBelow = true
                            else {
                                val relativeX = x - subX
                                val relativeY = y - subY
                                val ratio = relativeX.toDouble() / relativeY
                                if (subX < x) {
                                    neighbours[2].add(ratio)
                                } else {
                                    neighbours[3].add(ratio)
                                }
                            }
                        }
                    }
                    if (hasAbove) counter++
                    if (hasBelow) counter++

                    for (neighbour in neighbours) {
                        counter += neighbour.size
                    }
                    max = max(max, counter)
                    if (counter == max)
                        println("New max: $counter: $x,$y")
                }
            }
            println("Part 1: $max")
        }
    }
}