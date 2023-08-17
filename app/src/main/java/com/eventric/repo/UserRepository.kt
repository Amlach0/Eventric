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
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import javax.inject.Singleton

const val U_TAG = "UserRepository"

@Singleton
class UserRepository @Inject constructor() {
    private var auth: FirebaseAuth = Firebase.auth
    private val db = Firebase.firestore
    private val users = db.collection("users")

    private val currentUserFlow = MutableStateFlow(auth.currentUser)
    val user = currentUserFlow.flatMapLatest { currentUser ->
        Log.d(U_TAG, "User already logged -> currentUser : ${currentUserFlow.value?.email}")
        getUser(currentUser?.email.toString())
    }


    suspend fun login(email: String, password: String) {

        try {
            auth.signInWithEmailAndPassword(email, password).await()
            refreshLoggedUser()
            Log.d(U_TAG, "signInWithEmail:success")
        } catch (ce: CancellationException) {
            throw ce
        } catch (e: Exception) {
            Log.w(U_TAG, "signInWithEmail:failure", e)
            throw IllegalStateException("User not found")
        }
    }

    fun logout() {
        auth.signOut()
    }

    private fun refreshLoggedUser() {
        val currentUser = auth.currentUser
        currentUserFlow.value = currentUser
        Log.d(U_TAG, "User refreshed -> currentUser : ${currentUserFlow.value?.email}")
    }

    suspend fun createAccount(user: User, password: String) {
        try {
            auth.createUserWithEmailAndPassword(user.email, password).await()
            Log.d(U_TAG, "createUserWithEmail:success")
            createUser(user)
            refreshLoggedUser()
        } catch (ce: CancellationException) {
            throw ce
        } catch (e: Exception) {
            Log.w(U_TAG, "createUserWithEmail:failure", e)
        }

    }

    private suspend fun createUser(user: User) {
        try {
            users.document(user.email)
                .set(user).await()
            Log.d(U_TAG, "User written with ID: ${user.email}")
        } catch (ce: CancellationException) {
            throw ce
        } catch (e: Exception) {
            Log.w(U_TAG, "Error adding User", e)
        }
    }

    fun getUser(id: String) = try {
        users.document(id)
            .snapshots().map { document: DocumentSnapshot ->
                Pair(document.id, document.toObject<User>() ?: User.EMPTY_USER)
            }
    } catch (ce: CancellationException) {
        throw ce
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
    } catch (ce: CancellationException) {
        throw ce
    } catch (e: Exception) {
        Log.w(U_TAG, "Error reading Users", e)
        flowOf()
    }

    private suspend fun updateUser(
        userId: String,
        mapFieldValue: Map<String, Any>,
    ) {
        try {
            users.document(userId).update(mapFieldValue).await()
            Log.d(E_TAG, "User $userId updated with this updates : \n $mapFieldValue")
        } catch (ce: CancellationException) {
            throw ce
        } catch (e: Exception) {
            Log.w(E_TAG, "Error updating Event", e)
        }
    }


    /**
     * Adds or Removes a event Id from the favorite events of the user
     * @param userId The user Id
     * @param eventId The event Id
     * @param addOrRemove a boolean that tells if the Id has to be added (TRUE) or removed (FALSE)
     */
    suspend fun addOrRemoveFavorite(
        userId: String,
        eventId: String,
        addOrRemove: Boolean,
    ) {
        val favorites = getUser(userId).first().second.favoriteEvents.toMutableList()

        if (addOrRemove) {
            if (!favorites.contains(eventId))
                favorites.add(eventId)
        }
        else
            favorites.remove(eventId)

        updateUser(
            userId = userId,
            mapFieldValue = mapOf("favoriteEvents" to favorites.toList())
        )
    }

    /**
     * Adds or Removes a following User Id from the following users of the followed user
     * @param followedUserId The Followed user Id
     * @param followingUserId The Following user Id
     * @param addOrRemove a boolean that tells if the Id has to be added (TRUE) or removed (FALSE)
     */
    suspend fun addOrRemoveFollow(
        followedUserId: String,
        followingUserId: String,
        addOrRemove: Boolean,
    ) {
        val following = getUser(followingUserId).first().second.followingUsers.toMutableList()

        if (addOrRemove) {
            if (!following.contains(followedUserId))
                following.add(followedUserId)
        }
        else
            following.remove(followedUserId)

        updateUser(
            userId = followingUserId,
            mapFieldValue = mapOf("followingUsers" to following.toList())
        )
    }
}

