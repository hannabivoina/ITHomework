package com.example.weatherapp

import android.app.TaskStackBuilder.create
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

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
            .replace(R.id.mainActivityLay, MainFragment())
            .addToBackStack(null)
            .commit()
    }

    override fun weatherForecast() {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.mainActivityLay, ChooseCityFragment())
            .addToBackStack(null)
            .commit()
    }
}