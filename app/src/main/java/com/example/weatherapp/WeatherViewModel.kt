package com.example.weatherapp

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weatherapp.model.Result
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class WeatherViewModel : ViewModel(){

    private val weatherRepository = WeatherRepository(App.searchGeo)
    private val _findCityLiveData = MutableLiveData<List<Result>>()
    val findCityLiveData : LiveData<List<Result>>
        get() = _findCityLiveData

    private val _errorCityLiveData = MutableLiveData<String>()
    val errorCityLiveData : LiveData<String>
        get() = _errorCityLiveData

    private var searchJob: Job? = null

    val exeptionHandler = CoroutineExceptionHandler { _, t ->
        _errorCityLiveData.postValue(t.toString())
    }

    override fun onCleared() {
        super.onCleared()
        searchJob = null
    }

    fun findCity(text: CharSequence){
        searchJob?.cancel()
        searchJob = viewModelScope.launch(exeptionHandler) {
            delay(1000)
            val cityResponse = weatherRepository.findCityGeo(text.toString())
            if (cityResponse.isSuccess){
                cityResponse.getOrNull()?.let {
                    _findCityLiveData.postValue(it)
                    println("----------------${_findCityLiveData.value}")
                } ?: run{
                    cityResponse.exceptionOrNull()?.message ?: "UnExpected Expression"
            }}
        }
    }

}