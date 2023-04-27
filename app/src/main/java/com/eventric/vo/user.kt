package com.eventric.vo


data class User(
    val email: String,
    val id: String,
) {
    companion object {
        val EMPTY_USER = User(
            email = "",
            id = "",
        )
    }
}
