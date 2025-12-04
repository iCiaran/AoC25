package com.iciaran.aoc25

import kotlinx.benchmark.*
import org.openjdk.jmh.annotations.Fork

@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(BenchmarkTimeUnit.MICROSECONDS)
@State(Scope.Benchmark)
@Fork(1)
class Day04Benchmark {
    private lateinit var day: Day04
    private lateinit var input: List<List<Boolean>>

    @Setup
    fun setup() {
        day = Day04()
        input = day.getInput()
    }

    @Benchmark
    fun parseInput(): List<List<Boolean>> {
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
