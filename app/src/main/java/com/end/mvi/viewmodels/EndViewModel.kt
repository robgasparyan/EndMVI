package com.end.mvi.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.end.mvi.models.ClothesShoesModel
import com.end.mvi.repos.EndRepository
import com.end.mvi.utils.EndUIState
import com.end.mvi.utils.map
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class EndViewModel(
    private val endRepository: EndRepository
) : ViewModel() {

    private val _endData = MutableStateFlow<EndUIState<ClothesShoesModel>>(EndUIState.Loading)
    val endData: StateFlow<EndUIState<ClothesShoesModel>> = _endData

    fun getClothes() = viewModelScope.launch {
        endRepository.getClothesAndShoes().collect {
            _endData.value = it.map()
        }
    }

}