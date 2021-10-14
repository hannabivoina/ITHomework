package com.example.weatherapp.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.weatherapp.R
import com.example.weatherapp.common.contract
import com.example.weatherapp.viewModel.WeatherViewModel
import com.example.weatherapp.database.WeatherForecast
import com.example.weatherapp.databinding.FragmentMainBinding
import com.example.weatherapp.view.adapter.WeatherAdapter
import com.example.weatherapp.wheather.*
import kotlin.collections.ArrayList


class MainFragment : Fragment(R.layout.fragment_main) {
    private val viewModel = activityViewModels<WeatherViewModel>()
    lateinit private var adapter: WeatherAdapter
    lateinit private var binding: FragmentMainBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMainBinding.inflate(inflater, container, false)
        if(savedInstanceState == null){
            viewModel.value.getSavedForecast()
        }

        binding.buttonAdd.setOnClickListener {
            contract().changeCity()
        }

        val weatherForecast = viewModel.value.getCurrentForecast()

        adapter = WeatherAdapter(dailyWeatherForecast(weatherForecast?.weather?.daily))
        currentWeatherForecast(weatherForecast?.weather?.current)
        cityWeatherForecast(weatherForecast)

        setupRecyclerView()
        return binding.root
    }

    private fun setupRecyclerView() {
        binding.apply {
            recyclerViewMain.layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            recyclerViewMain.adapter = adapter

        }
    }

    private fun dailyWeatherForecast(dailyList: List<Daily>?): ArrayList<Daily> {
        val weatherForecast = ArrayList<Daily>()
        if (dailyList.isNullOrEmpty()) {
            Toast.makeText(requireContext(), "empty", Toast.LENGTH_SHORT)
        } else {
            for (i in 1..4) {
                weatherForecast.add(dailyList[i])
            }
        }
        return weatherForecast
    }

    private fun currentWeatherForecast(currentWeather: Current?) {
        if (currentWeather == null){
        }
        else{
            binding.apply {
                mainDate.text = contract().dateFormat(currentWeather.dt)
                mainDegree.text = "${(currentWeather.temp.toInt() - 273).toInt()}°"
                mainType.text = currentWeather.weather[0].main

                Glide
                    .with(requireContext())
                    .load(contract().getImageUrl(currentWeather.weather[0].icon))
                    .centerCrop()
                    .into(mainImage)
            }
        }
    }

    private fun cityWeatherForecast(weatherForecast: WeatherForecast?){
        if (weatherForecast == null){}
        else{
            binding.mainCity.text = weatherForecast.cityName
        }
    }
}