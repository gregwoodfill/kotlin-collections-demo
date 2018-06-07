package com.gregwoodfill.collectionsdemo.domain

import java.time.MonthDay

data class Person(
    val firstName: String,
    val lastName: String,
    val birthday: MonthDay
)