package com.example.weatherapp.wheather

data class CityWeather(
    val timezone: String,
    val current: Current,
    val daily: List<Daily>,
)