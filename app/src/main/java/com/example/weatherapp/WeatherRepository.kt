package com.example.weatherapp


import com.example.weatherapp.model.CityGeo
import com.example.weatherapp.model.Geometry
import com.example.weatherapp.wheather.CityWeather
import com.example.weatherapp.wheather.Daily
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.lang.Exception
import java.text.SimpleDateFormat

//https://api.opencagedata.com/geocode/v1/json?q=minsk&key=67849701dd23440a85ccca03eb079a5b

//const val baseUrl = "https://api.opencagedata.com/geocode/v1/json?key=67849701dd23440a85ccca03eb079a5b&no_annotations=1&add_request=1&limit=1&q=minsk"

class WeatherRepository(
    private val geoApi: GeoApi,
    private val weatherApi: WeatherApi,
    private val simpleDateFormat: SimpleDateFormat = SimpleDateFormat("yyyy.MM.dd")) {

    suspend fun findCityGeo(query: String): Result<CityGeo>{
        return withContext(Dispatchers.IO){
            runCatching {
                geoApi.findCityGeoAsync(query = query)
                    .await()
                    ?.takeIf { it.isSuccessful }
                    ?.body()
                    ?: throw Exception("Empty Data")
            }
        }
    }

    suspend fun findCityWeather(lat: String, lon: String):Result<CityWeather>{
        return withContext(Dispatchers.IO){
            kotlin.runCatching {
                weatherApi.findCityWeather(queryLat = lat, queryLon = lon)
                    .await()
                    ?.takeIf { it.isSuccessful }
                    ?.body()
                    ?: throw Exception("Empty weather")
            }
        }
    }
}