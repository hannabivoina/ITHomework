package com.example.weatherapp

import com.example.weatherapp.model.Result
import io.reactivex.Single
import kotlinx.coroutines.Deferred
import retrofit2.Response
import retrofit2.http.*

private const val API_KEY = "67849701dd23440a85ccca03eb079a5b"
//"https://api.opencagedata.com/
interface WeatherApi {
    @GET("geocode/v1/json")
    fun findCityGeo(
        @Query("q") query: String,
        @Query("no_annotations") ann: String = "1",
        @Query("add_request") request: String = "1",
        @Query("limit") limit: String = "1",
        @Query("key") apiKey: String = API_KEY
    ): Response<List<Result>>
}