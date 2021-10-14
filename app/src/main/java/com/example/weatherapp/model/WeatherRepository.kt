package com.example.weatherapp.model


import com.example.weatherapp.database.SavedForecastDao
import com.example.weatherapp.database.WeatherForecast
import com.example.weatherapp.viewModel.city.CityGeo
import com.example.weatherapp.wheather.CityWeather
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.lang.Exception

class WeatherRepository(
    private val geoApi: GeoApi,
    private val weatherApi: WeatherApi,
    private val savedForecastDao: SavedForecastDao
) {
    private var savedForecastList: List<WeatherForecast>? = null

    suspend fun findCityGeo(query: String): Result<CityGeo> {
        return withContext(Dispatchers.IO) {
            runCatching {
                geoApi.findCityGeoAsync(query = query)
                    .await()
                    ?.takeIf { it.isSuccessful }
                    ?.body()
                    ?: throw Exception("Empty Data")
            }
        }
    }

    suspend fun findCityWeather(lat: String, lon: String): Result<CityWeather> {
        return withContext(Dispatchers.IO) {
            kotlin.runCatching {
                weatherApi.findCityWeather(queryLat = lat, queryLon = lon)
                    .await()
                    ?.takeIf { it.isSuccessful }
                    ?.body()
                    ?: throw Exception("Empty weather")
            }
        }
    }

    suspend fun addWeatherToSavedForecast(weather: WeatherForecast): List<WeatherForecast> =
        withContext(Dispatchers.IO) {
            savedForecastDao.insert(weather)
            addToSavedForecastList(weather)

            return@withContext savedForecastList ?: emptyList()
        }

    private fun addToSavedForecastList(forecast: WeatherForecast) {
        savedForecastList = savedForecastList?.toMutableList()?.let {
            it.add(forecast)
            it
        } ?: arrayListOf(forecast)
    }

    suspend fun getAllSaved(): List<WeatherForecast> {
        if (savedForecastList.isNullOrEmpty()) {
            withContext(Dispatchers.IO) {
                savedForecastList = savedForecastDao.getAll()
            }
        }
        return savedForecastList ?: emptyList()
    }

    suspend fun clearAll() {
        withContext(Dispatchers.IO) {
            savedForecastDao.clearAll()
        }
    }

    suspend fun updateWeatherForecast(
        forecastId: Int,
        newWeather: CityWeather
    ): List<WeatherForecast> {
        withContext(Dispatchers.IO) {
            savedForecastDao.updateWeather(forecastId, newWeather)
            getAllSaved()
        }
        return savedForecastList ?: emptyList()
    }
}