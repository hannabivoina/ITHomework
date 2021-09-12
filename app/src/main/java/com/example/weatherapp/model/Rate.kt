package com.example.weatherapp.model

data class Rate(
    val limit: Int,
    val remaining: Int,
    val reset: Int
)