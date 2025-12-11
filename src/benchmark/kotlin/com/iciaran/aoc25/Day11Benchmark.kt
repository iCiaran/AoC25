package com.iciaran.aoc25

import kotlinx.benchmark.*
import org.openjdk.jmh.annotations.Fork

@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(BenchmarkTimeUnit.MILLISECONDS)
@State(Scope.Benchmark)
@Fork(1)
class Day11Benchmark {
    private lateinit var day: Day11
    private lateinit var input: Map<String, List<String>>

    @Setup
    fun setup() {
        day = Day11()
        input = day.getInput()
    }

    @Benchmark
    fun parseInput(): Map<String, List<String>> {
        return day.getInput()
    }

    @Benchmark
    fun partOne(): String {
        return day.part1(input)
    }

    @Benchmark
    fun partTwo(): String {
        return day.part2(input)
    }

}
