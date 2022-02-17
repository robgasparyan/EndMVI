package com.end.mvi.models

import androidx.annotation.Keep
import kotlinx.serialization.Serializable

@Keep
@Serializable
data class ClothesShoesModelDTO(
    val product_count: Int = 0,
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

operator fun ClothesShoesModelDTO.plus(clothesShoesModelDTO: ClothesShoesModelDTO): ClothesShoesModelDTO {
    return ClothesShoesModelDTO(
        product_count = this.product_count + clothesShoesModelDTO.product_count,
        products = this.products + clothesShoesModelDTO.products,
        title = this.title
    )
}