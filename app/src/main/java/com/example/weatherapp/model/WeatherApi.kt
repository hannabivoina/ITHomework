package com.example.weatherapp.model

import com.example.weatherapp.wheather.CityWeather
import kotlinx.coroutines.Deferred
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

private const val API_KEY_WEATHER = "97bbe5743e30f14fda7f4f2894eeb9de"
private const val query_lat = "lat"
private const val query_lon = "lon"
private const val query_exclude = "exclude"
private const val query_appid = "appid"

interface WeatherApi {
    @GET("data/2.5/onecall")
    fun findCityWeather(
        @Query(query_lat) queryLat: String,
        @Query(query_lon) queryLon: String,
        @Query(query_exclude) exclude : String = "hourly,minutely",
        @Query(query_appid) apiKey: String = API_KEY_WEATHER
    ): Deferred<Response<CityWeather>>
}