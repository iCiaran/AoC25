package com.iciaran.aoc25

import java.io.File

data class KitchenDatabase(val ranges: List<ClosedRange<Long>>, val ids: List<Long>)

class Day05 : Day<KitchenDatabase> {

    override fun getInput(): KitchenDatabase {
        val input = File("inputs/05.txt").readLines()
        val ranges = input
            .takeWhile { it.isNotEmpty() }
            .map { it.split("-") }
            .map { (a, b) -> a.toLong()..b.toLong() }

        val ids = input.dropWhile { it.isNotEmpty() }.drop(1).map(String::toLong)

        return KitchenDatabase(ranges, ids)
    }

    fun mergeRanges(ranges: List<ClosedRange<Long>>): List<ClosedRange<Long>> {
        return ranges.sortedBy { it.start }
            .fold(listOf()) { acc, range ->
                if (acc.isNotEmpty() &&
                    acc.first().contains(range.start) &&
                    acc.first().contains(range.endInclusive)
                ) {
                    acc
                } else if (acc.isNotEmpty() &&
                    acc.first().contains(range.start)
                ) {
                    listOf(acc.first().start..range.endInclusive) + acc.drop(1)
                } else {
                    listOf(range) + acc
                }
            }
    }

    override fun part1(input: KitchenDatabase): String {
        val merged = mergeRanges(input.ranges)
        return input.ids.count { id -> merged.any { id in it } }.toString()
    }

    override fun part2(input: KitchenDatabase): String {
        return mergeRanges(input.ranges).sumOf { it.endInclusive - it.start + 1 }.toString()
    }
}

