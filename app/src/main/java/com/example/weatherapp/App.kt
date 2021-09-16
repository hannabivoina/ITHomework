package com.example.weatherapp

import android.app.Application
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
//import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

//import okhttp3.logging.HttpLoggingInterceptor
//import retrofit2.Retrofit

class  App: Application() {

    lateinit var geoApi: GeoApi
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
        private val retrofitGeo = Retrofit
            .Builder()
            .baseUrl("https://api.opencagedata.com")
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .build()

        val searchGeo = retrofitGeo.create(GeoApi::class.java)

        private val retrofitWeather = Retrofit
            .Builder()
            .baseUrl("https://api.openweathermap.org/")
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .build()
        val searchWeather = retrofitWeather.create(WeatherApi::class.java)

    }

}