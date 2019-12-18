import java.io.File
import java.util.*
import kotlin.collections.ArrayList
import kotlin.math.min

class D8 {
    companion object {
        fun run() {
            val WIDTH = 25
            val HEIGHT = 6
            val scanner = Scanner(File("src/d8.txt"))

            val imageData: ArrayList<ArrayList<Int>> = ArrayList()
            imageData.add(ArrayList())
            val input = scanner.nextLine().chunked(1).map { c -> c.toInt() }

            // Part 1
            var layer = 0
            var minLayer = 0
            var fewestZeroes = Int.MAX_VALUE
            for (i in input.indices) {
                imageData[layer].add(input[i])
                if (i != 0 && (i + 1) % (WIDTH * HEIGHT) == 0 && i < input.size - 1) {
                    // End of layer
                    val amountOfZeroes = imageData[layer].filter { x -> x == 0 }.size
                    fewestZeroes = min(fewestZeroes, amountOfZeroes)
                    if (fewestZeroes == amountOfZeroes)
                        minLayer = layer

                    layer++
                    imageData.add(ArrayList())
                }
            }
            val amountOfOnes = imageData[minLayer].filter { i -> i == 1 }.size
            val amountOfTwos = imageData[minLayer].filter { i -> i == 2 }.size
            println("Part 1: " + amountOfOnes * amountOfTwos)
            println("on layer $minLayer")

            // Part 2
            val imageRepresentation: ArrayList<ArrayList<String>> = ArrayList()
            for (i in 0 until HEIGHT) {
                imageRepresentation.add(ArrayList())
                for (j in 0 until WIDTH)
                    imageRepresentation.last().add(" ")
            }
            for (currentLayer in imageData) {
                var row = 0
                for (i in currentLayer.indices) {
                    val col = i % WIDTH
                    val pixel = currentLayer[i]
                    if (imageRepresentation[row][col] == " ")
                        imageRepresentation[row][col] = valueToPixel(pixel)
                    if (col + 1 == WIDTH) {
                        row++
                    }
                }
            }

            // Print image
            println("Part 2")
            for (row in imageRepresentation) {
                println(row.map { t -> t.replace("_", " ") }.joinToString(" "))
            }

        }

        fun valueToPixel(value: Int): String {
            return when (value) {
                0 -> "_"
                1 -> "#"
                else -> " "
            }
        }
    }
}