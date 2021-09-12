package com.example.weatherapp.model

data class Result(
    val bounds: Bounds,
    val components: Components,
    val confidence: Int,
    val formatted: String,
    val geometry: Geometry
)