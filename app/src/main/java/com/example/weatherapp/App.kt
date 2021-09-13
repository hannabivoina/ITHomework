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
        private val retrofit = Retrofit
            .Builder()
            .baseUrl("https://api.opencagedata.com")
//            .client(getHttpClient())
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .build()

        val searchGeo = retrofit.create(GeoApi::class.java)
//        val okHttpClient = OkHttpClient()
    }

}