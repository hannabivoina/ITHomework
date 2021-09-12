package com.example.weatherapp.model

data class CityGeo(
    val documentation: String,
    val licenses: List<License>,
    val rate: Rate,
    val request: Request,
    val results: List<Result>,
    val status: Status,
    val stay_informed: StayInformed,
    val thanks: String,
    val timestamp: Timestamp,
    val total_results: Int
)