package com.end.mvi.viewmodels

import com.airbnb.mvrx.MavericksViewModel
import com.end.mvi.models.ClothesShoesModel
import com.end.mvi.repos.EndRepository
import com.end.mvi.state.EndMainState
import com.end.mvi.utils.ErrorState
import com.end.mvi.utils.LoadingState
import com.end.mvi.utils.SuccessState
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class EndViewModel(
    private val endRepository: EndRepository
) : MavericksViewModel<EndMainState<ClothesShoesModel>>(EndMainState.Loading) {


    fun getClothes() = viewModelScope.launch {
        endRepository.getClothesAndShoes().collect {
            when (it) {
                is LoadingState -> {
                    setState { EndMainState.Loading }
                }
                is ErrorState -> {
                    setState { EndMainState.Exception(it.error) }
                }
                is SuccessState -> {
                    setState { EndMainState.Data(it.data) }
                }
            }
        }
    }

//    fun onRVItemClicked(clothesShoesProduct: ClothesShoesModel.Product) = container.intent {
//        container.postSideEffect(event = NavigationToNextScreen.NavigationToDetailsScreen(bundleOf("navId" to clothesShoesProduct.id)))
//    }

}