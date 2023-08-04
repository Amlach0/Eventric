package com.eventric.ui.infoEvent

import androidx.lifecycle.ViewModel
import com.eventric.repo.EventRepository
import com.eventric.repo.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.map
import javax.inject.Inject

@HiltViewModel
class InfoEventViewModel @Inject constructor(
    private val userRepository: UserRepository,
    private val eventRepository: EventRepository
) : ViewModel() {
    val userFlow = userRepository.user

    fun getEventFlow(id: String) = eventRepository.getEvent(id).map { it.second }


    suspend fun subscribe()
    {

    }
}
