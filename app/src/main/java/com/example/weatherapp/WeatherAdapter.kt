package com.example.weatherapp

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.weatherapp.databinding.FragmentChooseCityBinding
import com.example.weatherapp.databinding.ItemBinding

class WeatherAdapter(): RecyclerView.Adapter<WeatherAdapter.DailyWeatherViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DailyWeatherViewHolder {
        TODO("Not yet implemented")
    }

    override fun onBindViewHolder(holder: DailyWeatherViewHolder, position: Int) {
        TODO("Not yet implemented")
    }

    override fun getItemCount(): Int {
        TODO("Not yet implemented")
    }
    class DailyWeatherViewHolder(binding: ItemBinding) :
        RecyclerView.ViewHolder(binding.root)
}