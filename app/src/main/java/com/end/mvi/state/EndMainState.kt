package com.end.mvi.state

import com.airbnb.mvrx.MavericksState


sealed class EndMainState<out T : Any> : MavericksState {
    class Data<out T : Any>(val data: T) : EndMainState<T>()
    class Exception<out T : Any>(val massage: String) : EndMainState<T>()
    class Click<out T : Any>(val id: Int) : EndMainState<T>()
    object Loading : EndMainState<Nothing>()
}
