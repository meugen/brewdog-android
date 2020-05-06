package com.brewdog.android.app

import android.app.Application
import com.brewdog.android.app.di.dataModule
import com.brewdog.android.app.di.repositoriesModule
import com.brewdog.android.app.di.viewModelsModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import org.koin.core.module.Module

class App: Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@App)
            modules(appModules())
        }
    }

    private fun appModules() = listOf<Module>(
        dataModule(),
        viewModelsModule(),
        repositoriesModule()
    )
}