package com.eventric.ui.infoEvent

import androidx.lifecycle.ViewModel
import com.eventric.repo.EventRepository
import com.eventric.utils.LoadingOperation
import com.eventric.utils.Operation
import com.eventric.repo.UserRepository
import com.eventric.utils.tryOperation
import com.eventric.vo.Event
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

@HiltViewModel
class infoEventViewModel @Inject constructor(
    private val userRepository: UserRepository,
    private val eventRepository: EventRepository
) : ViewModel() {

    var infoEventCodeResult = MutableStateFlow<Operation?>(null)

    suspend fun get(id: String): Flow<Pair<String, Event?>> {
        infoEventCodeResult.value = LoadingOperation

        var event: Flow<Pair<String, Event?>>
        infoEventCodeResult.value = tryOperation {
            event = eventRepository.getEvent(id)
        }
        return event
    }

    suspend fun subscribe()
    {
        infoEventCodeResult.value = LoadingOperation

        infoEventCodeResult.value = tryOperation {
            //eventRepository.subscribe(EventId, UserId)
        }
    }
}
