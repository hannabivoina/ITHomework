package com.example.weatherapp

import android.app.TaskStackBuilder.create
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.os.bundleOf
import com.example.weatherapp.wheather.Daily

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

    override fun weatherForecast(weatherDaily: List<Daily>) {
        val bundle: Bundle = bundleOf(Pair("weather", weatherDaily))
        val fragment = MainFragment().apply {
            arguments = bundle
        }
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.mainActivityLay, fragment)
            .addToBackStack(null)
            .commit()
    }
}