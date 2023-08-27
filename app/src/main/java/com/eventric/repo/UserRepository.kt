package com.eventric.repo

import android.util.Log
import com.eventric.vo.Notification
import com.eventric.vo.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.DocumentSnapshot
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
        if (currentUser != null) {
            Log.d(U_TAG, "User already logged -> currentUser : ${currentUserFlow.value?.email}")
            getUser(currentUser.email.toString())
        }
        else{
            Log.d(U_TAG, "User not logged")
            flowOf(Pair("",User.EMPTY_USER))
        }
    }


    suspend fun login(email: String, password: String) {

        try {
            auth.signInWithEmailAndPassword(email, password).await()
            refreshLoggedUser()
            Log.d(U_TAG, "signInWithEmail:success")
        }
        catch (e: Exception) {
            Log.w(U_TAG, "signInWithEmail:failure", e)
            throw e
        }
    }

    fun logout() = try {
        auth.signOut()
        refreshLoggedUser()
        Log.d(U_TAG, "signOut:success")
    }
    catch (e: Exception) {
        Log.w(U_TAG, "signOut:failure", e)
        throw e
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
        }
        catch (e: Exception) {
            Log.w(U_TAG, "createUserWithEmail:failure", e)
            throw e
        }

    }

    private suspend fun createUser(user: User) {
        try {
            users.document(user.email)
                .set(user).await()
            Log.d(U_TAG, "User written with ID: ${user.email}")
        } catch (e: Exception) {
            Log.w(U_TAG, "Error adding User", e)
            throw e
        }
    }

    fun getUser(id: String) = try {
        users.document(id)
            .snapshots().map { document: DocumentSnapshot ->
                Pair(document.id, document.toObject<User>() ?: User.EMPTY_USER)
            }
    } catch (ce: CancellationException) { throw ce }
    catch (e: Exception) {
        Log.w(U_TAG, "Error reading User", e)
        flowOf()
    }

    fun getAllUsers(
        order: String = "name",
    ) = try {
        users
            .orderBy(order)
            .snapshots().map { query: QuerySnapshot ->
                var docList = listOf<Pair<String, User>>()
                for (doc in query) {
                    docList = docList + Pair(doc.id, doc.toObject())
                }
                docList
            }
    } catch (ce: CancellationException) { throw ce }
    catch (e: Exception) {
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

    private suspend fun addOrRemoveNotification(
        userId: String,
        notification: Notification,
        addOrRemove: Boolean,
    ) {
        val notifications = getUser(userId).first().second.notifications.toMutableList()

        if (addOrRemove) {
            if (!notifications.contains(notification))
                notifications.add(notification)
        }
        else
            notifications.remove(notification)

        updateUser(
            userId = userId,
            mapFieldValue = mapOf("notifications" to notifications.toList())
        )
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

        val notification = Notification(
            text = " ha cominciato a seguirti",
            userId = followingUserId,
            eventId = null,
        )
        addOrRemoveNotification(
            userId = followedUserId,
            notification = notification,
            addOrRemove = true
        )
    }

    /**
     * Adds or Removes an invite from a user to an invited user for an event
     * @param userId The user Id
     * @param invitedUserId The invited user Id
     * @param eventId The event
     * @param addOrRemove a boolean that tells if the Id has to be added (TRUE) or removed (FALSE)
     */
    suspend fun addOrRemoveInvite(
        userId: String,
        invitedUserId: String,
        eventId: String,
        addOrRemove: Boolean,
    ) {
        val notification = Notification(
            text = " ti ha invitato all'evento ",
            userId = userId,
            eventId = eventId,
        )
        addOrRemoveNotification(
            userId = invitedUserId,
            notification = notification,
            addOrRemove = addOrRemove
        )
    }

    suspend fun clearAllNotifications(
        userId: String
    ){
        updateUser(
            userId = userId,
            mapFieldValue = mapOf("notifications" to listOf<Notification>())
        )
    }

    suspend fun editUser(
        userId: String,
        user: User,
        newPassword: String
    ) {
        try {
            users.document(userId).set(user).await()
            currentUserFlow.value?.updateEmail(user.email)?.await()
            currentUserFlow.value?.updatePassword(newPassword)?.await()
            Log.d(E_TAG, "User $userId edited with this updates : \n $user")
        } catch (ce: CancellationException) {
            throw ce
        } catch (e: Exception) {
            Log.w(E_TAG, "Error editing User", e)
        }
    }

    suspend fun deleteUser(
        userId: String,
    ) {
        try {
            users.document(userId).delete().await()
            Log.d(E_TAG, "User $userId deleted")
        } catch (ce: CancellationException) {
            throw ce
        } catch (e: Exception) {
            Log.w(E_TAG, "Error deleting User", e)
        }
    }
}

