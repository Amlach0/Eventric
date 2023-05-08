package com.eventric.ui.newEvent

import androidx.lifecycle.ViewModel
import com.eventric.repo.LoadingOperation
import com.eventric.repo.Operation
import com.eventric.repo.UserRepository
import com.eventric.repo.tryOperation
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject


@HiltViewModel
class CreateEventViewModel @Inject constructor() : ViewModel() {

    var createEventCodeResult = MutableStateFlow<Operation?>(null)

    suspend fun createEvent(name: String, location: String) {

    }
}