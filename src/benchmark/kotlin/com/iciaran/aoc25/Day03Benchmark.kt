package com.iciaran.aoc25

import kotlinx.benchmark.*
import org.openjdk.jmh.annotations.Fork

@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(BenchmarkTimeUnit.MICROSECONDS)
@State(Scope.Benchmark)
@Fork(1)
class Day03Benchmark {
    private lateinit var day: Day03
    private lateinit var input: List<List<Int>>

    @Setup
    fun setup() {
        day = Day03()
        input = day.getInput()
    }

    @Benchmark
    fun parseInput(): List<List<Int>> {
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
