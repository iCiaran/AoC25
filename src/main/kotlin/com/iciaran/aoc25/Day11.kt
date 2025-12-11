package com.iciaran.aoc25

import java.io.File

class Day11 : Day<Map<String, List<String>>> {
    override fun getInput(): Map<String, List<String>> {
        val graph = File("inputs/11.txt")
            .readLines().associate { line ->

                val split = line.split(":", limit = 2)
                val device = split.first().trim()
                val outputs = split.last().trim().split(" ")

                Pair(device, outputs)
            }

        val missing = graph.values
            .flatten()
            .filter { !graph.containsKey(it) }
            .associateWith { emptyList<String>() }

        return graph + missing
    }

    fun getInDegrees(graph: Map<String, List<String>>): Map<String, Int> {
        val outputs = graph.flatMap { (_, values) -> values }

        return graph.keys
            .associateWith { device -> outputs.count { it == device } }
    }

    fun topologicalSort(graph: Map<String, List<String>>): List<String> {
        val inDegrees = getInDegrees(graph).toMutableMap()
        val queue = ArrayDeque(inDegrees.filter { it.value == 0 }.map { it.key })

        val topologicalOrder = mutableListOf<String>()

        while (queue.isNotEmpty()) {
            val device = queue.removeFirst()
            topologicalOrder.add(device)

            for (neighbor in graph[device]!!) {
                inDegrees[neighbor] = inDegrees[neighbor]!! - 1
                if (inDegrees[neighbor] == 0) {
                    queue.add(neighbor)
                }
            }
        }

        return topologicalOrder.toList()
    }

    fun countPaths(
        graph: Map<String, List<String>>,
        sorted: List<String>,
        start: String,
        target: String
    ): Long {
        val numberOfPaths = graph.keys
            .associateBy({ it }, { 0L }).toMutableMap()
        numberOfPaths[start] = 1L

        sorted.forEach { device ->
            graph[device]?.forEach { neighbour ->
                numberOfPaths[neighbour] = numberOfPaths[neighbour]!! + numberOfPaths[device]!!
            }
        }

        return numberOfPaths[target]!!
    }

    fun countPathsThrough(
        graph: Map<String, List<String>>,
        sorted: List<String>,
        devices: List<String>
    ): Long {
        return devices
            .zipWithNext { a, b -> countPaths(graph, sorted, a, b) }
            .reduce { a, b -> a * b }
    }

    override fun part1(input: Map<String, List<String>>): String {
        return countPaths(input, topologicalSort(input), "you", "out").toString()
    }

    override fun part2(input: Map<String, List<String>>): String {
        val sorted = topologicalSort(input)
        val dacFirst = countPathsThrough(input, sorted, listOf("svr", "dac", "fft", "out"))
        val fftFirst = countPathsThrough(input, sorted, listOf("svr", "fft", "dac", "out"))

        return (dacFirst + fftFirst).toString()
    }
}
