package com.eventric.ui.explore

import android.net.Uri
import androidx.lifecycle.ViewModel
import com.eventric.repo.EventRepository
import com.eventric.repo.ImagesRepository
import com.eventric.repo.UserRepository
import com.eventric.utils.getMilliFromDate
import com.eventric.vo.Event
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import java.util.Calendar
import javax.inject.Inject

@HiltViewModel
class ExploreViewModel @Inject constructor(
    eventRepository: EventRepository,
    userRepository: UserRepository,
    imagesRepository: ImagesRepository,
) : ViewModel() {

    private val userFlow = userRepository.user


    val events: Flow<List<Triple<Pair<String, Event>, Boolean, Pair<Uri, Uri>>>> = combine(
        eventRepository.getAllEvents(),
        userRepository.getAllUsers(),
        userFlow,
        imagesRepository.downloadAllEventsImages(),
        imagesRepository.downloadAllUserImages()
    ) { events, users, (loggedUserId, loggedUser), eventsImages, usersImages ->
        events
            .filter { (_, event) ->
                val currentTime = Calendar.getInstance().time.time
                event.organizer != loggedUserId
                        && currentTime in getMilliFromDate(event.dateRegistration?.start ?: "", "dd/MM/yyyy hh:mm")..getMilliFromDate(event.dateRegistration?.end ?: "", "dd/MM/yyyy hh:mm")
                        && ( event.type == "public"
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
                        eventsImages[id] ?: Uri.EMPTY,
                        usersImages[event.organizer] ?: Uri.EMPTY
                    )
                )
            }
    }


}