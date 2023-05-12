package com.eventric.repo

import android.util.Log
import com.eventric.vo.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.Filter
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.snapshots
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Singleton

const val U_TAG = "UserRepository"

@OptIn(DelicateCoroutinesApi::class)
@Singleton
class UserRepository @Inject constructor() {
    private var auth: FirebaseAuth = Firebase.auth
    private val db = Firebase.firestore
    private val users = db.collection("users")

    private val userFlow = MutableStateFlow(Pair("", User.EMPTY_USER))
    val user: StateFlow<Pair<String, User>> by this::userFlow

    init {
        GlobalScope.launch { refreshLoggedUser() }
    }

    suspend fun login(email: String, password: String) {

        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Log.d(U_TAG, "signInWithEmail:success")
                } else {
                    Log.d(U_TAG, "signInWithEmail:failure", task.exception)
                    throw IllegalStateException("User not found")
                }
            }
        refreshLoggedUser()
    }

    fun logout() {
        auth.signOut()
    }

    private suspend fun refreshLoggedUser() {
        val currentUser = auth.currentUser


        if (currentUser != null) {
            Log.d(U_TAG, "User Refreshed")
            getUser(currentUser.email.toString()).collect { value ->
                userFlow.value = value
            }
        }
        else {
            Log.w(U_TAG, "Unable to refresh the logged user")
        }

    }

    suspend fun createAccount(user: User, password: String) {
        auth.createUserWithEmailAndPassword(user.email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d(U_TAG, "createUserWithEmail:success")
                    createUser(user)
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w(U_TAG, "createUserWithEmail:failure", task.exception)
                }
            }

        refreshLoggedUser()
    }

    private fun createUser(user: User) {
        users.document(user.email)
            .set(user)
            .addOnSuccessListener {
                Log.d(U_TAG, "User written with ID: ${user.email}")
            }
            .addOnFailureListener { e ->
                Log.w(U_TAG, "Error adding User", e)
            }
    }

    fun getUser(id: String) = try {
        users.document(id)
            .snapshots().map { document: DocumentSnapshot ->
                Pair(document.id, document.toObject<User>() ?: User.EMPTY_USER)
            }
    } catch (e: Exception) {
        Log.w(U_TAG, "Error reading User", e)
        flowOf()
    }

    fun getAllUsers(
        filter: Filter = Filter(),
        order: String = "name",
    ) = try {
        users
            .where(filter)
            .orderBy(order)
            .snapshots().map { query: QuerySnapshot ->
                var docList = listOf<Pair<String, User>>()
                for (doc in query) {
                    docList = docList + Pair(doc.id, doc.toObject())
                }
                docList
            }
    } catch (e: Exception) {
        Log.w(U_TAG, "Error reading Users", e)
        flowOf()
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