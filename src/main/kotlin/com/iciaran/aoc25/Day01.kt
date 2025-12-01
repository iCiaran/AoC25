package com.iciaran.aoc25

import java.io.File

class Day01 : Day<List<Pair<Char, Int>>> {
    override fun getInput(): List<Pair<Char, Int>> {
        return File("inputs/01.txt")
            .bufferedReader()
            .readLines()
            .map { rotation -> Pair(rotation[0], rotation.drop(1).toInt()) }
    }

    override fun part1(input: List<Pair<Char, Int>>): String {
        var current = 50
        var zeroCount = 0

        for ((direction, distance) in input) {
            if (direction == 'L') {
                current -= distance
            } else {
                current += distance
            }

            current = current.mod(100)

            if (current == 0) {
                zeroCount++
            }
        }

        return zeroCount.toString()
    }

    override fun part2(input: List<Pair<Char, Int>>): String {
        var current = 50
        var zeroCount = 0

        for ((direction, distance) in input) {
            val start = current

            if (direction == 'L') {
                current -= distance % 100
            } else {
                current += distance % 100
            }

            // Handle first pass
            // Will we wrap around or end on 0?
            // Don't increment if we were already at 0
            if (current !in 1..<100 && start != 0) {
                zeroCount++
            }

            // Handle additional passes
            zeroCount += distance / 100

            current = current.mod(100)
        }

        return zeroCount.toString()
    }
}
