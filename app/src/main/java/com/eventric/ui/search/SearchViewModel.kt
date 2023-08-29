package com.eventric.ui.search

import android.net.Uri
import android.util.Log
import androidx.lifecycle.ViewModel
import com.eventric.repo.EventRepository
import com.eventric.repo.ImagesRepository
import com.eventric.repo.UserRepository
import com.eventric.utils.getMilliFromDate
import com.eventric.vo.Event
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine
import java.util.Calendar
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    eventRepository: EventRepository,
    userRepository: UserRepository,
    imagesRepository: ImagesRepository,
) : ViewModel() {

    val searchWordFlow = MutableStateFlow("")

    private val userFlow = userRepository.user

    val events: Flow<List<Pair<Triple<Pair<String, Event>, Boolean, Uri>, Boolean>>> =
        combine(
            eventRepository.getAllEvents(),
            userRepository.getAllUsers(),
            userFlow,
            searchWordFlow,
            imagesRepository.downloadAllEventsImages()
        ) { events, users, (loggedUserId, loggedUser), searchWord, eventsImages ->
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
                    Log.d("test", "event")
                    val organizerUser = users.find { it.first == event.organizer }?.second

                    Pair(
                        Triple(
                            Pair(
                                id,
                                event.copy(
                                    organizer = "${organizerUser?.name} ${organizerUser?.surname}"
                                )
                            ),
                            loggedUser.favoriteEvents.contains(id),
                            eventsImages[id] ?: Uri.EMPTY
                        ),
                        event.name?.contains(searchWord) == true
                                || event.location?.contains(searchWord) == true
                                || event.date?.start?.contains(searchWord) == true
                                || event.info?.contains(searchWord) == true
                                || event.type?.contains(searchWord) == true
                    )
                }
        }

    fun setSearchWord(word: String) {
        searchWordFlow.value = word
    }
}