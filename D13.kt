import java.io.File
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap

class D13 {
    companion object {
        fun run() {
            val scanner = Scanner(File("src/d13.txt"))
            val dataList = scanner.nextLine().split(",")
            val part1Game: HashMap<Int, Long> = HashMap()
            val part2Game: HashMap<Int, Long> = HashMap()
            for (i in dataList.indices) {
                part1Game[i] = dataList[i].toLong()
                part2Game[i] = dataList[i].toLong()
            }

            // Part 1
            val tileMap = HashMap<Pair<Long, Long>, Int>()
            var compParams = Pair(0, 0) // <Pos, RelBase>
            while (true) {
                var compResult = D9.opComputer(part1Game, startPos = compParams.first, startRelativeBase = compParams.second)
                if (compResult.second == Long.MAX_VALUE)
                    break
                compParams = compResult.first
                val x = compResult.second

                compResult = D9.opComputer(part1Game, startPos = compParams.first, startRelativeBase = compParams.second)
                if (compResult.second == Long.MAX_VALUE)
                    break
                compParams = compResult.first
                val y = compResult.second

                compResult = D9.opComputer(part1Game, startPos = compParams.first, startRelativeBase = compParams.second)
                if (compResult.second == Long.MAX_VALUE)
                    break
                compParams = compResult.first
                val tileType = compResult.second

                tileMap[Pair(x, y)] = tileType.toInt()
            }
            println("Part 1: ${tileMap.values.filter { tile -> tile == 2 }.size}")

            // Part 2
            compParams = Pair(0, 0)
            println("Part 2")
            part2Game[0] = 2L // Simulate quarters

            // Cheat: The paddle now fills the entire width
            for (i in 1630 until 1673)
                part2Game[i] = 3L

            var score = 0L
            var input = 0L

            val gameMatrix = ArrayList<ArrayList<Int>>()
            outer@ while (true) {
                while (true) {
                    var compResult = D9.opComputer(part2Game, input, startPos = compParams.first, startRelativeBase = compParams.second)
                    if (compResult.second == Long.MAX_VALUE) {
                        println("Game Over!")
                        drawGame(gameMatrix, score)
                        break@outer
                    }
                    compParams = compResult.first
                    val x = compResult.second.toInt()

                    compResult = D9.opComputer(part2Game, startPos = compParams.first, startRelativeBase = compParams.second)
                    if (compResult.second == Long.MAX_VALUE) {
                        println("Game Over!")
                        drawGame(gameMatrix, score)
                        break@outer
                    }
                    compParams = compResult.first
                    val y = compResult.second.toInt()

                    compResult = D9.opComputer(part2Game, startPos = compParams.first, startRelativeBase = compParams.second)
                    if (compResult.second == Long.MAX_VALUE) {
                        println("Game Over!")
                        drawGame(gameMatrix, score)
                        break@outer
                    }
                    compParams = compResult.first
                    val tileType = compResult.second

                    if (x == -1 && y == 0) {
                        score = tileType
                        break
                    } else {
                        if (y >= gameMatrix.size)
                            for (i in gameMatrix.size until y + 1)
                                gameMatrix.add(ArrayList())
                        if (x >= gameMatrix[y].size)
                            for (i in gameMatrix[y].size until x + 1)
                                gameMatrix[y].add(0)
                        gameMatrix[y][x] = tileType.toInt()
                        if (tileType.toInt() == 4)
                            break
                    }
                }
            }
            drawGame(gameMatrix, score)
            println("Part 2: $score")
        }

        private fun drawGame(gameMatrix: ArrayList<ArrayList<Int>>, score: Long) {
            println()
            for (row in gameMatrix) {
                val rowString = StringBuilder()
                for (pixel in row) {
                    when (pixel) {
                        0 -> rowString.append(" ")
                        1 -> rowString.append("#")
                        2 -> rowString.append("+")
                        3 ->
                            rowString.append("-")
                        4 -> rowString.append("o")
                    }
                }
                println(rowString.toString())
            }
            println("--- Score: $score ---")
        }
    }
}