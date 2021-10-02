package com.example.weatherapp

import com.example.weatherapp.wheather.CityWeather

data class WeatherForecast(
    val cityName: String,
    val geoLat: Double,
    val geoLng: Double,
    val weather: CityWeather
)
