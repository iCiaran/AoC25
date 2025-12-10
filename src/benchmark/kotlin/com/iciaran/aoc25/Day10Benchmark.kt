package com.iciaran.aoc25

import kotlinx.benchmark.*
import org.openjdk.jmh.annotations.Fork

@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(BenchmarkTimeUnit.MILLISECONDS)
@State(Scope.Benchmark)
@Fork(1)
class Day10Benchmark {
    private lateinit var day: Day10
    private lateinit var input: List<Machine>

    @Setup
    fun setup() {
        day = Day10()
        input = day.getInput()
    }

    @Benchmark
    fun parseInput(): List<Machine> {
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
