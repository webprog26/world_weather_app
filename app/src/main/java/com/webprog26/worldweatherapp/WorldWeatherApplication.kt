package com.webprog26.worldweatherapp

import android.app.Application
import android.content.Context

class WorldWeatherApplication : Application() {

    companion object {
        lateinit var appContext: Context
    }

    override fun onCreate() {
        super.onCreate()
        appContext = this
    }
}