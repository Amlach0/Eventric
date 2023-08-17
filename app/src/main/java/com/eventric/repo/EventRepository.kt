package com.eventric.repo

import android.util.Log
import com.eventric.vo.Event
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.Filter
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.snapshots
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import javax.inject.Singleton

const val E_TAG = "EventRepository"

@Singleton
class EventRepository @Inject constructor() {
    private val db = Firebase.firestore
    private val events = db.collection("events")

    suspend fun createEvent(event: Event): String {
        return try {
            val refDoc = events.add(event).await()
            Log.d(E_TAG, "Event written with ID: ${refDoc.id}")
            refDoc.id
        } catch (ce: CancellationException) {
            throw ce
        } catch (e: Exception) {
            Log.w(E_TAG, "Error adding Event", e)
            ""
        }
    }

    fun getEvent(id: String) = try {
        events.document(id)
            .snapshots().map { document: DocumentSnapshot ->
                Pair(document.id, document.toObject<Event>())
            }
    } catch (ce: CancellationException) {
        throw ce
    } catch (e: Exception) {
        Log.w(E_TAG, "Error reading Event", e)
        flowOf()
    }

    fun getAllEvents(
        filter: Filter = Filter(),
        order: String = "name",
    ) = try {
        events
            .where(filter)
            .orderBy(order)
            .snapshots().map { query: QuerySnapshot ->
                var docList = listOf<Pair<String, Event>>()
                for (doc in query) {
                    docList = docList + Pair(doc.id, doc.toObject())
                }
                docList
            }
    } catch (ce: CancellationException) {
        throw ce
    } catch (e: Exception) {
        Log.w(E_TAG, "Error reading Events", e)
        flowOf()
    }

    private suspend fun updateEvent(
        eventId: String,
        mapFieldValue: Map<String, Any>
    ) {
        try {
            events.document(eventId).update(mapFieldValue).await()
            Log.d(E_TAG, "Event $eventId updated with this updates : \n $mapFieldValue")
        } catch (ce: CancellationException) {
            throw ce
        } catch (e: Exception) {
            Log.w(E_TAG, "Error updating Event", e)
        }
    }

    suspend fun deleteEvent(
        eventId: String,
    ) {
        try {
            events.document(eventId).delete().await()
            Log.d(E_TAG, "Event $eventId deleted")
        } catch (ce: CancellationException) {
            throw ce
        } catch (e: Exception) {
            Log.w(E_TAG, "Error deleting Event", e)
        }
    }

    suspend fun editEvent(
        eventId: String,
        event: Event
    ) {
        try {
            events.document(eventId).set(event).await()
            Log.d(E_TAG, "Event $eventId edited with this updates : \n $event")
        } catch (ce: CancellationException) {
            throw ce
        } catch (e: Exception) {
            Log.w(E_TAG, "Error editing Event", e)
        }
    }


    /**
     * Adds or Removes a User Id from the subscribed users of the event
     * @param eventId The event Id
     * @param userId The user Id
     * @param addOrRemove a boolean that tells if the Id has to be added (TRUE) or removed (FALSE)
     */
    suspend fun addOrRemoveSubscribe(
        eventId: String,
        userId: String,
        addOrRemove: Boolean,
    ) {
        val subscribed = getEvent(eventId).first().second?.subscribed?.toMutableList() ?: mutableListOf()

        if (addOrRemove) {
            if (!subscribed.contains(userId))
                subscribed.add(userId)
        }
        else
            subscribed.remove(userId)

        updateEvent(
            eventId = eventId,
            mapFieldValue = mapOf("subscribed" to subscribed.toList())
        )
    }
}
