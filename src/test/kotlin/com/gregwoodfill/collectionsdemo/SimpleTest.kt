package com.gregwoodfill.collectionsdemo

import org.junit.jupiter.api.Test
import java.util.stream.Collectors

class SimpleTest {

    @Test
    fun `loops over collection`() {
        val list = listOf("a", "b", "c")

        for (s in list) {
            println(s)
        }

        // Java 8
        list.stream().forEach { println(it) }

        // with lambda arg
        list.forEach { s -> println(s) }

        // with implict it
        list.forEach { println(it) }
    }

    @Test
    fun `transformation with map`() {
        val list: List<String> = listOf("a", "b", "c")

        val newList = mutableListOf<String>()
        for (s in list) {
            newList.add(s.toUpperCase())
        }

        // .map is a 1:1 transformation
        // each element returned in the map lambda is a new element in a new list

        // java 8 style
        // no intermediary list to append to
        list.stream().map { s -> s.toUpperCase() }
                .collect(Collectors.toList())
        // ["A","B","C"]

        // kotlin style
        list.map { s -> s.toUpperCase() }
        list.map { it.toUpperCase() }
    }

    @Test
    fun `filters results`() {
        val list = listOf("Greg", "Gregg", "Craig")

        list.filter { it.startsWith("G") }
        list.filter { name -> name.startsWith("G") }
        // [Greg, Gregg]

        list.takeLast(2)
    }
}
