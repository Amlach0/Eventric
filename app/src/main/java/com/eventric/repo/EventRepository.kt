package com.eventric.repo

import android.util.Log
import com.eventric.vo.Event
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.snapshots
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

const val E_TAG = "UserRepository"

@Singleton
class EventRepository @Inject constructor() {
    private val db = Firebase.firestore
    private val events = db.collection("events")

    fun createEvent(event: Event) {
        events.add(event)
            .addOnSuccessListener { documentReference ->
                Log.d(E_TAG, "Event written with ID: ${documentReference.id}")
            }
            .addOnFailureListener { e ->
                Log.w(E_TAG, "Error adding Event", e)
            }
    }

    fun getEvent(id: String) = try {
        events.document(id)
            .snapshots().map { document: DocumentSnapshot ->
                Pair(document.id, document.toObject<Event>())
            }
    } catch (e: Exception) {
        Log.w(E_TAG, "Error reading Event", e)
        flowOf()
    }

    fun getAllEvents() = try {
        events
            .snapshots().map { query: QuerySnapshot ->
                var docList = listOf<Pair<String, Event>>()
                for (doc in query) {
                    docList = docList + Pair(doc.id, doc.toObject())
                }
                docList
            }
    } catch (e: Exception) {
        Log.w(E_TAG, "Error reading Events", e)
        flowOf()
    }
}
