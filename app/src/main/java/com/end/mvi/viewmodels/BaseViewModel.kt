package com.end.mvi.viewmodels

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.Channel.Factory.BUFFERED
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.receiveAsFlow

open class BaseViewModel : ViewModel() {
}

class Container<STATE, SIDE_EFFECT>(
    private val scope: CoroutineScope,
    private val initialState: STATE
) {
    private val _stateFlow = MutableStateFlow(initialState)
    val state: StateFlow<STATE> = _stateFlow
    private val _sideEffect = Channel<SIDE_EFFECT>(BUFFERED)
    val sideEffect: Flow<SIDE_EFFECT> = _sideEffect.receiveAsFlow()
    fun intent(transform: suspend Container<STATE, SIDE_EFFECT>.() -> Unit) =
        scope.launch(SINGLE_THREAD) {
            this@Container.transform()
        }

    suspend fun reduce(reducer: STATE. () -> STATE) =
        withContext(SINGLE_THREAD) {
            _stateFlow.value = _stateFlow.value.reducer()
        }

    suspend fun postSideEffect(event: SIDE_EFFECT) =
        _sideEffect.send(event)

    companion object {
        @OptIn(ObsoleteCoroutinesApi::class)
        private val SINGLE_THREAD = newSingleThreadContext("mvi")
    }
}