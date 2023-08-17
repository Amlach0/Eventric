package com.eventric.vo

import com.eventric.utils.getDateFromMilli
import java.util.Calendar


data class User(
    val email: String,
    val name: String?,
    val surname: String?,
    val image: String?,
    val bio: String?,
    val birthDate: String?,
    val followingUsers: List<String>,
    val organizedEvents: List<String>,
    val subscribedEvents: List<String>,
    val favoriteEvents: List<String>,
    val notifications: List<Notification>,
) {
    constructor() : this(
        "",
        null,
        null,
        null,
        null,
        null,
        listOf(),
        listOf(),
        listOf(),
        listOf(),
        listOf()
    )
    constructor(email: String, name: String?, surname: String?, bio: String?, birthDate: String?): this(
        email,
        name,
        surname,
        null,
        bio,
        birthDate,
        listOf(),
        listOf(),
        listOf(),
        listOf(),
        listOf()
    )

    companion object {
        val EMPTY_USER = User(
            email = "",
            name = null,
            surname = null,
            image = null,
            bio = null,
            birthDate = null,
            followingUsers = listOf(),
            organizedEvents = listOf(),
            subscribedEvents = listOf(),
            favoriteEvents = listOf(),
            notifications = listOf()
        )
    }
}

data class Notification(
    val text: String?,
    val userId: String?,
    val eventId: String?,
    val time: String?,
) {
    constructor() : this(null, null, null, null)
    constructor(text: String?, userId: String?, eventId: String?) : this(
        text,
        userId,
        eventId,
        getDateFromMilli(Calendar.getInstance().time.time, "dd/MM/yyyy hh:mm")
    )

    override fun equals(other: Any?): Boolean =
        (other is Notification) && userId == other.userId && eventId == other.eventId

}
