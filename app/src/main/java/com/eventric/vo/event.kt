package com.eventric.vo

import com.google.type.Date


data class Event(
    val name: String?,
    val image: String?,
    val location: String?,
    val date: DateRange?,
    val dateRegistration: DateRange?,
    val category: String?,
    val type: String?,
    val organizers: List<String?>,
    val subscribed: List<String?>,
)

data class DateRange(
    val start: Date,
    val end: Date,
)
