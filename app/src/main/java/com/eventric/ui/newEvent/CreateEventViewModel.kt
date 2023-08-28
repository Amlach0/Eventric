package com.eventric.ui.newEvent

import androidx.lifecycle.ViewModel
import com.eventric.repo.EventRepository
import com.eventric.repo.UserRepository
import com.eventric.utils.LoadingOperation
import com.eventric.utils.Operation
import com.eventric.utils.tryOperation
import com.eventric.vo.DateRange
import com.eventric.vo.Event
import com.eventric.vo.EventCategory
import com.eventric.vo.EventType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import javax.inject.Inject


@HiltViewModel
class CreateEventViewModel @Inject constructor(
    private val eventRepository: EventRepository,
    private val userRepository: UserRepository,
) : ViewModel() {

    var createEventCodeResult = MutableStateFlow<Operation?>(null)
    var deleteEventCodeResult = MutableStateFlow<Operation?>(null)

    val eventIdFlow = MutableStateFlow("")
    val eventFlow = eventIdFlow.flatMapLatest { id ->
        if (id != "")
            eventRepository.getEvent(id).map { it.second ?: Event() }
        else
            flowOf()
    }

    private val user = userRepository.user

    fun setEventId(id: String) {
        eventIdFlow.value = id
    }

    //TODO adding info
    suspend fun createOrEditEvent(
        name: String,
        location: String,
        category: EventCategory,
        type: EventType,
        startDate: String,
        endDate: String,
        startRegistrationDate: String,
        endRegistrationDate: String,
        info: String
    ) {
        createEventCodeResult.value = LoadingOperation
        val eventId = eventIdFlow.value

        val organizer = user.first().first

        val date = DateRange(startDate, endDate)
        val registrationDate = DateRange(startRegistrationDate, endRegistrationDate)
        val event = ( if(eventId == "") Event() else eventFlow.first() ).copy(
            name = name,
            info = info,
            location = location,
            category = category.dbString,
            type = type.dbString,
            date = date,
            dateRegistration = registrationDate,
            organizer = organizer
        )

        createEventCodeResult.value = tryOperation {
            if (eventId == "") {
                eventIdFlow.value = eventRepository.createEvent(event)
            } else
                eventRepository.editEvent(eventId, event)
        }
    }

    suspend fun deleteEvent() {
        val eventId = eventIdFlow.value
        deleteEventCodeResult.value = tryOperation {
            if (eventId != "")
                eventRepository.deleteEvent(eventId)
        }
    }
}