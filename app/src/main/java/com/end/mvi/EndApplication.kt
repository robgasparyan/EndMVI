package com.end.mvi

import android.app.Application
import com.end.mvi.di.mappers
import com.end.mvi.di.networkModule
import com.end.mvi.di.repositories
import com.end.mvi.di.viewModels
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class EndApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@EndApplication)
            androidLogger(Level.NONE)
            modules(listOf(networkModule, viewModels, repositories, mappers))
        }
    }

}