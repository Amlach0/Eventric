package com.eventric.ui.explore

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
class ExploreViewModel @Inject constructor(
    private val eventRepository: EventRepository,
    private val userRepository: UserRepository,
) : ViewModel() {

    private val userFlow = userRepository.user


    fun getEvents(): Flow<List<Triple<String, Boolean, Event>>> {
        Log.d("test", "start get")
        return combine(
            eventRepository.getAllEvents(),
            userRepository.getAllUsers(),
            userFlow
        ) { events, users, currentUser ->
            events.map { (id, event) ->
                Log.d("test", "event")
                val organizerUser = users.find { it.first == event.organizer }?.second
                Triple(
                    id,
                    currentUser.second.favoriteEvents.contains(id),
                    event.copy(
                        organizer = "${organizerUser?.name} ${organizerUser?.surname}"
                    )
                )
            }
        }
    }



}