package com.eventric.repo

import android.util.Log
import com.eventric.vo.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Singleton

const val TAG = "UserRepository"

@Singleton
class UserRepository @Inject constructor() {
    private var auth: FirebaseAuth = Firebase.auth
    private val userFlow = MutableStateFlow(User.EMPTY_USER)
    val user: StateFlow<User> by this::userFlow

    init {
        GlobalScope.launch { refreshLoggedUser() }
    }

    suspend fun login(email: String, password: String) {

        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful){
                    Log.d(TAG, "signInWithEmail:success")
                }
                else{
                    Log.d(TAG, "signInWithEmail:failure", task.exception)
                    throw IllegalStateException("User not found")
                }
            }
        refreshLoggedUser()
    }

    suspend fun logout(username: String, password: String) {
        auth.signOut()
    }

    suspend fun refreshLoggedUser(){
        val currentUser = auth.currentUser
        userFlow.value = if (currentUser == null) User.EMPTY_USER
        else User(
            id=currentUser.uid,
            email = currentUser.email?: ""
        )
    }

    suspend fun CreateAccount(email: String, password: String) {

    }
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