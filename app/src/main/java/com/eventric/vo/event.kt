package com.eventric.vo

import com.google.type.Date


data class Event(
    val name: String?,
    val image: String?,
    val location: String?,
    val date: DateRange?,
    val dateRegistration: DateRange?,
    val category: EventCategory?,
    val type: EventType?,
    val organizers: List<String?>,
    val subscribed: List<String?>,
){
    constructor(): this(null, null, null, null, null, EventCategory.NoCategory, EventType.InviteOnly, listOf(), listOf())
    constructor(name: String, location: String?, category: EventCategory, type: EventType, date: DateRange?, registrationDate: DateRange?): this(name, null, location, date, registrationDate, category, type, listOf(), listOf())
}

data class DateRange(
    val start: String,
    val end: String,
)
