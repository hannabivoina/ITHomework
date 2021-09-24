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
/*
Daily(dt=1632477600, temp=Temp(day=282.4), weather=[WeatherX(description=moderate rain, icon=10d, id=501, main=Rain)]),
 Daily(dt=1632564000, temp=Temp(day=285.46), weather=[WeatherX(description=light rain, icon=10d, id=500, main=Rain)]),
 Daily(dt=1632650400, temp=Temp(day=286.33), weather=[WeatherX(description=few clouds, icon=02d, id=801, main=Clouds)]),
  Daily(dt=1632736800, temp=Temp(day=284.62), weather=[WeatherX(description=clear sky, icon=01d, id=800, main=Clear)]),
   Daily(dt=1632823200, temp=Temp(day=284.42), weather=[WeatherX(description=light rain, icon=10d, id=500, main=Rain)]),
    Daily(dt=1632909600, temp=Temp(day=283.46), weather=[WeatherX(description=light rain, icon=10d, id=500, main=Rain)]),
     Daily(dt=1632992400, temp=Temp(day=285.08), weather=[WeatherX(description=light rain, icon=10d, id=500, main=Rain)]),
Daily(dt=1633078800, temp=Temp(day=283.45), weather=[WeatherX(description=moderate rain, icon=10d, id=501, main=Rain)])
 */