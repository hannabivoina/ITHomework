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

class WeatherViewModel : ViewModel() {

    private val weatherRepository = WeatherRepository(App.searchGeo, App.searchWeather)

//    private var citiesList = ArrayList<String>()

    private val _weatherForecastLiveData = MutableLiveData<WeatherForecast>()
    val weatherForecastLiveData: LiveData<WeatherForecast>
        get() = _weatherForecastLiveData

    private val _citiesListLiveData = MutableLiveData<ArrayList<String>>()
    val citiesListLiveData: LiveData<ArrayList<String>>
        get() = _citiesListLiveData

    private val _findCityLiveData = MutableLiveData<CityGeo>()
    val findCityLiveData: LiveData<CityGeo>
        get() = _findCityLiveData
    private val _errorCityLiveData = MutableLiveData<String>()
    val errorCityLiveData: LiveData<String>
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

    fun findCity(text: CharSequence) {
        searchJob?.cancel()
        searchJob = viewModelScope.launch(exeptionHandlerGeo) {
            val cityResponse = weatherRepository.findCityGeo(text.toString())
            if (cityResponse.isSuccess) {
                cityResponse.getOrNull()?.let {
//                    citiesList.add(updateCitiesList(it))
//                   _citiesListLiveData.postValue(updateCitiesList(it))
                    _findCityLiveData.postValue(it)
                } ?: run {
                    cityResponse.exceptionOrNull()?.message ?: "UnExpected Expression"
                }
            }
        }
    }

    fun findWeather(geo: CityGeo) {
        searchWeatherJob?.cancel()
        searchWeatherJob = viewModelScope.launch(exeptionHandlerWeather) {
            val weatherResponse = weatherRepository.findCityWeather(
                geo.results[0].geometry.lat.toString(),
                geo.results[0].geometry.lng.toString()
            )
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

    fun getForecast(city: CityGeo?, weather: CityWeather?) {
        if (city != null && weather != null) {
            val weatherForecast = WeatherForecast(
                cityName = createCityName(city),
                geoLat = city.results[0].geometry.lat,
                geoLng = city.results[0].geometry.lng,
                weather = weather
            )
            _weatherForecastLiveData.postValue(weatherForecast)
            _citiesListLiveData.postValue(updateList(weatherForecast.cityName))
        }
    }

    fun updateList(city: String): ArrayList<String> {
        val newList = ArrayList<String>()
        newList.add(city)
        if (citiesListLiveData.value.isNullOrEmpty()) {
            return newList
        } else {
            newList.addAll(citiesListLiveData.value!!)
        }
        return newList
    }
}