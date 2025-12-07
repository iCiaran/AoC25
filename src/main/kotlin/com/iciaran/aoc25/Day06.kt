package com.iciaran.aoc25

import java.io.File

data class Problem(val numbers: List<String>, val operation: Char)

class Day06 : Day<List<Problem>> {
    fun splitAtIndices(s: String, indices: List<Int>): List<String> {
        val result = mutableListOf<String>()

        var start = 0
        for (i in indices) {
            if (i in start..<s.length) {
                result += s.substring(start, i)
                start = i + 1
            }
        }

        if (start < s.length) {
            result += s.substring(start)
        }

        return result
    }

    override fun getInput(): List<Problem> {
        val lines = File("inputs/06.txt").readLines()
        val maxLength = lines.maxOfOrNull { it.length } ?: 0
        val splitIndices =
            lines[0].indices.filter { i -> lines.all { it[i] == ' ' } }

        val split = lines
            .map { splitAtIndices(it.padEnd(maxLength, ' '), splitIndices) }

        val worksheet = mutableListOf<Problem>()
        for (i in (split[0].indices)) {
            val numbers = split.dropLast(1).map { it[i] }
            val operation = split.last()[i]
            worksheet.add(Problem(numbers, operation.first()))
        }
        return worksheet
    }

    fun calculate(numbers: List<Long>, operation: Char): Long {
        return if (operation == '+') {
            numbers.sum()
        } else {
            numbers.reduce { a, b -> a * b }
        }
    }

    override fun part1(input: List<Problem>): String {
        return input.sumOf { (numbers, operation) ->
            calculate(numbers.map { it.trim().toLong() }, operation)
        }.toString()
    }

    fun toCephalopodNumbers(numbers: List<String>): List<Long> {
        return numbers[0].indices.map { i ->
            var total = 0L
            numbers.forEach {
                if (it[i] != ' ') {
                    total *= 10
                    total += it[i].digitToInt()
                }
            }
            total
        }
    }

    override fun part2(input: List<Problem>): String {
        return input.sumOf { (numbers, operation) ->
            calculate(toCephalopodNumbers(numbers), operation)
        }.toString()
    }
}
