package com.eventric.ui.events

import android.util.Log
import androidx.lifecycle.ViewModel
import com.eventric.repo.EventRepository
import com.eventric.repo.UserRepository
import com.eventric.vo.Event
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine
import javax.inject.Inject

@HiltViewModel
class EventsViewModel @Inject constructor(
    private val eventRepository: EventRepository,
    private val userRepository: UserRepository,
) : ViewModel() {

    private val selectedFilterFlow = MutableStateFlow("organized")

    private val userFlow = userRepository.user

    val getEvents: Flow<List<Pair<Triple<String, Boolean, Event>, Boolean>>> =
        combine(
            eventRepository.getAllEvents(),
            userRepository.getAllUsers(),
            userFlow,
            selectedFilterFlow
        ) { events, users, (loggedUserId, loggedUser), selectedFilter ->
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
                        when(selectedFilter){
                            "organized" -> event.organizer == loggedUserId
                            "subscribed" -> event.subscribed.contains(loggedUserId)
                            "favorites" -> loggedUser.favoriteEvents.contains(id)
                            else -> false
                        }
                    )
                }
        }

    fun setSelectedFilter(filter: String) {
        selectedFilterFlow.value = filter
    }
}