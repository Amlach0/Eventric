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
import javax.inject.Inject


@HiltViewModel
class CreateEventViewModel @Inject constructor(
    private val eventRepository: EventRepository,
    private val userRepository: UserRepository
) : ViewModel() {

    var createEventCodeResult = MutableStateFlow<Operation?>(null)

    private val user = userRepository.user

    //TODO adding info
    suspend fun createEvent(name: String, location: String, category: EventCategory, type: EventType, startDate: String, endDate: String, startRegistrationDate: String, endRegistrationDate: String) {
        createEventCodeResult.value = LoadingOperation

        val organizer = user.first().first

        val date = DateRange(startDate, endDate)
        val registrationDate = DateRange(startRegistrationDate, endRegistrationDate)
        val event = Event(name, location, category.dbString, type.dbString, date, registrationDate, organizer)

        createEventCodeResult.value = tryOperation {
            eventRepository.createEvent(event)
        }
    }
}