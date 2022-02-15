package com.end.mvi.repos

import com.end.mvi.api.EndService
import com.end.mvi.mappers.EndMapper
import com.end.mvi.models.ClothesShoesModel
import com.end.mvi.utils.ErrorState
import com.end.mvi.utils.SuccessState
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flow

class EndRepositoryImpl(
    private val apiService: EndService,
    private val endMapper: EndMapper
) : EndRepository {

    override suspend fun getClothesAndShoes() = flow {
        combine(getFromSource1(), getFromSource2()) { one, second ->
            val returnList = arrayListOf<ClothesShoesModel>()
            if (one is SuccessState) {
                returnList.add(one.data)
            } else if (one is ErrorState) {
                this.emit(ErrorState(one.error))
            }
            if (second is SuccessState) {
                returnList.add(second.data)
            } else if (second is ErrorState) {
                this.emit(ErrorState(second.error))
            }
            return@combine returnList
        }.collect { array ->
            emit(
                SuccessState(
                    ClothesShoesModel(
                        product_count = array.sumOf { it.product_count },
                        products = array.map { it.products }.flatten().sortedBy { it.id == "1" },
                        title = array[0].title
                    )
                )
            )
        }
    }

    fun getFromSource1() = flow {
        try {
            val firstResponse = apiService.getDataFromSource1()
            val clothesFirstResponse =
                endMapper.endClothesShoesDtoToClothesShoesModel(firstResponse)
            emit(
                SuccessState(
                    ClothesShoesModel(
                        product_count = clothesFirstResponse.product_count,
                        products = (clothesFirstResponse.products),
                        title = clothesFirstResponse.title
                    )
                )
            )
        } catch (e: Exception) {
            emit(ErrorState(e.localizedMessage))
        }
    }

    fun getFromSource2() = flow {
        try {
            val firstResponse = apiService.getDataFromSource2()
            val clothesFirstResponse =
                endMapper.endClothesShoesDtoToClothesShoesModel(firstResponse)
            emit(
                SuccessState(
                    ClothesShoesModel(
                        product_count = clothesFirstResponse.product_count,
                        products = (clothesFirstResponse.products),
                        title = clothesFirstResponse.title
                    )
                )
            )
        } catch (e: Exception) {
            emit(ErrorState(e.localizedMessage))
        }
    }

}