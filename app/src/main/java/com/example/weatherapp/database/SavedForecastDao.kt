package com.example.weatherapp.database

import androidx.room.*
import com.example.weatherapp.wheather.CityWeather

@TypeConverters(WeatherConverter::class)
@Dao
interface SavedForecastDao{
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(weatherForecastList: WeatherForecast)

    @Query("SELECT * FROM SavedForecast")
    fun getAll(): List<WeatherForecast>

    @Query("DELETE FROM SavedForecast")
    fun clearAll()

    @Query("UPDATE SavedForecast SET weather = :newWeather WHERE id = :forecastId")
    fun updateWeather(forecastId: Int, newWeather: CityWeather)
}