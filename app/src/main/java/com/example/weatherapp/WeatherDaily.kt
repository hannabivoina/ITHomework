package com.example.weatherapp

import java.util.*

data class WeatherDaily(
    val id: Int,
    val date: Date,
    val icon: String,
    val degree: String,
    val character: String,
    val city: String,
    val countryCode: String
)
