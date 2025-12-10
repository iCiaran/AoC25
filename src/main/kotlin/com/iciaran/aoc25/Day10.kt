package com.iciaran.aoc25

import java.io.File

data class Machine(
    val lightTarget: List<Boolean>,
    val buttons: List<List<Int>>,
    val joltages: List<Int>
)

class Day10 : Day<List<Machine>> {
    override fun getInput(): List<Machine> {
        val lines = File("inputs/10.txt").readLines().map { it.split(" ") }
        return lines.map {
            val lightTarget = it.first()
                .substringAfter("[")
                .substringBefore("]")
                .map { light -> light == '#' }

            val buttons = it.drop(1).dropLast(1).map { buttonList ->
                buttonList
                    .substringAfter("(")
                    .substringBefore(")")
                    .split(",")
                    .map(String::toInt)
            }


            val joltages = it.last()
                .substringAfter("{")
                .substringBefore("}")
                .split(",")
                .map(String::toInt)

            Machine(lightTarget, buttons, joltages)
        }
    }

    fun flipLights(lights: List<Boolean>, buttons: List<Int>): List<Boolean> {
        return buttons.fold(lights.toMutableList()) { acc, button ->
            acc[button] = !acc[button]
            acc
        }
    }

    fun List<Boolean>.toBitmask(): Int =
        fold(0) { acc, b -> acc.shl(1).or(if (b) 1 else 0) }

    fun countFlipsToTargetLights(target: List<Boolean>, buttons: List<List<Int>>): Int {
        val initial = List(target.size) { false }
        val visited = mutableSetOf<Int>()

        val queue = ArrayDeque(listOf(Pair(initial, 0)))

        while (queue.isNotEmpty()) {
            val current = queue.removeFirst()
            visited.add(current.first.toBitmask())

            buttons.forEach {
                val flipped = flipLights(current.first, it)

                if (flipped == target) {
                    return current.second + 1
                }

                if (!visited.contains(flipped.toBitmask())) {
                    queue.add(Pair(flipped, current.second + 1))
                }
            }
        }

        return -1
    }

    override fun part1(input: List<Machine>): String {
        return input.sumOf { countFlipsToTargetLights(it.lightTarget, it.buttons) }.toString()
    }

    override fun part2(input: List<Machine>): String {
        return "-1"
    }

}
