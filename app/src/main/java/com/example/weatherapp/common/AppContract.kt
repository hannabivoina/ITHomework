package com.example.weatherapp.common

import android.content.Context
import androidx.fragment.app.Fragment
import com.example.weatherapp.wheather.Daily

fun Fragment.contract() : AppContract = requireActivity() as AppContract

interface AppContract {
    fun changeCity()
    fun weatherForecast()
    fun dateFormat(date: Int): String
    fun getImageUrl(icon: String): String
    fun isNetworkAvailable(context: Context): Boolean
}