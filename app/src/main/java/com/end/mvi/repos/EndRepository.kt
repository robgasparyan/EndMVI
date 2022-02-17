package com.end.mvi.repos

import com.end.mvi.models.ClothesShoesModel
import com.end.mvi.models.ClothesShoesModelDTO
import com.end.mvi.utils.EndResult
import io.reactivex.rxjava3.core.Flowable
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.disposables.Disposable
import kotlinx.coroutines.flow.Flow

interface EndRepository {

    fun getClothesAndShoes(): Observable<ClothesShoesModel>

}