package com.example.weatherapp

import android.app.Application
import okhttp3.OkHttpClient

class App: Application() {
    override fun onCreate() {
        super.onCreate()
    }
    companion object{
        val httpClient = OkHttpClient()
    }
}