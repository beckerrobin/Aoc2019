import java.io.File
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap

class D9 {
    companion object {
        fun run() {
            // Tests
            /*val test1 = listOf<Long>(109, 1, 204, -1, 1001, 100, 1, 100, 1008, 100, 16, 101, 1006, 101, 0, 99).mapIndexed({ k, v -> k to v }).toMap() as HashMap<Int, Long>
            val test2 = listOf<Long>(1102, 34915192, 34915192, 7, 4, 7, 99, 0).mapIndexed { k, v -> k to v }.toMap() as HashMap<Int, Long>
            val test3 = listOf(104, 1125899906842624, 99).mapIndexed { k, v -> k to v }.toMap() as HashMap<Int, Long>

            var relBase = 0
            while (true) {
                val result = opComputer(test1, startRelativeBase = relBase)
                print("${result.second}, ")
                relBase = result.first.second
                if (result.second == Long.MAX_VALUE || result.second == 99L) {
                    println()
                    break
                }
            }
            println(opComputer(test2).second)
            println(opComputer(test3).second)*/

            // Part 1
            val scanner = Scanner(File("src/d9.txt"))
            val dataList = scanner.nextLine().split(",")
            val inputMap: HashMap<Int, Long> = HashMap()
            for (i in dataList.indices) {
                inputMap.put(i, dataList[i].toLong())
            }

            val result = opComputer(inputMap, 1)
            println("Part 1: ${result.second}")
        }

        fun getByValue(memory: HashMap<Int, Long>, index: Int): Long {
            return memory[index]!!
        }

        fun getByReference(memory: HashMap<Int, Long>, index: Int): Long {
            return memory[memory[index]!!.toInt()]!!
        }

        fun getByRelativeReference(memory: HashMap<Int, Long>, index: Int, relativeBase: Int): Long {
            return memory[memory[index]!!.toInt() + relativeBase]!!
        }

        fun readValue(memory: HashMap<Int, Long>, index: Int, mode: Int, relativeBase: Int): Long {
            return when {
                mode == 1 -> getByValue(memory, index)
                mode == 2 -> getByRelativeReference(memory, index, relativeBase)
                else -> getByReference(memory, index)
            }
        }

        fun getWritePosition(memory: HashMap<Int, Long>, index: Int, mode: Int, relativeBase: Int): Int {
            return when {
                mode == 2 -> getByValue(memory, index).toInt() + relativeBase
                else -> getByValue(memory, index).toInt()
            }
        }

        fun opComputer(memory: HashMap<Int, Long>, vararg input: Long, startPos: Int = 0, startRelativeBase: Int = 0): Pair<Pair<Int, Int>, Long> {
            var stepSize: Int
            var inputPos = 0

            var relativeBase = startRelativeBase
            var pos = startPos
            while (pos < memory.size) {
                val data = memory[pos]!!
                var opcode = data.toInt()
                var modes = ArrayList<Int>(listOf(0, 0, 0))

                // Parameter mode
                if (data > 100) {
                    val s: String = data.toString()
                    opcode = Integer.parseInt(s.substring(s.length - 1))
                    modes = when {
                        data > 10000 ->
                            ArrayList(listOf(data.toInt() / 10000, (data.toInt() / 1000) % 10, (data.toInt() / 100) % 10))
                        data > 1000 -> ArrayList(listOf(0, data.toInt() / 1000, (data.toInt() / 100) % 10))
                        else -> ArrayList(listOf(0, 0, data.toInt() / 100))
                    }
                }

//                println("data: $data | opcode: $opcode")
                when (opcode) {
                    1 -> { // [z] = x + y
                        val val1 = readValue(memory, pos + 1, modes[2], relativeBase)
                        val val2 = readValue(memory, pos + 2, modes[1], relativeBase)
                        val writePos = getWritePosition(memory, pos + 3, modes[0], relativeBase)

                        memory[writePos] = val1 + val2
                        stepSize = 4
                    }
                    2 -> { // [z] = x * y
                        val val1 = readValue(memory, pos + 1, modes[2], relativeBase)
                        val val2 = readValue(memory, pos + 2, modes[1], relativeBase)
                        val writePos = getWritePosition(memory, pos + 3, modes[0], relativeBase)

                        memory[writePos] = val1 * val2
                        stepSize = 4
                    }
                    3 -> { // input x
                        val writePos = getWritePosition(memory, pos + 1, modes[2], relativeBase)
                        memory[writePos] = input[inputPos]
                        inputPos++

                        stepSize = 2
                    }
                    4 -> { // output x
                        val value = readValue(memory, pos + 1, modes[2], relativeBase)

                        return Pair(Pair(pos + 2, relativeBase), value)
                    }
                    5 -> { // if x != 0 then Position = y
                        val val1 = readValue(memory, pos + 1, modes[2], relativeBase)

                        if (val1 != 0L) {
                            pos = readValue(memory, pos + 2, modes[1], relativeBase).toInt()
                            stepSize = 0
                        } else {
                            stepSize = 3
                        }
                    }
                    6 -> { // if x == 0 then Position = y
                        val val1 = readValue(memory, pos + 1, modes[2], relativeBase)

                        if (val1 == 0L) {
                            pos = readValue(memory, pos + 2, modes[1], relativeBase).toInt()
                            stepSize = 0
                        } else {
                            stepSize = 3
                        }
                    }
                    7 -> { // [x] = if x < y then 1 else 0
                        val val1 = readValue(memory, pos + 1, modes[2], relativeBase)
                        val val2 = readValue(memory, pos + 2, modes[1], relativeBase)
                        val writePos = getWritePosition(memory, pos + 3, modes[0], relativeBase)

                        memory[writePos] = if (val1 < val2) 1L else 0L
                        stepSize = 4

                    }
                    8 -> { // [x] = if x == y then 1 else 0
                        val val1 = readValue(memory, pos + 1, modes[2], relativeBase)
                        val val2 = readValue(memory, pos + 2, modes[1], relativeBase)
                        val writePos = getWritePosition(memory, pos + 3, modes[0], relativeBase)

                        memory[writePos] = if (val1 == val2) 1L else 0L
                        stepSize = 4
                    }

                    9 -> { // change relativeBase
                        relativeBase += readValue(memory, pos + 1, modes[2], relativeBase).toInt()
                        stepSize = 2
                    }
                    99 -> // Exit
                        return Pair(Pair(pos + 1, relativeBase), Long.MAX_VALUE)
                    else -> {
                        stepSize = 1
                    }
                }
                pos += stepSize
            }
            return Pair(Pair(0, 0), -1)
        }
    }
}