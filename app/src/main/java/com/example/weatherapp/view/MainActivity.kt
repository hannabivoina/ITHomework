package com.example.weatherapp.view

import android.app.TaskStackBuilder.create
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.os.bundleOf
import com.example.weatherapp.R
import com.example.weatherapp.common.AppContract
import com.example.weatherapp.wheather.Daily
import java.text.SimpleDateFormat
import java.util.*

private const val imageUrl = "https://openweathermap.org/img/wn/"

class MainActivity : AppCompatActivity(), AppContract {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if(savedInstanceState == null){
            supportFragmentManager
                .beginTransaction()
                .replace(R.id.mainActivityLay, MainFragment())
                .commit()
        }
    }

    override fun changeCity() {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.mainActivityLay, ChooseCityFragment())
            .addToBackStack(null)
            .commit()
    }

    override fun weatherForecast() {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.mainActivityLay, MainFragment())
            .addToBackStack(null)
            .commit()
    }

    override fun dateFormat(intDate: Int): String {
        val simpleDateFormat: SimpleDateFormat = SimpleDateFormat("dd.MM.yyyy")
        val date = Date(intDate.toLong() * 1000)
        val newDate = simpleDateFormat.format(date)
        return newDate
    }

    override fun getImageUrl(icon: String): String{
        val url = imageUrl + icon + "@2x.png"
        return url
    }

    override fun isNetworkAvailable(context: Context): Boolean {
        val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        var activeNetworkInfo: NetworkInfo? = null
        activeNetworkInfo = cm.activeNetworkInfo
        return activeNetworkInfo != null && activeNetworkInfo.isConnectedOrConnecting
    }
}