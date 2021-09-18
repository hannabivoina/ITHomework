package com.example.weatherapp

import androidx.fragment.app.Fragment

fun Fragment.contract() : AppContract = requireActivity() as AppContract

interface AppContract {
    fun changeCity()
    fun weatherForecast()
}