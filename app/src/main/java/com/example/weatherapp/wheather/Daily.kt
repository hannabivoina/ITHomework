package com.example.weatherapp.wheather

data class Daily(
    val dt: Int,
    val temp: Temp,
    val weather: List<WeatherX>
)