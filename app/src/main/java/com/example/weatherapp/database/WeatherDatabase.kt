package com.example.weatherapp.database

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [WeatherForecast::class], version = 1)
abstract class WeatherDatabase: RoomDatabase() {
    abstract fun getSavedForecastDao(): SavedForecastDao
}