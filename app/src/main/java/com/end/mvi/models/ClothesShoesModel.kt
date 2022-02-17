package com.end.mvi.models

import androidx.annotation.Keep
import kotlinx.serialization.Serializable

open class BaseEndResponse

@Keep
data class ClothesShoesModel(
    val product_count: Int,
    val products: List<Product>,
    val title: String?
): BaseEndResponse() {
    @Keep
    data class Product(
        val id: String?,
        val image: String?,
        val name: String?,
        val price: String?
    )
}

operator fun ClothesShoesModel.plus(clothesShoesModel: ClothesShoesModel): ClothesShoesModel {
    return ClothesShoesModel(
        product_count = this.product_count + clothesShoesModel.product_count,
        products = this.products + clothesShoesModel.products,
        title = this.title
    )
}