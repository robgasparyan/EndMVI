package com.end.mvi.repos

import com.end.mvi.models.ClothesShoesModel
import com.end.mvi.utils.EndResult
import kotlinx.coroutines.flow.Flow

interface EndRepository {

    suspend fun getClothesAndShoes(): Flow<EndResult<ClothesShoesModel>>

}