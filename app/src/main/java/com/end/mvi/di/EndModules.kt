package com.end.mvi.di

import com.end.mvi.BuildConfig
import com.end.mvi.api.EndService
import com.end.mvi.mappers.EndMapper
import com.end.mvi.repos.EndRepository
import com.end.mvi.repos.EndRepositoryImpl
import com.end.mvi.utils.serializerModule
import com.end.mvi.viewmodels.EndViewModel
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.androidx.experimental.dsl.viewModel
import org.koin.core.module.Module
import org.koin.dsl.module
import org.koin.experimental.builder.factory
import org.koin.experimental.builder.factoryBy
import retrofit2.Retrofit
import java.util.concurrent.TimeUnit

@OptIn(ExperimentalSerializationApi::class)
val networkModule: Module = module {

    // OkHttpClient
    single {
        val builder = OkHttpClient().newBuilder()
        val logging = HttpLoggingInterceptor()
//        val networkAvailabilityInterceptor = NetworkAvailabilityInterceptor(get())

        if (BuildConfig.DEBUG) {
//            builder.addInterceptor(ChuckInterceptor(get()))
            logging.level = HttpLoggingInterceptor.Level.BODY
        } else {
            logging.level = HttpLoggingInterceptor.Level.BASIC
        }
        builder.addInterceptor(logging)
//        builder.addInterceptor(networkAvailabilityInterceptor)
            .connectTimeout(10, TimeUnit.SECONDS)
            .writeTimeout(
                1,
                TimeUnit.MINUTES
            ) // fix exception when uploading files with slow internet
            .readTimeout(1, TimeUnit.MINUTES)
        builder.build()
    }

    // Retrofit
    single {
        Retrofit.Builder()
            .addConverterFactory(get())
            .baseUrl(BuildConfig.END_API_BASEURL)
            .client(get())
            .build()
    }
    single {
        Json {
            ignoreUnknownKeys = true
            isLenient = true
            prettyPrint = true
            encodeDefaults = true
            classDiscriminator = "discriminator"
            serializersModule = serializerModule
        }.asConverterFactory("application/json".toMediaType())
    }

    single { get<Retrofit>().create(EndService::class.java) }

}

val viewModels: Module = module {
    viewModel<EndViewModel>()
}

val repositories: Module = module {
    factoryBy<EndRepository, EndRepositoryImpl>()
}

val mappers: Module = module {
    factory<EndMapper>()
}