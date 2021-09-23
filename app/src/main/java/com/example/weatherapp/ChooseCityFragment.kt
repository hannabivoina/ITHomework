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
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.weatherapp.databinding.FragmentChooseCityBinding
import kotlinx.android.synthetic.main.fragment_choose_city.*

class ChooseCityFragment: Fragment(R.layout.fragment_choose_city) {
    private lateinit var binding: FragmentChooseCityBinding
    private val viewModel = viewModels<WeatherViewModel>()
    private val adapter = CityAdapter()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentChooseCityBinding.inflate(inflater, container, false)

        viewModel.value.findCityLiveData.observe(viewLifecycleOwner){
            if (it.results.isEmpty()){
                Toast.makeText(requireContext(),"такого города нет", Toast.LENGTH_LONG).show()
            }
            else {
                var geometry = ".results${it.results[0].geometry.lat}, ${it.results[0].geometry.lng}"
                Toast.makeText(requireContext(), geometry, Toast.LENGTH_LONG).show()
                viewModel.value.findWeather(it)
            }
        }

        viewModel.value.errorCityLiveData.observe(viewLifecycleOwner){
            Toast.makeText(requireContext(), "ошибка"+it, Toast.LENGTH_LONG).show()
            println("-------ошибка------------------------$it")
        }


        viewModel.value.findWeatherLiveData.observe(viewLifecycleOwner){
            if (it.daily.isEmpty()){
                Toast.makeText(requireContext(),"такой погоды нет", Toast.LENGTH_LONG).show()
            }
            else {
                println("--------------3--------------${it.daily.toString()}")
                Toast.makeText(requireContext(), it.toString(), Toast.LENGTH_LONG).show()
            }
        }

        binding.buttonAddCity.setOnClickListener {
//            adapter.addCity("hello")
            var addCity = EditText(requireContext())
            addCity.hint = "City name"
            var builder = AlertDialog
                .Builder(requireContext())
                .setMessage("Please put the city name")
                .setView(addCity)
                .setPositiveButton("ok"){dialog, which -> viewModel.value.findCity(addCity.text)}
                .setNegativeButton("cancel"){dialog, which -> dialog.cancel()}
            builder.show()
        }


        setupRecyclerView()
//        val layoutManager = LinearLayoutManager(requireContext())
//        binding.citiesListRecycler.layoutManager = layoutManager
//        binding.citiesListRecycler.adapter = adapter

        viewModel.value.citiesListLiveData.observe(viewLifecycleOwner){
            adapter.addCity(it)
        }

        binding.buttonBack.setOnClickListener {
            Toast.makeText(requireContext(), viewModel.value.findWeatherLiveData.value.toString(), Toast.LENGTH_LONG).show()
//            contract().weatherForecast()
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