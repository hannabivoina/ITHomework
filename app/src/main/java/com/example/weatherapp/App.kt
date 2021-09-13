package com.example.weatherapp

import android.app.Application
//import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import okhttp3.OkHttpClient
//import okhttp3.logging.HttpLoggingInterceptor
//import retrofit2.Retrofit

class  App: Application() {

    lateinit var weatherApi: WeatherApi

    override fun onCreate() {
        super.onCreate()
    }

    companion object{

//        private fun getHttpClient():OkHttpClient{
//            val httpLoggingInterceptor = HttpLoggingInterceptor()
//            httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
//
//            val okHttpClient =OkHttpClient
//                .Builder()
//                .addInterceptor(httpLoggingInterceptor)
//                .build()
//        return okHttpClient
//        }
//
//
//        val retrofit = Retrofit
//            .Builder()
//            .baseUrl("https://api.opencagedata.com")
//            .client(getHttpClient())
//            .addConverterFactory(GsonConverterFactory.create())
//            .addCallAdapterFactory(CoroutineCallAdapterFactory())
//            .build()
//
//        val searchWeather = retrofit.create(WeatherApi::class.java)
        val okHttpClient = OkHttpClient()
    }

}