package com.iciaran.aoc25

import kotlinx.benchmark.*
import org.openjdk.jmh.annotations.Fork

@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(BenchmarkTimeUnit.MICROSECONDS)
@State(Scope.Benchmark)
@Fork(1)
class Day02Benchmark {
    private lateinit var day: Day02
    private lateinit var input: List<Pair<Long, Long>>

    @Setup
    fun setup() {
        day = Day02()
        input = day.getInput()
    }

    @Benchmark
    fun parseInput(): List<Pair<Long, Long>> {
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
