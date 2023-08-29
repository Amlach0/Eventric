package com.eventric.ui.explore

import android.net.Uri
import androidx.lifecycle.ViewModel
import com.eventric.repo.EventRepository
import com.eventric.repo.ImagesRepository
import com.eventric.repo.UserRepository
import com.eventric.vo.Event
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.first
import javax.inject.Inject

@HiltViewModel
class ExploreViewModel @Inject constructor(
    private val eventRepository: EventRepository,
    private val userRepository: UserRepository,
    private val imagesRepository: ImagesRepository,
) : ViewModel() {

    private val userFlow = userRepository.user


    val events: Flow<List<Triple<Pair<String, Event>, Boolean, Pair<Uri, Uri>>>> = combine(
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
                val organizerUser = users.find { it.first == event.organizer }?.second
                Triple(
                    Pair(
                        id, event.copy(
                            organizer = "${organizerUser?.name} ${organizerUser?.surname}"
                        )
                    ),
                    loggedUser.favoriteEvents.contains(id),
                    Pair(
                        imagesRepository.downloadEventImage(id).first(),
                        imagesRepository.downloadUserImage(event.organizer.toString()).first()
                    )
                )
            }
    }


}