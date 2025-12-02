package com.iciaran.aoc25

import java.io.File
import kotlin.math.floor
import kotlin.math.log10
import kotlin.math.pow

class Day02 : Day<List<Pair<Long, Long>>> {
    override fun getInput(): List<Pair<Long, Long>> {
        return File("inputs/02.txt")
            .readText()
            .trim()
            .split(",")
            .map { it.split("-") }
            .map { Pair(it[0].toLong(), it[1].toLong()) }
    }

    fun getInvalid(range: ClosedRange<Long>, repeats: Int): List<Long> {
        val startDigits = 1 + floor(log10(range.start.toDouble())).toInt()
        val endDigits = 1 + floor(log10(range.endInclusive.toDouble())).toInt()

        if (startDigits == endDigits && startDigits % repeats != 0) {
            return listOf()
        }

        val invalid = mutableListOf<Long>()
        var digits = 0

        do {
            // Calculate a multiplier that gives R repeats when multiplied by a number with N digits
            //
            // For example:
            //  R = 2
            //      N = 1 -> We need to multiply by 11
            //               1 * 11 = 11
            //               2 * 11 = 22
            //      N = 2 -> We need to multiply by 101
            //               11 * 101 = 1111
            //               12 * 101 = 1212
            //  R = 3
            //      N = 1 -> We need to multiply by 111
            //               1 * 111 = 111
            //               2 * 111 = 222
            //      N = 2 -> We need to multiply by 10101
            //               11 * 10101 = 111111
            //               12 * 10101 = 121212
            //
            //  https://oeis.org/A062397
            //  https://oeis.org/A066138

            digits += 1
            var multiplier: Long = 1
            for (i in (1..<repeats)) {
                multiplier += 10.toDouble().pow(digits * i).toLong()
            }

            val lowerIndex = 10.toDouble().pow(digits - 1).toLong()
            val upperIndex = 10.toDouble().pow(digits).toLong()

            if (upperIndex * multiplier < range.start) {
                continue
            }

            for (i in (lowerIndex..<upperIndex)) {
                if (i * multiplier in range) {
                    invalid.add(i * multiplier)
                }
            }

        } while (lowerIndex * multiplier < range.endInclusive)

        return invalid
    }

    fun getInvalid(range: ClosedRange<Long>): List<Long> {
        // Some invalid numbers can be generated in multiple ways
        // 10 repeated 4 times   -> 10101010
        // 1010 repeated 2 times -> 10101010
        // Use a set to only keep unique invalid numbers

        val invalid = mutableSetOf<Long>()
        val maxDigits = floor(log10(range.endInclusive.toDouble())).toInt() + 1

        for (i in (2..maxDigits)) {
            invalid.addAll(
                getInvalid(
                    range,
                    i
                )
            )
        }

        return invalid.toList()
    }

    override fun part1(input: List<Pair<Long, Long>>): String {
        return input
            .fold(0) { acc: Long, (start, finish) ->
                acc + getInvalid(
                    start..finish,
                    2
                ).sum()
            }
            .toString()
    }

    override fun part2(input: List<Pair<Long, Long>>): String {
        return input
            .fold(0) { acc: Long, (start, finish) ->
                acc + getInvalid(
                    start..finish
                ).sum()
            }
            .toString()
    }

}
