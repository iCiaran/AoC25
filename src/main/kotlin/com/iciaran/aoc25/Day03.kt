package com.iciaran.aoc25

import java.io.File

class Day03 : Day<List<List<Int>>> {
    override fun getInput(): List<List<Int>> {
        return File("inputs/03.txt")
            .readLines()
            .map { it.map(Char::digitToInt) }
    }

    fun findLargestJoltageGeneral(bank: List<Int>, digits: Int): Long {
        val indices = IntArray(digits)
        for (i in (1..<digits)) {
            indices[digits - i] = bank.size - i
        }

        // Forward pass for 1st
        for (battery in (1..bank.size - digits)) {
            if (bank[battery] > bank[indices[0]]) {
                indices[0] = battery
            }
        }

        // Backward passes for rest
        for (i in (1..<indices.size)) {
            for (battery in indices[i] downTo indices[i - 1] + 1) {
                if (bank[battery] >= bank[indices[i]]) {
                    indices[i] = battery
                }
            }
        }

        var joltage = 0L
        indices.forEach {
            joltage *= 10
            joltage += bank[it]
        }

        return joltage
    }

    override fun part1(input: List<List<Int>>): String {
        return input.sumOf { findLargestJoltageGeneral(it, 2) }.toString()
    }

    override fun part2(input: List<List<Int>>): String {
        return input.sumOf { findLargestJoltageGeneral(it, 12) }.toString()
    }


}
