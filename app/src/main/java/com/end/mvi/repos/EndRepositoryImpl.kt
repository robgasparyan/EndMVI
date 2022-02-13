package com.end.mvi.repos

import com.end.mvi.api.EndService
import com.end.mvi.mappers.EndMapper
import com.end.mvi.models.ClothesShoesModel
import com.end.mvi.utils.ErrorState
import com.end.mvi.utils.SuccessState
import kotlinx.coroutines.flow.flow

class EndRepositoryImpl(
    private val apiService: EndService,
    private val endMapper: EndMapper
) : EndRepository {

    override suspend fun getClothesAndShoes() = flow {
        try {
            val firstResponse = apiService.getDataFromSource1()
            val secondResponse = apiService.getDataFromSource2()
            val clothesFirstResponse =
                endMapper.endClothesShoesDtoToClothesShoesModel(firstResponse)
            val clothesSecondResponse =
                endMapper.endClothesShoesDtoToClothesShoesModel(secondResponse)
            emit(
                SuccessState(
                    ClothesShoesModel(
                        clothesFirstResponse.product_count,
                        (clothesFirstResponse.products + clothesSecondResponse.products).filter { it.id == "1" },
                        clothesFirstResponse.title
                    )
                )
            )
        } catch (t: Throwable) {
            emit(ErrorState(t.localizedMessage))
        }
    }

}