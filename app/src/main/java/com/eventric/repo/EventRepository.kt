package com.eventric.repo

import com.eventric.vo.Event
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject
import javax.inject.Singleton

const val TAGevent = "EventRepository"

@Singleton
class EventRepository @Inject constructor() {
    private val eventFlow = MutableStateFlow(Event.EMPTY_EVENT)
    val event: StateFlow<Event> by this::eventFlow

    init {
    }

    suspend fun create(name: String, location: String) {

    }
}

sealed class eventOperation

object eventSuccessOperation : Operation()
object eventLoadingOperation : Operation()
data class eventErrorOperation(val error: Throwable) : Operation()

suspend fun eventtryOperation(fn: suspend () -> Unit) = try {
    LoadingOperation
    fn()
    SuccessOperation
} catch (e: CancellationException) {
    throw e
} catch (e: Exception) {
    ErrorOperation(e)
}