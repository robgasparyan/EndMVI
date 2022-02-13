package com.end.mvi.models

import androidx.annotation.Keep
import kotlinx.serialization.Serializable

@Keep
@Serializable
data class ClothesShoesModelDTO(
    val product_count: Int? = 0,
    val products: List<Product> = emptyList(),
    val title: String? = ""
) {
    @Keep
    @Serializable
    data class Product(
        val id: String,
        val image: String,
        val name: String,
        val price: String
    )
}