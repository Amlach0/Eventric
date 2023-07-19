package com.eventric.vo

import com.google.type.Date


data class User(
    val email: String,
    val name: String?,
    val surname: String?,
    val image: String?,
    val bio: String?,
    val birthDate: String?,
    val organizedEvents: List<String?>,
    val subscribedEvents: List<String?>,
    val favoriteEvents: List<String?>,
) {
    constructor(): this("", null, null, null, null , null, listOf(), listOf() , listOf())
    constructor(email: String, name: String?, surname: String?, bio: String?, birthDate: String?): this(email, name, surname, null, bio, birthDate, listOf(), listOf(), listOf())

    companion object {
        val EMPTY_USER = User(
            email = "",
            name = null,
            surname = null,
            image = null,
            bio = null,
            birthDate = null,
            organizedEvents = listOf(),
            subscribedEvents = listOf(),
            favoriteEvents = listOf(),
        )
    }
}
