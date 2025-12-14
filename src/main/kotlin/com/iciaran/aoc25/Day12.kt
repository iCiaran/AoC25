package com.iciaran.aoc25

import java.io.File

data class Shape(val filled: List<Boolean>)
data class Region(val width: Int, val height: Int, val shapes: List<Int>)

class Day12 : Day<Pair<List<Shape>, List<Region>>> {
    override fun getInput(): Pair<List<Shape>, List<Region>> {
        val shapes = mutableListOf<Shape>()
        val regions = mutableListOf<Region>()

        val lines = File("inputs/12.txt").readLines()

        var start = 0

        for (i in lines.indices) {
            if ('x' in lines[i]) {
                val split = lines[i].split(":")
                val size = split[0].split("x").map(String::toInt)
                val regionShapes = split[1].trim().split(" ").map(String::toInt)

                regions.add(Region(size[0], size[1], regionShapes))
            }

            if (lines[i].isEmpty()) {
                shapes.add(Shape(lines.subList(start + 1, i).joinToString().map { it == '#' }))
                start = i + 1
            }
        }

        return Pair(shapes, regions)
    }

    fun canFit(region: Region, shapes: List<Shape>): Boolean {
        // Cheese :(
        
        val shapeFilledCount = shapes.map { shape -> shape.filled.count { it } }
        val shapesTotalArea = region.shapes.mapIndexed { i, c -> shapeFilledCount[i] * c }.sum()
        val regionArea = region.width * region.height

        if (shapesTotalArea > regionArea) {
            return false
        }

        val maxShapes = (region.width / 3) * (region.height / 3)
        val numberOfShapes = region.shapes.sum()

        return numberOfShapes <= maxShapes
    }

    override fun part1(input: Pair<List<Shape>, List<Region>>): String {
        val (shapes, regions) = input

        return regions.count { canFit(it, shapes) }.toString()
    }

    override fun part2(input: Pair<List<Shape>, List<Region>>): String {
        return ""
    }
}
