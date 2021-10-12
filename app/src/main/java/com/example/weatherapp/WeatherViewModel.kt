package com.example.weatherapp

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weatherapp.database.WeatherForecast
import com.example.weatherapp.model.CityGeo
import com.example.weatherapp.model.Geometry
import com.example.weatherapp.model.Result
import com.example.weatherapp.wheather.CityWeather
import com.example.weatherapp.wheather.Daily
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class WeatherViewModel : ViewModel() {

    private val weatherRepository = WeatherRepository(App.searchGeo, App.searchWeather, App.getSavedForecastDao())

    private var currentForecast: WeatherForecast? = null
    private var forecastList = listOf<WeatherForecast>()

    fun setNullLiveData(){
        _errorCityLiveData.postValue(null)
        _errorWeatherLiveData.postValue(null)
        _findCityLiveData.postValue(null)
        _findWeatherLiveData.postValue(null)
    }

    fun getCurrentForecast() = currentForecast
    fun getForecastList() = forecastList
    private fun addToForecastList(forecast: WeatherForecast){
        forecastList = forecastList.toMutableList().let {
            it.add(forecast)
            it
        }
    }

    private val _newForecastLiveData = MutableLiveData<List<WeatherForecast>>()
    val newForecastLiveData: LiveData<List<WeatherForecast>>
        get() = _newForecastLiveData


    private val _findCityLiveData = MutableLiveData<CityGeo?>()
    val findCityLiveData: LiveData<CityGeo?>
        get() = _findCityLiveData
    private val _errorCityLiveData = MutableLiveData<String?>()
    val errorCityLiveData: LiveData<String?>
        get() = _errorCityLiveData

    private val _findWeatherLiveData = MutableLiveData<CityWeather?>()
    val findWeatherLiveData: LiveData<CityWeather?>
        get() = _findWeatherLiveData
    private val _errorWeatherLiveData = MutableLiveData<String?>()
    val errorWeatherLiveData: LiveData<String?>
        get() = _errorWeatherLiveData

    private val _updateWeatherLiveData = MutableLiveData<CityWeather?>()
    val updateWeatherLiveData: LiveData<CityWeather?>
        get() = _updateWeatherLiveData


    private var searchJob: Job? = null
    private var searchWeatherJob: Job? = null

    val exeptionHandlerGeo = CoroutineExceptionHandler { _, t ->
        _errorCityLiveData.postValue(t.toString())
    }

    val exeptionHandlerWeather = CoroutineExceptionHandler { _, t ->
        _errorWeatherLiveData.postValue(t.toString())
    }

    override fun onCleared() {
        super.onCleared()
        searchJob = null
    }

    fun findCity(text: String) {
        searchJob?.cancel()
        searchJob = viewModelScope.launch(exeptionHandlerGeo) {
            val cityResponse = weatherRepository.findCityGeo(text)
            if (cityResponse.isSuccess) {
                cityResponse.getOrNull()?.let {
                    _findCityLiveData.postValue(it)
                } ?: run {
                    cityResponse.exceptionOrNull()?.message ?: "UnExpected Expression"
                }
            }
        }
    }

    fun findWeather(lat: String, lng: String) {
        searchWeatherJob?.cancel()
        searchWeatherJob = viewModelScope.launch(exeptionHandlerWeather) {
            val weatherResponse = weatherRepository.findCityWeather(lat, lng)
            if (weatherResponse.isSuccess) {
                weatherResponse.getOrNull()?.let {
                    _findWeatherLiveData.postValue(it)
                } ?: run {
                    weatherResponse.exceptionOrNull()?.message ?: "UnExpected Expression"
                }
            }
        }
    }

    private fun createCityName(city: CityGeo): String {
        val name = city.results[0].components.city
        val code = city.results[0].components.countryCode
        val cityInfo = name + "," + code
        return cityInfo
    }

    fun getForecast(city: CityGeo?, weather: CityWeather?){
        if (city != null && weather != null) {
            val newForecast = WeatherForecast(
                id = if (forecastList.isEmpty()) 0 else forecastList.size,
                currentStatus = false,
                cityName = createCityName(city),
                geoLat = city.results[0].geometry.lat,
                geoLng = city.results[0].geometry.lng,
                weather = weather
            )
            addToSavedForecast(newForecast)
            addToForecastList(newForecast)
        }
    }

    fun updateCurrentForecast(newCurrentWeatherId: Int){
        for(i in forecastList){
            i.currentStatus = i.id == newCurrentWeatherId
        }
        currentForecast = forecastList[newCurrentWeatherId]
    }

    fun getSavedForecast(){
        viewModelScope.launch {
            forecastList = weatherRepository.getAllSaved()
        }
    }

    private fun addToSavedForecast(weatherForecast: WeatherForecast){
        viewModelScope.launch {
            weatherRepository.addWeatherToSavedForecast(weatherForecast)
        }
    }

    fun clearAll(){
        viewModelScope.launch {
            weatherRepository.clearAll()
        }
    }
}