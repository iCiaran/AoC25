package com.iciaran.aoc25

import kotlin.reflect.full.createInstance

fun <T> runDay(day: Day<T>) {
    println("Running ${day.javaClass.simpleName}")
    val input = day.getInput()
    println("Part 1: ${day.part1(input)}")
    println("Part 2: ${day.part2(input)}")
    println()
}

fun runAllDays() {
    Day::class.sealedSubclasses.forEach { subclass ->
        runDay(subclass.createInstance())
    }
}

fun runSingleDay(day: Int) {
    Day::class.sealedSubclasses.find { it.simpleName == "Day%02d".format(day) }?.let {
        c -> runDay(c.createInstance())
    } ?: println("Day $day not implemented")
}

fun main(args: Array<String>) {
    if (args.isEmpty() || args[0] == "all") {
        runAllDays()
    } else {
        runSingleDay(args[0].toInt())
    }
}

