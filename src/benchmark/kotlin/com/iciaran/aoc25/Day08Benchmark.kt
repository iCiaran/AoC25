package com.iciaran.aoc25

import com.iciaran.aoc25.Day08.Connection
import kotlinx.benchmark.*
import org.openjdk.jmh.annotations.Fork

@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(BenchmarkTimeUnit.MILLISECONDS)
@State(Scope.Benchmark)
@Fork(1)
class Day08Benchmark {
    private lateinit var day: Day08
    private lateinit var input: Pair<List<Coordinate>, List<Connection>>

    @Setup
    fun setup() {
        day = Day08()
        input = day.getInput()
    }

    @Benchmark
    fun parseInput(): Pair<List<Coordinate>, List<Connection>> {
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
