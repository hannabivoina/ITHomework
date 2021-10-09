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
    private val viewModel = activityViewModels<WeatherViewModel>()
    private val adapter = CityAdapter()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentChooseCityBinding.inflate(inflater, container, false)

        setupRecyclerView()
        adapter.updateList(viewModel.value.getForecastList())

        println("------------")
        println(viewModel.value.getForecastList().size)


//        viewModel.value.newLiveData.observe(viewLifecycleOwner){
//            if (it != null){
//                Toast.makeText(requireContext(), it.toString(), Toast.LENGTH_LONG).show()}
//            viewModel.value.getNew(false)
//        }

        viewModel.value.errorCityLiveData.observe(viewLifecycleOwner) {
            if (it != null) {
                Toast.makeText(requireContext(), "ошибка" + it, Toast.LENGTH_LONG).show()
            }
        }

        viewModel.value.findCityLiveData.observe(viewLifecycleOwner) { city ->
            println("-----------------1=3")
            if (city != null) {
                if (city.results.isEmpty()) {
                    Toast.makeText(requireContext(), "такого города нет", Toast.LENGTH_LONG).show()
                    viewModel.value.setNullLiveData()
                } else {
                    Toast.makeText(requireContext(), "ghbdtn", Toast.LENGTH_LONG).show()
                    viewModel.value.findWeather(city)
                }
            }
        }

        viewModel.value.findWeatherLiveData.observe(viewLifecycleOwner) { weather ->
            println("-----------------2")
            if (weather != null) {
                if (weather.daily.isEmpty()) {
                    Toast.makeText(requireContext(), "такой погоды нет", Toast.LENGTH_LONG).show()
                    viewModel.value.setNullLiveData()
                } else {
                    viewModel.value.getNewForecast(viewModel.value.findCityLiveData.value, weather)
                    adapter.updateList(viewModel.value.getForecastList())
                    println(viewModel.value.getForecastList().size)
                    viewModel.value.setNullLiveData()
                }
            }
        }

//        viewModel.value.weatherForecastLiveData.observe(viewLifecycleOwner) {
//            println("-----------------1")
//
//            adapter.addCity(it.cityName)
//
//            adapter.updateList(viewModel.updateList(it.cityName))
//        }

        binding.buttonAddCity.setOnClickListener {
            var addCity = EditText(requireContext())
            addCity.hint = "City name"
            var builder = AlertDialog
                .Builder(requireContext())
                .setMessage("Please put the city name")
                .setView(addCity)
                .setPositiveButton("ok") { dialog, which -> viewModel.value.findCity(addCity.text) }
                .setNegativeButton("cancel") { dialog, which -> dialog.cancel() }
            builder.show()
        }



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
