package com.eventric.ui.events

import android.util.Log
import androidx.lifecycle.ViewModel
import com.eventric.repo.EventRepository
import com.eventric.repo.UserRepository
import com.eventric.vo.Event
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import javax.inject.Inject

@HiltViewModel
class EventsViewModel @Inject constructor(
    private val eventRepository: EventRepository,
    private val userRepository: UserRepository,
) : ViewModel() {

    private val userFlow = userRepository.user

    fun getEvents(): Flow<List<Pair<Triple<String, Boolean, Event>, Triple<Boolean, Boolean, Boolean>>>> {
        Log.d("test", "start get")
        return combine(
            eventRepository.getAllEvents(),
            userRepository.getAllUsers(),
            userFlow
        ) { events, users, (loggedUserId, loggedUser) ->
            events
                .filter { (eventId, event) ->
                    event.organizer == loggedUserId ||
                    loggedUser.favoriteEvents.contains(eventId) ||
                    event.subscribed.contains(loggedUserId)

                }
                .map { (id, event) ->
                    Log.d("test", "event")
                    val organizerUser = users.find { it.first == event.organizer }?.second
                    Pair(
                        Triple(
                            id,
                            loggedUser.favoriteEvents.contains(id),
                            event.copy(
                                organizer = "${organizerUser?.name} ${organizerUser?.surname}"
                            )
                        ),
                        Triple(
                            event.organizer == loggedUserId,
                            event.subscribed.contains(loggedUserId),
                            loggedUser.favoriteEvents.contains(id)
                        )
                    )
                }
        }
    }
}