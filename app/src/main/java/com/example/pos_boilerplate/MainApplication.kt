package com.example.pos_boilerplate

import android.app.Application
import com.example.pos_boilerplate.core.cache.di.cacheModule
import com.example.pos_boilerplate.core.data.di.dataModule
import com.example.pos_boilerplate.di.appModule
import com.example.pos_boilerplate.utils.DebugTree
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import timber.log.Timber

class MainApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        initLogger()

        startKoin {
            androidLogger()
            androidContext(this@MainApplication)
            modules(appModule, cacheModule, dataModule)
        }
    }

    private fun initLogger() {
        if (BuildConfig.DEBUG) Timber.plant(DebugTree())
    }
}