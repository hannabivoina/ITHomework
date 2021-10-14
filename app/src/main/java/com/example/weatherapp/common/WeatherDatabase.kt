package com.example.weatherapp.common

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.weatherapp.database.SavedForecastDao
import com.example.weatherapp.database.WeatherForecast

@Database(entities = [WeatherForecast::class], version = 1)
abstract class WeatherDatabase: RoomDatabase() {
    abstract fun getSavedForecastDao(): SavedForecastDao
}