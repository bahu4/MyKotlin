package com.example.mykotlin

import android.app.Application
import com.example.mykotlin.data.di.appModule
import com.example.mykotlin.data.di.loginModule
import com.example.mykotlin.data.di.mainModule
import com.example.mykotlin.data.di.secondModule
import org.koin.android.ext.android.startKoin

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin(this, listOf(appModule, loginModule, secondModule, mainModule))
    }
}