package com.eventric.vo


data class Event(
    val id: String,
    val name: String,
    val location: String,
) {
    companion object {
        val EMPTY_EVENT = Event(
            name = "",
            id = "",
            location = ""
        )
    }
}
