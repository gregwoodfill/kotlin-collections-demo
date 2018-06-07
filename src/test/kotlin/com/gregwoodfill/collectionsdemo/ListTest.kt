package com.gregwoodfill.collectionsdemo

import com.gregwoodfill.collectionsdemo.domain.Person
import org.amshove.kluent.shouldBeEmpty
import org.amshove.kluent.shouldEqual
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import java.time.Month
import java.time.MonthDay
import java.util.stream.Collectors

class ListTest {
    @Nested
    @DisplayName("given a list of strings")
    class GivenAListOfStrings {
        val strings = listOf("a", "b", "c", "")

        @Test
        fun `it should filter and map`() {
            strings.filter { it.isBlank() }.size shouldEqual 1
            strings.map { "$it foo" }[0] shouldEqual "a foo"

            val filteredAndMapped = strings.filter { it.isNotBlank() }.map { it.toUpperCase() }
            filteredAndMapped shouldEqual listOf("A", "B", "C")
        }

        @Test
        fun `java 8 stream filter should work`() {
            val streamed = strings.stream().filter { it.isBlank() }.collect(Collectors.toList())
            streamed shouldEqual listOf("")
        }

        @Test
        fun `java 8 stream map should work`() {
            val mapped = strings
                    .stream()
                    .filter { it.isNotBlank() }
                    .map {
                        it.toUpperCase()
                    }.collect(Collectors.toList())
        }

        @Test
        fun `it should fold`() {
            listOf("a", "b", "c")
                    .reduce { acc, s -> "$acc-$s" } shouldEqual "a-b-c"
        }

        @Test
        fun `it should flatten`() {
            val twoDList = listOf(listOf(1, 2), listOf(3, 4))
            twoDList.flatten() shouldEqual listOf(1, 2, 3, 4)
        }
    }

    @Nested
    @DisplayName("given a map")
    class GivenMap {
        val mapDemo = mapOf(
                "NAME" to "greg",
                "CUSTOMERNUMBER" to "789")

        @Test
        fun `it should map keys and values`() {
            val transformed = mapDemo.mapKeys { it.key.toLowerCase() }
                    .mapValues { it.value.capitalize() }

            transformed["name"] shouldEqual "Greg"
        }
    }

    @Nested
    @DisplayName("given a list of objects")
    class GivenFamily {
        val greg = Person(
                firstName = "greg",
                lastName = "woodfill",
                birthday = MonthDay.of(Month.MARCH, 2))
        val amanda = Person(firstName = "amanda",
                lastName = "woodfill",
                birthday = MonthDay.of(Month.AUGUST, 2))
        val lillian = Person(firstName = "lillian",
                lastName = "woodfill",
                birthday = MonthDay.of(Month.AUGUST, 3))

        val family = listOf(greg, amanda, lillian)

        @Test
        fun `it should filter results`() {
            val augustBdays = family.filter { it.birthday.month == Month.AUGUST }
            augustBdays shouldEqual listOf(amanda, lillian)
        }

        @Test
        fun `it should map to a new list`() {
            val capitalized = family.map {
                it.copy(
                        firstName = it.firstName.capitalize(),
                        lastName = it.lastName.capitalize())
            }

            capitalized.filter { it.lastName == "woodfill" }.shouldBeEmpty()
            capitalized.filter { it.lastName == "Woodfill" }.size shouldEqual 3
        }
    }
}