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

class ChooseCityFragment : Fragment(R.layout.fragment_choose_city) {
    private lateinit var binding: FragmentChooseCityBinding
    private val viewModel: WeatherViewModel by activityViewModels()
    private val adapter = CityAdapter()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentChooseCityBinding.inflate(inflater, container, false)

        viewModel.errorCityLiveData.observe(viewLifecycleOwner) {
            Toast.makeText(requireContext(), "ошибка" + it, Toast.LENGTH_LONG).show()
        }

        viewModel.findCityLiveData.observe(viewLifecycleOwner) { city ->
            println("-----------------1=3")
            if (city.results.isEmpty()) {
                Toast.makeText(requireContext(), "такого города нет", Toast.LENGTH_LONG).show()
            } else {
                Toast.makeText(requireContext(), "ghbdtn", Toast.LENGTH_LONG).show()

//                viewModel.findWeather(city)
            }
        }

        viewModel.findWeatherLiveData.observe(viewLifecycleOwner) { weather ->
            println("-----------------2")

            if (weather.daily.isEmpty()) {
                Toast.makeText(requireContext(), "такой погоды нет", Toast.LENGTH_LONG).show()
            } else {
//                Toast.makeText(requireContext(), weather.toString(), Toast.LENGTH_LONG).show()

                viewModel.getForecast(viewModel.findCityLiveData.value, weather)
            }
        }

        viewModel.weatherForecastLiveData.observe(viewLifecycleOwner) {
            println("-----------------1")
            adapter.addCity(it.cityName)

//            adapter.updateList(viewModel.updateList(it.cityName))
        }

        viewModel.citiesListLiveData.observe(viewLifecycleOwner){

        }

        binding.buttonAddCity.setOnClickListener {
            var addCity = EditText(requireContext())
            addCity.hint = "City name"
            var builder = AlertDialog
                .Builder(requireContext())
                .setMessage("Please put the city name")
                .setView(addCity)
                .setPositiveButton("ok") { dialog, which -> viewModel.findCity(addCity.text) }
                .setNegativeButton("cancel") { dialog, which -> dialog.cancel() }
            builder.show()
        }

        setupRecyclerView()

        binding.buttonBack.setOnClickListener {
            contract().weatherForecast()
        }


        return binding.root
    }

    private fun setupRecyclerView() {
        binding.apply {
            citiesListRecycler.layoutManager = LinearLayoutManager(requireContext())
            citiesListRecycler.adapter = adapter
        }
    }
}
