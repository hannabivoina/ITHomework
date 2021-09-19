package com.example.weatherapp

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weatherapp.model.CityGeo
import com.example.weatherapp.model.Geometry
import com.example.weatherapp.model.Result
import com.example.weatherapp.wheather.CityWeather
import com.example.weatherapp.wheather.Daily
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class WeatherViewModel : ViewModel(){

    private val weatherRepository = WeatherRepository(App.searchGeo, App.searchWeather)

    private val _findCityLiveData = MutableLiveData<CityGeo>()
    val findCityLiveData : LiveData<CityGeo>
        get() = _findCityLiveData
    private val _errorCityLiveData = MutableLiveData<String>()
    val errorCityLiveData : LiveData<String>
        get() = _errorCityLiveData

    private val _findWeatherLiveData = MutableLiveData<CityWeather>()
    val findWeatherLiveData: LiveData<CityWeather>
        get() = _findWeatherLiveData
    private val _errorWeatherLiveData = MutableLiveData<String>()
    val errorWeatherLiveData: LiveData<String>
        get() = _errorWeatherLiveData


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

    fun findCity(text: CharSequence){
        searchJob?.cancel()
        searchJob = viewModelScope.launch(exeptionHandlerGeo) {
//            delay(1000)
            val cityResponse = weatherRepository.findCityGeo(text.toString())
            if (cityResponse.isSuccess){
                cityResponse.getOrNull()?.let {
                    _findCityLiveData.postValue(it)
                    println("------1----------${_findCityLiveData.value}")
                } ?: run{
                    cityResponse.exceptionOrNull()?.message ?: "UnExpected Expression"
            }
            }
        }
//        findCityLiveData.observeForever {
//            if (it.results.isNotEmpty()){
//                findWeather(it)
//            }
//        }
    }

    fun findWeather(geo : CityGeo){
        searchWeatherJob?.cancel()
        searchWeatherJob = viewModelScope.launch(exeptionHandlerWeather) {
            val weatherResponse = weatherRepository.findCityWeather(geo.results[0].geometry.lat.toString(), geo.results[0].geometry.lng.toString())
            if (weatherResponse.isSuccess){
                weatherResponse.getOrNull()?.let {
                    _findWeatherLiveData.postValue(it)
                }?: kotlin.run {
                    weatherResponse.exceptionOrNull()?.message ?: "UnExpected Expression"
                }
            }
        }
    }

}