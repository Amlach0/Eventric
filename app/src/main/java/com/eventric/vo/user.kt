package com.eventric.vo


data class User(
    val username: String,
    val password: String,
    val token: String,
) {
    companion object {
        val EMPTY_USER = User(
            username = "",
            password = "",
            token = "",
        )
    }
}
