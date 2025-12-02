package com.iciaran.aoc25

import kotlinx.benchmark.*
import org.openjdk.jmh.annotations.Fork

@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(BenchmarkTimeUnit.MICROSECONDS)
@State(Scope.Benchmark)
@Fork(1)
class Day01Benchmark {
    private lateinit var day: Day01
    private lateinit var input: List<Pair<Char, Int>>

    @Setup
    fun setup() {
        day = Day01()
        input = day.getInput()
    }

    @Benchmark
    fun parseInput(): List<Pair<Char, Int>> {
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
