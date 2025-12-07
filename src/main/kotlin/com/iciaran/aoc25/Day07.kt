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
        var current = input.splitters[0].mapIndexed { x, _ -> x == input.start.first }
        var splitCount = 0

        for (y in (1..<input.splitters.size)) {
            val next = Array(current.size) { false }

            for (x in current.indices) {
                if (current[x] && input.splitters[y][x]) {
                    splitCount++
                    next[x - 1] = true
                    next[x + 1] = true
                } else if (current[x]) {
                    next[x] = true
                }
            }

            current = next.toList()
        }

        return splitCount.toString()
    }

    override fun part2(input: Manifold): String {
        var current = Array(input.splitters[0].size) { 0L }
        current[input.start.first] = 1L

        for (y in (1..<input.splitters.size)) {
            val next = Array(current.size) { 0L }
            
            for (x in current.indices) {
                if (current[x] > 0 && input.splitters[y][x]) {
                    next[x - 1] += current[x]
                    next[x + 1] += current[x]
                } else if (current[x] > 0) {
                    next[x] += current[x]
                }
            }

            current = next
        }

        return current.sum().toString()
    }
}
