package com.eventric.repo

import com.eventric.vo.User
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserRepository @Inject constructor() {
    private val userFlow = MutableStateFlow(User.EMPTY_USER)
    val user: StateFlow<User> by this::userFlow
    private var savedUsers = mutableListOf<User>()


    init {
        savedUsers += User(username = "admin", password = "admin", token = "")
    }

    suspend fun login(username: String, password: String) {
        delay(2000L)
        val userLogin =
            User(username = username, password = password, token = "")
        if (savedUsers.contains(userLogin)) {
            userFlow.value = userLogin
        } else throw IllegalStateException("User not found")
    }

    suspend fun logout(username: String, password: String) {
        userFlow.value = User.EMPTY_USER
    }

    suspend fun buildAuthorizationHeaders(): Map<String, String> {
        return mapOf()
    }

    suspend fun refreshToken() {}
}

sealed class Operation

object SuccessOperation : Operation()
object LoadingOperation : Operation()
data class ErrorOperation(val error: Throwable) : Operation()

suspend fun tryOperation(fn: suspend () -> Unit) = try {
    LoadingOperation
    fn()
    SuccessOperation
} catch (e: CancellationException) {
    throw e
} catch (e: Exception) {
    ErrorOperation(e)
}