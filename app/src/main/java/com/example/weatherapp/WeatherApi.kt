package com.example.weatherapp

import io.reactivex.Single
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST

interface WeatherApi {
    @GET
    fun getWeatherList(): Single<>
}