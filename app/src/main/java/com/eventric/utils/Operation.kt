package com.eventric.utils

sealed class Operation
object SuccessOperation : Operation()
object LoadingOperation : Operation()
data class ErrorOperation(val error: Throwable) : Operation()

suspend fun tryOperation(fn: suspend () -> Unit) = try {
    LoadingOperation
    fn()
    SuccessOperation
} catch (e: Exception) {
    ErrorOperation(e)
}