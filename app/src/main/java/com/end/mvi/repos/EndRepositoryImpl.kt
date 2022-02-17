package com.end.mvi.repos

import com.end.mvi.api.EndService
import com.end.mvi.mappers.EndMapper
import com.end.mvi.models.ClothesShoesModel
import com.end.mvi.models.plus
import io.reactivex.rxjava3.core.Observable

class EndRepositoryImpl(
    private val apiService: EndService,
    private val endMapper: EndMapper
) : EndRepository {

    override fun getClothesAndShoes(): Observable<ClothesShoesModel> {
        return Observable.zip(
            apiService.getDataFromSource1(),
            apiService.getDataFromSource2()
        ) { firstResponse, secondResponse ->
            firstResponse + secondResponse
        }.map {
            endMapper.endClothesShoesDtoToClothesShoesModel(it)
        }
    }
}