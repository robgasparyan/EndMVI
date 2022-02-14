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
                        product_count = clothesFirstResponse.product_count + clothesSecondResponse.product_count,
                        products = (clothesFirstResponse.products + clothesSecondResponse.products).sortedBy { it.id == "1" },
                        title = clothesFirstResponse.title
                    )
                )
            )
        } catch (t: Throwable) {
            emit(ErrorState(t.localizedMessage))
        }
    }

}