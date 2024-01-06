package com.example.mad_android

import android.app.Application
import com.example.mad_android.data.AppContainer
import com.example.mad_android.data.DefaultAppContainer

class TrainApplication : Application() {
    lateinit var container: AppContainer

    override fun onCreate() {
        super.onCreate()
        container = DefaultAppContainer(context = applicationContext)
    }
}