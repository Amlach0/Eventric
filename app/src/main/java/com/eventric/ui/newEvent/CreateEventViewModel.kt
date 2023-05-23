package com.eventric.ui.newEvent

import androidx.compose.runtime.remember
import androidx.lifecycle.ViewModel
import com.eventric.repo.EventRepository
import com.eventric.utils.LoadingOperation
import com.eventric.utils.Operation
import com.eventric.utils.tryOperation
import com.eventric.vo.DateRange
import com.eventric.vo.Event
import com.eventric.vo.EventCategory
import com.eventric.vo.EventType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject


@HiltViewModel
class CreateEventViewModel @Inject constructor(
    private val eventRepository: EventRepository
) : ViewModel() {

    var createEventCodeResult = MutableStateFlow<Operation?>(null)

    suspend fun createEvent(name: String, location: String, category: EventCategory, type: EventType, startDate: String, endDate: String, startRegistrationDate: String, endRegistrationDate: String) {
        createEventCodeResult.value = LoadingOperation;

        var date: DateRange = DateRange(startDate, endDate)
        var registrationDate: DateRange = DateRange(startRegistrationDate, endRegistrationDate)
        var event: Event = Event(name, location, category, type, date, registrationDate)

        createEventCodeResult.value = tryOperation {
            eventRepository.createEvent(event)
        }
    }
}