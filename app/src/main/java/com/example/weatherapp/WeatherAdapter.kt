package com.example.weatherapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.weatherapp.databinding.FragmentChooseCityBinding
import com.example.weatherapp.databinding.ItemBinding
import com.example.weatherapp.databinding.ItemCityBinding
import com.example.weatherapp.wheather.CityWeather
import com.example.weatherapp.wheather.Daily

private const val imageUrl = "https://openweathermap.org/img/wn/"

class WeatherAdapter(): RecyclerView.Adapter<WeatherAdapter.DailyWeatherViewHolder>(){

    private var weatherDailyList = ArrayList<Daily>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DailyWeatherViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item, parent, false)
        return DailyWeatherViewHolder(view)
    }

    override fun onBindViewHolder(holder: DailyWeatherViewHolder, position: Int) {
        val dailyWeather = weatherDailyList[position]
        holder.bind(dailyWeather)
    }

    override fun getItemCount(): Int = weatherDailyList.size

    class DailyWeatherViewHolder(view: View): RecyclerView.ViewHolder(view){
        val binding = ItemBinding.bind(view)

        private fun getImageUrl(icon: String): String{
            val url = imageUrl + icon + "@2x.png"
            return url
        }

        fun bind(weatherDaily: Daily) = with(binding){
            itemDate.text = weatherDaily.dt.toString()
            itemDegree.text = weatherDaily.temp.day.toString()
            itemType.text = weatherDaily.weather[0].main

            Glide
                .with(itemView)
                .load(getImageUrl(weatherDaily.weather[0].icon))
                .centerCrop()
                .into(itemImage)
        }
    }
}