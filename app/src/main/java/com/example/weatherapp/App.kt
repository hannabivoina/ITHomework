package com.example.weatherapp

import android.app.Application
import android.content.Context
import androidx.room.Room
import com.example.weatherapp.database.WeatherDatabase
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
//import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class  App: Application() {

    lateinit var geoApi: GeoApi
    lateinit var weatherApi: WeatherApi

    override fun onCreate() {
        super.onCreate()

        weatherDatabase = Room.databaseBuilder(this, WeatherDatabase::class.java, WEATHER_DATABASE).build()
    }

    companion object{

        private const val WEATHER_DATABASE = "WEATHER_DATABASE"

        private lateinit var weatherDatabase: WeatherDatabase

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

        fun getSavedForecastDao() = weatherDatabase.getSavedForecastDao()
    }

}