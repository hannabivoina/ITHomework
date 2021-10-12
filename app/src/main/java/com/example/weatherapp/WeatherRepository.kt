package com.example.weatherapp


import com.example.weatherapp.database.SavedForecastDao
import com.example.weatherapp.database.WeatherForecast
import com.example.weatherapp.model.CityGeo
import com.example.weatherapp.model.Geometry
import com.example.weatherapp.wheather.CityWeather
import com.example.weatherapp.wheather.Daily
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.lang.Exception
import java.text.SimpleDateFormat

class WeatherRepository(
    private val geoApi: GeoApi,
    private val weatherApi: WeatherApi,
    private val savedForecastDao: SavedForecastDao) {

    private var savedForecastList: List<WeatherForecast>? = null

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

    suspend fun addWeatherToSavedForecast(weather: WeatherForecast):List<WeatherForecast>  =
        withContext(Dispatchers.IO) {
            savedForecastDao.insert(weather)
            addToSavedForecastList(weather)

            return@withContext savedForecastList ?: emptyList()
    }

    private fun addToSavedForecastList(forecast: WeatherForecast){
        savedForecastList = savedForecastList?.toMutableList()?.let {
            it.add(forecast)
            it
        } ?: arrayListOf(forecast)
    }

    suspend fun getAllSaved(): List<WeatherForecast>{
        if(savedForecastList.isNullOrEmpty()) {
            withContext(Dispatchers.IO) {
                savedForecastList = savedForecastDao.getAll()
                println("----------------------saved")
            }
        }

        return savedForecastList?: emptyList()
    }

    suspend fun clearAll(){
        withContext(Dispatchers.IO){
            savedForecastDao.clearAll()
        }
    }

    suspend fun updateWeatherForecast(newWeather: CityWeather, forecastId: Int): List<WeatherForecast>{
        withContext(Dispatchers.IO){
            savedForecastDao.updateWeather(newWeather,forecastId)
        }
        savedForecastList = getAllSaved()
        return savedForecastList?: emptyList()
    }
}