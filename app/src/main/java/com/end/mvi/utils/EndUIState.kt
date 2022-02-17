package com.end.mvi.utils

import android.os.Bundle

sealed class EndMainPageUIState<out T : Any> {
    class SucceedEndClothes<out T : Any>(val data: T) : EndMainPageUIState<T>()
    class Error<out T : Any>(val massage: String) : EndMainPageUIState<T>()
    object Loading : EndMainPageUIState<Nothing>()
}

//fun <T : Any> EndResult<T>.map(): EndUIState<T> {
//    return when (this) {
//        is SuccessState -> EndUIState.Data(this.data)
//        is ErrorState -> EndUIState.Fail.Exception(this.error)
//        is LoadingState -> EndUIState.Loading
//    }
//}

sealed class NavigationToNextScreen {
    class NavigationToDetailsScreen(val bundle: Bundle?) : NavigationToNextScreen()
}