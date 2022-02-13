package com.end.mvi.mappers

import com.end.mvi.models.ClothesShoesModel
import com.end.mvi.models.ClothesShoesModelDTO

class EndMapper {

    fun endClothesShoesDtoToClothesShoesModel(clothesShoesModelDTO: ClothesShoesModelDTO): ClothesShoesModel {
        return ClothesShoesModel(
            product_count = clothesShoesModelDTO.product_count,
            title = clothesShoesModelDTO.title,
            products = clothesShoesModelDTO.products.map {
                ClothesShoesModel.Product(
                    id = it.id,
                    image = it.image,
                    name = it.name,
                    price = it.price
                )
            }
        )
    }
}