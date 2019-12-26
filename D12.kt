import java.io.File
import java.util.*
import kotlin.collections.ArrayList
import kotlin.math.abs

class D12 {
    companion object {
        fun run() {
            // Init
            val turns = 1000

            val moonList: ArrayList<Moon> = ArrayList()
            val scanner = Scanner(File("src/d12.txt"))
            while (scanner.hasNextLine()) {
                val pattern = Regex("(?=)-?\\d{1,2}")
                val matchList = pattern.findAll(scanner.nextLine()).toList()
                moonList.add(Moon(matchList))
            }

            // Loop
            for (x in 0 until turns) {
                // Calc velocities
                for (i in 0 until moonList.size) {
                    for (j in 0 until moonList.size) {
                        if (i == j)
                            continue
                        // x
                        if (moonList[i].x < moonList[j].x)
                            moonList[i].vX++
                        else if (moonList[i].x > moonList[j].x)
                            moonList[i].vX--
                        // y
                        if (moonList[i].y < moonList[j].y)
                            moonList[i].vY++
                        else if (moonList[i].y > moonList[j].y)
                            moonList[i].vY--
                        // z
                        if (moonList[i].z < moonList[j].z)
                            moonList[i].vZ++
                        else if (moonList[i].z > moonList[j].z)
                            moonList[i].vZ--
                    }
                }

                // Movement
                for (moon in moonList) {
                    moon.x += moon.vX
                    moon.z += moon.vZ
                    moon.y += moon.vY
                }
            }
            // Energy
            var energy = 0
            for (moon in moonList) {
                val pot = abs(moon.x) + abs(moon.y) + abs(moon.z)
                val kin = abs(moon.vX) + abs(moon.vY) + abs(moon.vZ)

                energy += pot * kin
            }
            println("Part 1: $energy")

            for (moon in moonList) {
                moon.moonReset()
            }

            // Part 2
            for (dim in 0 until 3) {
                println("Dim: $dim = ${simulateOneDimensionalUniverse(moonList, dim)}")
            }
        }

        /**
         * moons: list of Moons<pos, velo>
         * returns n of steps when all are back to origin
         */
        fun simulateOneDimensionalUniverse(moons: ArrayList<Moon>, dimension: Int): Int {
            var steps = 0

            loop@ while (true) {// calc velo
                for (moon in moons) {
                    for (otherMoon in moons.filter { otherMoon -> otherMoon != moon }) {
                        when (dimension) {
                            0 -> {
                                // x
                                if (moon.x < otherMoon.x)
                                    moon.vX++
                                else if (moon.x > otherMoon.x)
                                    moon.vX--
                            }
                            1 -> {
                                // y
                                if (moon.y < otherMoon.y)
                                    moon.vY++
                                else if (moon.y > otherMoon.y)
                                    moon.vY--
                            }
                            2 -> {
                                // z
                                if (moon.z < otherMoon.z)
                                    moon.vZ++
                                else if (moon.z > otherMoon.z)
                                    moon.vZ--
                            }
                        }
                    }
                }

                var test = true
                // move
                for (moon in moons) {
                    when (dimension) {
                        0 -> {
                            moon.x += moon.vX
                            if (test && moon.x != moon.origX || moon.vX != 0)
                                test = false
                        }
                        1 -> {
                            moon.y += moon.vY
                            if (test && moon.y != moon.origY || moon.vY != 0)
                                test = false
                        }
                        2 -> {
                            moon.z += moon.vZ
                            if (test && moon.z != moon.origZ || moon.vZ != 0)
                                test = false
                        }
                    }
                }

                steps++
                // test
                if (test)
                    break
            }
            return steps
        }
    }

    class Moon(list: List<MatchResult>) {
        var x: Int = list[0].value.toInt()
        var y: Int = list[1].value.toInt()
        var z: Int = list[2].value.toInt()
        var vX = 0
        var vY = 0
        var vZ = 0
        val origX = list[0].value.toInt()
        val origY = list[1].value.toInt()
        val origZ = list[2].value.toInt()

        override fun toString(): String {
            return "Moon(x=$x, y=$y, z=$z, vX=$vX, vY=$vY, vZ=$vZ)"
        }

        fun moonReset() {
            x = origX
            y = origY
            z = origZ
            vX = 0
            vY = 0
            vZ = 0
        }

        override fun equals(other: Any?): Boolean {
            if (this === other) return true
            if (javaClass != other?.javaClass) return false

            other as Moon

            if (origX != other.origX) return false
            if (origY != other.origY) return false
            if (origZ != other.origZ) return false

            return true
        }

        override fun hashCode(): Int {
            var result = origX
            result = 31 * result + origY
            result = 31 * result + origZ
            return result
        }

    }
}