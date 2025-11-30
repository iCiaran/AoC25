package com.iciaran.aoc25

sealed interface Day<T> {
    fun getInput(): T
    fun part1(input: T): String
    fun part2(input: T): String
}
