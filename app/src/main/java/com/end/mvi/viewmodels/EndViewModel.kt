package com.end.mvi.viewmodels

import androidx.core.os.bundleOf
import androidx.lifecycle.viewModelScope
import com.end.mvi.models.ClothesShoesModel
import com.end.mvi.repos.EndRepository
import com.end.mvi.utils.EndUIState
import com.end.mvi.utils.NavigationToNextScreen
import com.end.mvi.utils.map
import kotlinx.coroutines.flow.collect

class EndViewModel(
    private val endRepository: EndRepository
) : BaseViewModel() {

    val container = Container<EndUIState<ClothesShoesModel>, NavigationToNextScreen>(
        viewModelScope,
        EndUIState.Loading
    )

    fun getClothes() = container.intent {
        endRepository.getClothesAndShoes().collect {
            container.reduce {
                it.map()
            }
        }
    }

    fun onRVItemClicked(clothesShoesProduct: ClothesShoesModel.Product) = container.intent {
        container.postSideEffect(event = NavigationToNextScreen.NavigationToDetailsScreen(bundleOf("navId" to clothesShoesProduct.id)))
    }

}