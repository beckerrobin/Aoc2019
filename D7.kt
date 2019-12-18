import java.io.File
import java.util.*
import kotlin.collections.ArrayList

// Kotlin

class D7 {
    companion object {
        fun run() {
            val scanner = Scanner(File("src/d7.txt"))
            val array: ArrayList<Int> = scanner.nextLine().split(",").map { t -> t.toInt() } as ArrayList<Int>
            val testArray = ArrayList<Int>(listOf(3, 31, 3, 32, 1002, 32, 10, 32, 1001, 31, -2, 31, 1007, 31, 0, 33,
                    1002, 33, 7, 33, 1, 33, 31, 31, 1, 32, 31, 31, 4, 31, 99, 0, 0, 0))
            val phaseSeq = listOf(0, 1, 2, 3, 4)

            var max: Int
            var maxPerm: List<Int>? = null

            /*// Part 1
            for (permutation in permute(phaseSeq)) {
                var input = 0 // First input
                for (phase in permutation) {
                    input = opComputer(array, phase, input)
                }

                max = if (max > input) max else input
                if (max == input)
                    maxPerm = permutation
            }
            println("Part 1 max: " + max)
            println("with permutation: " + maxPerm)*/

            // Part 2
            val phaseSeq2 = listOf(5, 6, 7, 8, 9)
            val testList2 = listOf(3, 26, 1001, 26, -4, 26, 3, 27, 1002, 27, 2, 27, 1, 27, 26,
                    27, 4, 27, 1001, 28, -1, 28, 1005, 28, 6, 99, 0, 0, 5)
            val ampList = "ABCDE".split("").subList(1, "ABCDE".length + 1)

            val computers: ArrayList<Pair<Int, ArrayList<Int>>> = ArrayList()

            max = 0

            for (permutation in permute(phaseSeq2)) {
                var ampListIterator = ampList.listIterator()
                for (i in phaseSeq2) {
                    computers.add(Pair(0, ArrayList<Int>(array)))
                }
                var input = 0 // First input

                for (i in permutation.indices) {
                    val answerPair = opComputer(computers[i].second, permutation[i], input, startPos = 0)
                    computers[i] = computers[i].copy(first = answerPair.first)
                    input = answerPair.second
                    if (!ampListIterator.hasNext())
                        ampListIterator = ampList.listIterator()
                }
                var currentAmp = 0
                while (!computers.isEmpty()) {
                    val answerPair = opComputer(computers[currentAmp].second, input, startPos = computers[currentAmp].first)
                    computers[currentAmp] = computers[currentAmp].copy(first = answerPair.first)
                    input = answerPair.second

                    max = if (max > input) max else input
                    if (max == input)
                        maxPerm = permutation as ArrayList<Int>

                    if (input == -1) {
                        computers.removeAt(currentAmp)
                        currentAmp--
                    }
                    currentAmp++
                    if (currentAmp == computers.size)
                        currentAmp = 0
                    if (!ampListIterator.hasNext())
                        ampListIterator = ampList.listIterator()
                }


            }
            println("Part 2 max: " + max)
            println("with permutation: " + maxPerm)
        }

        fun opComputer(array: ArrayList<Int>, vararg input: Int, startPos: Int): Pair<Int, Int> {
            var stepSize: Int
            var inputPos = 0

            var pos = startPos
            while (pos < array.size) {
                val data = array[pos]
                var opcode = data
                var modes = ArrayList<String>(listOf("0", "0"))

                if (data > 100) {
                    val s: String = data.toString()
                    opcode = Integer.parseInt(s.substring(s.length - 1))
                    modes = if (data > 1000) {
                        ArrayList(s.substring(0, 2).split("").subList(1, 3))
                    } else
                        ArrayList(listOf("0", s.substring(0, 1)))
                }

                when (opcode) {
                    1 -> {
                        val val1 = if (modes[1] == "1") array[pos + 1] else array[array[pos + 1]]
                        val val2 = if (modes[0] == "1") array[pos + 2] else array[array[pos + 2]]
                        array[array[pos + 3]] = val1 + val2
                        stepSize = 4
                    }
                    2 -> {
                        val val1 = if (modes[1] == "1") array[pos + 1] else array[array[pos + 1]]
                        val val2 = if (modes[0] == "1") array[pos + 2] else array[array[pos + 2]]
                        array[array[pos + 3]] = val1 * val2
                        stepSize = 4
                    }
                    3 -> {
                        array[array[pos + 1]] = input[inputPos]
                        inputPos++

                        stepSize = 2
                    }
                    4 -> {
                        val value = (if (modes[1] == "0") array[array[pos + 1]] else array[pos + 1])
                        return Pair(pos + 2, value)
                    }
                    5 -> {
                        val val1 = if (modes[1] == "1") array[pos + 1] else array[array[pos + 1]]
                        if (val1 != 0) {
                            pos = (if (modes[0] == "1") array[pos + 2] else array[array[pos + 2]])
                            stepSize = 0
                        } else {
                            stepSize = 3
                        }
                    }
                    6 -> {
                        val val1 = if (modes[1] == "1") array[pos + 1] else array[array[pos + 1]]
                        if (val1 == 0) {
                            pos = (if (modes[0] == "1") array[pos + 2] else array[array[pos + 2]])
                            stepSize = 0
                        } else {
                            stepSize = 3
                        }
                    }
                    7 -> {
                        val val1 = if (modes[1] == "1") array[pos + 1] else array[array[pos + 1]]
                        val val2 = if (modes[0] == "1") array[pos + 2] else array[array[pos + 2]]

                        array[array[pos + 3]] = if (val1 < val2) 1 else 0
                        stepSize = 4

                    }
                    8 -> {
                        val val1 = if (modes[1] == "1") array[pos + 1] else array[array[pos + 1]]
                        val val2 = if (modes[0] == "1") array[pos + 2] else array[array[pos + 2]]

                        array[array[pos + 3]] = if (val1 == val2) 1 else 0
                        stepSize = 4
                    }
                    99 ->
                        return Pair(pos + 1, -1)
                    else -> {
                        stepSize = 1
                    }
                }
                pos += stepSize
            }
            return Pair(0, 0)
        }

        fun <T> permute(input: List<T>): List<List<T>> {
            if (input.size == 1) return listOf(input)
            val perms = mutableListOf<List<T>>()
            val toInsert = input[0]
            for (perm in permute(input.drop(1))) {
                for (i in 0..perm.size) {
                    val newPerm = perm.toMutableList()
                    newPerm.add(i, toInsert)
                    perms.add(newPerm)
                }
            }
            return perms
        }
    }
}