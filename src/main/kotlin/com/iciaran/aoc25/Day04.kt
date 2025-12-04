package com.iciaran.aoc25

import java.io.File

class Day04 : Day<List<List<Boolean>>> {
    override fun getInput(): List<List<Boolean>> {
        return File("inputs/04.txt")
            .readLines()
            .map { line -> line.toCharArray().map { it == '@' } }
    }

    data class Point(val x: Int, val y: Int) {
        fun add(other: Point): Point {
            return Point(x + other.x, y + other.y)
        }
    }

    val adjacentOffsets = listOf(
        Point(-1, -1), Point(0, -1), Point(1, -1),
        Point(-1, 0), Point(1, 0),
        Point(-1, 1), Point(0, 1), Point(1, 1)
    )

    fun countAdjacentRolls(grid: List<List<Boolean>>, location: Point): Int {
        return adjacentOffsets.count {
            val next = location.add(it)
            next.y in 0..<grid.size && next.x in 0..<grid[0].size && grid[next.y][next.x]
        }
    }

    fun findRemovable(grid: List<List<Boolean>>): List<List<Boolean>> {
        return grid.mapIndexed { y, row ->
            row.mapIndexed { x, _ ->
                grid[y][x] && countAdjacentRolls(
                    grid,
                    Point(x, y)
                ) < 4
            }
        }
    }

    override fun part1(input: List<List<Boolean>>): String {
        return findRemovable(input).flatten().count { it }.toString()
    }

    override fun part2(input: List<List<Boolean>>): String {
        var removed = 0
        val next = input.map { it.toMutableList() }

        do {
            val toRemove = findRemovable(next)
            val numberRemoved = toRemove.flatten().count { it }
            removed += numberRemoved

            for (y in next.indices) {
                for (x in next[0].indices) {
                    if (next[y][x] && toRemove[y][x]) {
                        next[y][x] = false
                    }
                }
            }
        } while (numberRemoved > 0)

        return removed.toString()
    }
}
