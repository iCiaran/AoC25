package com.iciaran.aoc25

import kotlinx.benchmark.*
import org.openjdk.jmh.annotations.Fork

@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(BenchmarkTimeUnit.MICROSECONDS)
@State(Scope.Benchmark)
@Fork(1)
class Day07Benchmark {
    private lateinit var day: Day07
    private lateinit var input: Manifold

    @Setup
    fun setup() {
        day = Day07()
        input = day.getInput()
    }

    @Benchmark
    fun parseInput(): Manifold {
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
