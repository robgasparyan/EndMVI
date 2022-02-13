package com.end.mvi.utils


sealed class EndResult<T>
class LoadingState<T> : EndResult<T>()
class SuccessState<T>(val data: T) : EndResult<T>()
class ErrorState<T>(val error: String) : EndResult<T>()
