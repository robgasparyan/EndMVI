package com.end.mvi.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.end.mvi.repos.EndRepository
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class EndViewModel(
    private val endRepository: EndRepository
) : ViewModel() {


    fun abc() = viewModelScope.launch {
        endRepository.getClothesAndShoes().collect {
            println()
        }
    }

}