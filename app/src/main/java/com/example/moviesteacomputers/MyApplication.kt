package com.example.moviesteacomputers

import android.app.Application
import com.example.moviesteacomputers.di.dataModules
import com.example.moviesteacomputers.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class MyApplication : Application() {


    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@MyApplication)
            androidLogger()
            modules(listOf(dataModules,viewModelModule))
        }
    }
}