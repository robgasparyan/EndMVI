package com.end.mvi.utils

sealed class EndUIState<out T : Any> {
    class Data<out T : Any>(val data: T) : EndUIState<T>()
    sealed class Fail<out T : Any> : EndUIState<T>() {
        class Exception<out T : Any>(val massage: String) : Fail<T>()
        object NoInternet : Fail<Nothing>()
    }

    object Loading : EndUIState<Nothing>()
}

fun <T : Any> EndResult<T>.map(): EndUIState<T> {
    return when (this) {
        is SuccessState -> EndUIState.Data(this.data)
        is ErrorState -> EndUIState.Fail.Exception(this.error)
        is LoadingState -> EndUIState.Loading
    }
}