package com.end.mvi.utils

import com.end.mvi.models.ClothesShoesModelDTO
import kotlinx.serialization.modules.SerializersModule
import kotlinx.serialization.modules.polymorphic

val serializerModule = SerializersModule {
    polymorphic(ClothesShoesModelDTO::class) {}
}
