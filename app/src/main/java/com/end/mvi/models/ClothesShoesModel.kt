package com.end.mvi.models

import androidx.annotation.Keep
import kotlinx.serialization.Serializable

@Keep
data class ClothesShoesModel(
    val product_count: Int,
    val products: List<Product>,
    val title: String?
) {
    @Keep
    data class Product(
        val id: String?,
        val image: String?,
        val name: String?,
        val price: String?
    )
}