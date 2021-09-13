package com.example.weatherapp

import com.example.weatherapp.wheather.CityWheather
import kotlinx.coroutines.Deferred
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

//https://api.openweathermap.org/data/2.5/onecall?lat=53.9024716&lon=27.5618225&exclude=hourly,minutely&appid=97bbe5743e30f14fda7f4f2894eeb9de

private const val API_KEY_WEATHER = "97bbe5743e30f14fda7f4f2894eeb9de"
private const val query_lat = "lat"
private const val query_lon = "lon"
private const val query_exclude = "exclude"


interface WeatherApi {
    @GET("data/2.5/onecall")
    fun findCityWeather(
        @Query(query_lat) queryLat: String,
        @Query(query_lon) queryLon: String,
        @Query(query_exclude) exclude : String = "hourly,minutely",
        @Query("appid") apiKet: String = API_KEY_WEATHER
    ): Deferred<Response<CityWheather>>
}