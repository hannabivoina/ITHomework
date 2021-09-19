package com.example.weatherapp.wheather

data class Current(
    val dew_point: Double,
    val dt: Int,
    val temp: Double,
    val uvi: Double,
    val weather: List<Weather>,
)