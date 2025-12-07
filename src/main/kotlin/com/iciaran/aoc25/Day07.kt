package com.iciaran.aoc25

import java.io.File

data class Manifold(val splitters: List<List<Boolean>>, val start: Pair<Int, Int>)

class Day07 : Day<Manifold> {
    override fun getInput(): Manifold {
        val splitters = mutableListOf<List<Boolean>>()
        var start = Pair(0, 0)

        File("inputs/07.txt").readLines().forEachIndexed { index, line ->
            if (line.contains('S')) {
                start = Pair(line.indexOf('S'), index)
            }

            splitters.add(line.map { it == '^' })
        }

        return Manifold(splitters, start)
    }

    override fun part1(input: Manifold): String {
        val initial = Array(input.splitters[0].size) { false }
        initial[input.start.first] = true

        return input.splitters.drop(1)
            .fold(Pair(initial, 0)) { (current, count), splitters ->
                val next = Array(current.size) { false }

                val splitCount = current.indices.map { x ->
                    if (current[x] && splitters[x]) {
                        next[x - 1] = true
                        next[x + 1] = true
                        1
                    } else {
                        if (current[x]) {
                            next[x] = true
                        }
                        0
                    }
                }.sum()

                Pair(next, count + splitCount)
            }.second.toString()
    }

    override fun part2(input: Manifold): String {
        val initial = Array(input.splitters[0].size) { 0L }
        initial[input.start.first] = 1L

        return input.splitters.drop(0).fold(initial) { current, splitters ->
            val next = Array(current.size) { 0L }

            for (x in current.indices) {
                if (current[x] > 0 && splitters[x]) {
                    next[x - 1] += current[x]
                    next[x + 1] += current[x]
                } else if (current[x] > 0) {
                    next[x] += current[x]
                }
            }

            next
        }.sum().toString()
    }
}
