package com.eventric.vo

import androidx.compose.ui.graphics.Color
import com.eventric.R


data class Event(
    val name: String?,
    val image: String?,
    val info: String?,
    val location: String?,
    val date: DateRange?,
    val dateRegistration: DateRange?,
    val category: String?,
    val type: String?,
    val organizer: String?,
    val subscribed: List<String?>,
){
    constructor(): this(null, null, null, null, null, null, EventCategory.NoCategory.dbString, EventType.Private.dbString, null, listOf())
    constructor(name: String, location: String?, category: EventCategory, type: EventType, date: DateRange?, registrationDate: DateRange?, organizer: String?): this(name, null, null, location, date, registrationDate, category.dbString, type.dbString, organizer, listOf())
}

data class DateRange(
    val start: String?,
    val end: String?,
) {
    constructor(): this(null, null)
}


sealed class EventCategory(var title: String, var icon: Int, var color: Color, var dbString: String) {
    object NoCategory : EventCategory("None", R.drawable.ic_category_none, Color(0xFF807A7A), "none")
    object Music : EventCategory("Music", R.drawable.ic_category_music, Color(0xFF6B7AED), "music")
    object Art : EventCategory("Art", R.drawable.ic_category_art, Color(0xFF39C3F2), "art")
    object Food : EventCategory("Food", R.drawable.ic_category_food, Color(0xFF29D697), "food")
    object Sport : EventCategory("Sport", R.drawable.ic_category_sport, Color(0xFFEE544A), "sport")

    override fun toString(): String {
        return this.dbString
    }

    constructor(): this("", 0, Color.Black, "")
}

sealed class EventType(var title: String, var icon: Int, var dbString: String) {
    object InviteOnly: EventType("Invite only", R.drawable.ic_type_invite, "invite_only")
    object Private: EventType("Private", R.drawable.ic_type_private, "private")
    object Public: EventType("Public", R.drawable.ic_type_public, "public")

    override fun toString(): String {
        return this.dbString
    }

    constructor(): this("", 0, "")
}