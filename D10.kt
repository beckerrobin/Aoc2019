import java.io.File
import java.lang.Integer.max
import java.util.*
import kotlin.collections.ArrayList

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
            var maxPoint = Pair(0, 0)
            for (y in 0 until stars.size) {
                for (xPos in 0 until stars[y].size) {
                    val x = stars[y][xPos]
                    var counter = 0

                    val neighbours = detect(stars, x, y)
                    for (neighbour in neighbours) {
                        counter += neighbour.size
                    }
                    max = max(max, counter)
                    if (counter == max)
                        maxPoint = Pair(x, y)
                }
            }
            println("Part 1: $max at $maxPoint")

            val x = maxPoint.first
            val y = maxPoint.second
            var vaporCounter = 0
            outer@ while (vaporCounter < 200) {
                val neighbors = detect(stars, x = maxPoint.first, y = maxPoint.second)
                for (neighbor in neighbors) {
                    for (entry in neighbor.entries.sortedWith(kotlin.Comparator { ent1, ent2 ->
                        ent2.key.compareTo(ent1.key)
                    })) {
                        stars[entry.value.second].remove(entry.value.first)
                        vaporCounter++
                        if (vaporCounter == 200) {
                            println("Part 2: ${entry.value} = ${entry.value.first * 100 + entry.value.second}")
                            break@outer
                        }
                    }
                }
            }
        }

        fun detect(stars: ArrayList<ArrayList<Int>>, x: Int, y: Int): ArrayList<HashMap<Double, Pair<Int, Int>>> {
            // One neighbour-set for each quadrant
            val neighbours: ArrayList<HashMap<Double, Pair<Int, Int>>> = ArrayList(listOf(HashMap(), HashMap(), HashMap(), HashMap()))
            for (subY in y - 1 downTo 0) {
                for (subXPos in 0 until stars[subY].size) {
                    val subX = stars[subY][subXPos]
                    val coordinates = Pair(subX, subY)
                    val relativeX = subX - x
                    val relativeY = subY - y
                    val ratio = relativeX.toDouble() / relativeY

                    if (relativeX == 0) {
                        neighbours[0].putIfAbsent(ratio, coordinates)
                        continue
                    } else {
                        if (subX < x) {
                            neighbours[3].putIfAbsent(ratio, coordinates)
                        } else {
                            neighbours[0].putIfAbsent(ratio, coordinates)
                        }
                    }
                }
            }
            val rightSide = stars[y].filter { c -> c > x }
            if (!rightSide.isEmpty())
                neighbours[1].put(Double.MAX_VALUE, Pair(rightSide.first(), y))

            for (subY in y + 1 until stars.size) {
                for (subXPos in 0 until stars[subY].size) {
                    val subX = stars[subY][subXPos]
                    val coordinates = Pair(subX, subY)
                    val relativeX = subX - x
                    val relativeY = subY - y
                    val ratio = relativeX.toDouble() / relativeY

                    if (relativeX == 0) {
                        neighbours[2].putIfAbsent(ratio, coordinates)
                        continue
                    } else {
                        if (subX < x) {
                            neighbours[2].putIfAbsent(ratio, coordinates)
                        } else {
                            neighbours[1].putIfAbsent(ratio, coordinates)
                        }
                    }
                }
            }
            val leftSide = stars[y].filter { c -> c < x }
            if (!leftSide.isEmpty())
                neighbours[3].put(Double.MAX_VALUE, Pair(leftSide.last(), y))
            return neighbours
        }
    }
}