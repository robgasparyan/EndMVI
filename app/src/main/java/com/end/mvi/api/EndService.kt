package com.end.mvi.api

import com.end.mvi.models.ClothesShoesModelDTO
import com.end.mvi.utils.EndResult
import retrofit2.http.GET

interface EndService {

    @GET("media/catalog/android_test_example.json")
    suspend fun getDataFromSource1(): ClothesShoesModelDTO

    @GET("media/catalog/example.json")
    suspend fun getDataFromSource2(): ClothesShoesModelDTO

}
