package com.iciaran.aoc25

import com.iciaran.aoc25.Day08.Connection
import java.io.File

data class Coordinate(val x: Long, val y: Long, val z: Long) {
    override fun toString() = "($x, $y, $z)"
}

class Day08 : Day<Pair<List<Coordinate>, List<Connection>>> {
    override fun getInput(): Pair<List<Coordinate>, List<Connection>> {
        val coordinates = File("inputs/08.txt")
            .readLines()
            .map {
                it.split(",")
                    .map(String::toLong)
                    .let { (x, y, z) -> Coordinate(x, y, z) }
            }
        val connections = getAllConnections(coordinates)

        return Pair(coordinates, connections)
    }

    data class Connection(val startIndex: Int, val endIndex: Int, val distanceSquared: Long)

    fun getAllConnections(coordinates: List<Coordinate>): List<Connection> {
        val connections = mutableListOf<Connection>()

        for (i in coordinates.indices) {
            for (j in i + 1..<coordinates.size) {
                val a = coordinates[i]
                val b = coordinates[j]
                val distanceSquared = (a.x - b.x) * (a.x - b.x) +
                        (a.y - b.y) * (a.y - b.y) +
                        (a.z - b.z) * (a.z - b.z)

                connections.add(Connection(i, j, distanceSquared))
            }
        }

        return connections
    }


    fun addToCircuits(circuits: MutableList<MutableSet<Int>>, connection: Connection) {
        val hasStart = circuits.indexOfFirst { circuit -> circuit.contains(connection.startIndex) }
        val hasEnd = circuits.indexOfFirst { circuit -> circuit.contains(connection.endIndex) }

        // Not in any circuits, make a new one
        if (hasStart == -1 && hasEnd == -1) {
            circuits.add(mutableSetOf(connection.startIndex, connection.endIndex))
            return
        }

        // In the same circuit, do nothing
        if (hasStart == hasEnd) {
            return
        }

        // Start is in a circuit, add end
        if (hasEnd == -1) {
            circuits[hasStart].add(connection.endIndex)
            return
        }

        // End is in a circuit, add start
        if (hasStart == -1) {
            circuits[hasEnd].add(connection.startIndex)
            return
        }

        // Spanning 2 circuits, join them
        circuits[hasStart].addAll(circuits[hasEnd])
        circuits.removeAt(hasEnd)
        return
    }

    override fun part1(input: Pair<List<Coordinate>, List<Connection>>): String {
        val connections = input.second
        val connectionsSortedByDistance = connections.sortedBy { it.distanceSquared }.take(1000)

        val circuits = mutableListOf<MutableSet<Int>>()
        connectionsSortedByDistance.forEach { connection -> addToCircuits(circuits, connection) }

        return circuits
            .map { it.size }
            .sorted()
            .takeLast(3)
            .reduce { a, b -> a * b }
            .toString()
    }

    override fun part2(input: Pair<List<Coordinate>, List<Connection>>): String {
        val connections = input.second
        val connectionsSortedByDistance = connections.sortedBy { it.distanceSquared }

        val circuits = mutableListOf<MutableSet<Int>>()

        connectionsSortedByDistance.forEach { connection ->
            addToCircuits(circuits, connection)
            if (circuits.map { it.size }.maxOf { it } == input.first.size) {
                return (input.first[connection.startIndex].x * input.first[connection.endIndex].x).toString()
            }
        }

        return "wrong"
    }
}
