package com.example.weatherapp.view

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.weatherapp.CityAdapter
import com.example.weatherapp.CityInterface
import com.example.weatherapp.R
import com.example.weatherapp.viewModel.WeatherViewModel
import com.example.weatherapp.common.contract
import com.example.weatherapp.databinding.FragmentChooseCityBinding

class ChooseCityFragment : Fragment(R.layout.fragment_choose_city) {
    private lateinit var binding: FragmentChooseCityBinding
    private val viewModel = activityViewModels<WeatherViewModel>()
    private val adapter = CityAdapter(object : CityInterface {
        override fun changeCurrent(id: Int) {
            if (contract().isNetworkAvailable(requireContext())) {
                viewModel.value.searchWeather(
                    "update",
                    viewModel.value.getForecastList()[id].geoLat.toString(),
                    viewModel.value.getForecastList()[id].geoLng.toString()
                )
            }
            viewModel.value.updateCurrentForecast(id)
        }
    })

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentChooseCityBinding.inflate(inflater, container, false)

        adapter.updateList(viewModel.value.getForecastList())

        viewModel.value.findCityLiveData.observe(viewLifecycleOwner) { city ->
            if (city != null) {
                if (city.results.isEmpty()) {
                    Toast.makeText(requireContext(), "такого города нет", Toast.LENGTH_LONG).show()
                    viewModel.value.setNullLiveData()
                } else {
                    viewModel.value.searchWeather(
                        "create",
                        city.results[0].geometry.lat.toString(),
                        city.results[0].geometry.lng.toString()
                    )
                }
            }
        }

        viewModel.value.createWeatherLiveData.observe(viewLifecycleOwner) { weather ->
            if (weather != null) {
                if (weather?.daily.isNullOrEmpty()) {
                    Toast.makeText(
                        requireContext(),
                        "погоды для такого города нет",
                        Toast.LENGTH_LONG
                    ).show()
                } else {
                    viewModel.value.createForecast(viewModel.value.findCityLiveData.value, weather)
                    adapter.updateList(viewModel.value.getForecastList())
                }
                viewModel.value.setNullLiveData()
            }
        }

        viewModel.value.updateWeatherLiveData.observe(viewLifecycleOwner) { weather ->
            if (weather != null) {
                if (weather?.daily.isNullOrEmpty()) {
                    Toast.makeText(requireContext(), "новой погоды нет", Toast.LENGTH_LONG).show()
                } else {
                    viewModel.value.updateForecast(
                        viewModel.value.getCurrentForecast()!!.id,
                        weather
                    )
                }
                adapter.updateList(viewModel.value.getForecastList())
                viewModel.value.setNullLiveData()
            }
        }

        viewModel.value.errorCityLiveData.observe(viewLifecycleOwner) {
            if (it != null) {
                Toast.makeText(requireContext(), "ошибка" + it, Toast.LENGTH_LONG).show()
            }
        }
        viewModel.value.errorWeatherLiveData.observe(viewLifecycleOwner) {
            if (it != null) {
                Toast.makeText(requireContext(), "ошибка" + it, Toast.LENGTH_LONG).show()
            }
        }

        binding.buttonAddCity.setOnClickListener {
            var addCity = EditText(requireContext())
            addCity.hint = "City name"
            var builder = AlertDialog
                .Builder(requireContext())
                .setMessage("Please put the city name")
                .setView(addCity)
                .setPositiveButton("ok") { dialog, which -> startSearch(addCity.text.toString()) }
                .setNegativeButton("cancel") { dialog, which -> dialog.cancel() }
            builder.show()
        }

        binding.toolbarChooseCity.toolbarButtonBack.setOnClickListener {
            contract().weatherForecast()
//            viewModel.value.clearAll()
        }

        setupRecyclerView()

        return binding.root
    }

    private fun setupRecyclerView() {
        binding.apply {
            citiesListRecycler.layoutManager = LinearLayoutManager(requireContext())
            citiesListRecycler.adapter = adapter
        }
    }

    private fun startSearch(query: String) {
        if (contract().isNetworkAvailable(requireContext())) {
            viewModel.value.searchCity(query)
        } else {
            Toast.makeText(requireContext(), "No internet connection", Toast.LENGTH_LONG).show()
        }
    }
}
