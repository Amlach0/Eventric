package com.eventric.ui.explore

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
        return combine(
            eventRepository.getAllEvents(),
            userRepository.getAllUsers(),
            userFlow
        ) { events, users, (loggedUserId, loggedUser) ->
            events
                .filter { (_, event) ->
                    event.organizer != loggedUserId
                            && (event.type == "public"
                                    || (event.type == "private"
                                    && loggedUser.followingUsers.contains(event.organizer)))
                }
                .map { (id, event) ->
                    Log.d("test", "event")
                    val organizerUser = users.find { it.first == event.organizer }?.second
                    Triple(
                        id,
                        loggedUser.favoriteEvents.contains(id),
                        event.copy(
                            organizer = "${organizerUser?.name} ${organizerUser?.surname}"
                        )
                    )
                }
        }
    }


}