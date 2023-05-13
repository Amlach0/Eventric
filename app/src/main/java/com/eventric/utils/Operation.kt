package com.eventric.utils

import kotlinx.coroutines.CancellationException

sealed class Operation
object SuccessOperation : Operation()
object LoadingOperation : Operation()
data class ErrorOperation(val error: Throwable) : Operation()

suspend fun tryOperation(fn: suspend () -> Unit) = try {
    LoadingOperation
    fn()
    SuccessOperation
} catch (e: CancellationException) {
    throw e
} catch (e: Exception) {
    ErrorOperation(e)
}