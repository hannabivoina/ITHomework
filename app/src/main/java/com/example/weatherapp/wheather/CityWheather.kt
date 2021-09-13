package com.example.weatherapp.wheather

data class CityWheather(
    val current: Current,
    val daily: List<Daily>,
    val lat: Double,
    val lon: Double,
    val timezone: String,
    val timezone_offset: Int
)