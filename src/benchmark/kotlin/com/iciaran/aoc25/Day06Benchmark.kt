package com.iciaran.aoc25

import kotlinx.benchmark.*
import org.openjdk.jmh.annotations.Fork

@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(BenchmarkTimeUnit.MICROSECONDS)
@State(Scope.Benchmark)
@Fork(1)
class Day06Benchmark {
    private lateinit var day: Day06
    private lateinit var input: List<Problem>

    @Setup
    fun setup() {
        day = Day06()
        input = day.getInput()
    }

    @Benchmark
    fun parseInput(): List<Problem> {
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
