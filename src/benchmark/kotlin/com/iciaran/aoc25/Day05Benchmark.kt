package com.iciaran.aoc25

import kotlinx.benchmark.*
import org.openjdk.jmh.annotations.Fork

@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(BenchmarkTimeUnit.MICROSECONDS)
@State(Scope.Benchmark)
@Fork(1)
class Day05Benchmark {
    private lateinit var day: Day05
    private lateinit var input: KitchenDatabase

    @Setup
    fun setup() {
        day = Day05()
        input = day.getInput()
    }

    @Benchmark
    fun parseInput(): KitchenDatabase {
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
