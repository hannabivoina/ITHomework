package com.example.weatherapp

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.Toast
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.weatherapp.databinding.FragmentChooseCityBinding
import kotlinx.android.synthetic.main.fragment_choose_city.*
import kotlinx.android.synthetic.main.toolbar_choose_city.view.*

class ChooseCityFragment : Fragment(R.layout.fragment_choose_city) {
    private lateinit var binding: FragmentChooseCityBinding
    private val viewModel = activityViewModels<WeatherViewModel>()
    private val adapter = CityAdapter(object : CityInterface {
        override fun changeCurrent(id: Int) {
            viewModel.value.updateCurrentForecast(id)
            if (contract().isNetworkAvailable(requireContext())) {
                viewModel.value.searchWeather(
                    "update",
                    viewModel.value.getCurrentForecast()!!.geoLat.toString(),
                    viewModel.value.getCurrentForecast()!!.geoLng.toString()
                )
            }
        }
    })

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentChooseCityBinding.inflate(inflater, container, false)

        adapter.updateList(viewModel.value.getForecastList())

        viewModel.value.errorCityLiveData.observe(viewLifecycleOwner) {
            if (it != null) {
                Toast.makeText(requireContext(), "ошибка" + it, Toast.LENGTH_LONG).show()
            }
        }

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
                Toast.makeText(requireContext(), "погоды для такого города нет", Toast.LENGTH_LONG)
                    .show()
            } else {
                viewModel.value.createForecast(viewModel.value.findCityLiveData.value, weather)
                adapter.updateList(viewModel.value.getForecastList())
            }
            viewModel.value.getForecastList()
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
//                viewModel.value.updateCurrentForecast(id)
                adapter.updateList(viewModel.value.getForecastList())
                Toast.makeText(requireContext(), "Updated", Toast.LENGTH_LONG).show()
            }
            viewModel.value.setNullLiveData()
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
