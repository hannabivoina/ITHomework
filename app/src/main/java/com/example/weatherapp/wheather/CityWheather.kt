package com.example.weatherapp.wheather

data class CityWeather(
    val current: Current,
    val daily: List<Daily>,
)