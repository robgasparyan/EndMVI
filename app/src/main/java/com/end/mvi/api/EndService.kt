package com.end.mvi.api

import com.end.mvi.models.ClothesShoesModel
import com.end.mvi.models.ClothesShoesModelDTO
import io.reactivex.rxjava3.core.Observable
import retrofit2.http.GET

interface EndService {

    @GET("media/catalog/android_test_example.json")
    fun getDataFromSource1(): Observable<ClothesShoesModelDTO>

    @GET("media/catalog/example.json")
    fun getDataFromSource2(): Observable<ClothesShoesModelDTO>

}
