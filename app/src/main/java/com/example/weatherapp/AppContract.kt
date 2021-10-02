package com.example.weatherapp

import androidx.fragment.app.Fragment
import com.example.weatherapp.wheather.Daily

fun Fragment.contract() : AppContract = requireActivity() as AppContract

interface AppContract {
    fun changeCity()
    fun weatherForecast()
    fun dateFormat(date: Int): String
    fun getImageUrl(icon: String): String
}