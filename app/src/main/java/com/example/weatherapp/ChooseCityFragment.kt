package com.example.weatherapp

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.weatherapp.databinding.FragmentChooseCityBinding

class ChooseCityFragment: Fragment(R.layout.fragment_choose_city) {
    lateinit var binding: FragmentChooseCityBinding
    private val viewModel = viewModels<WeatherViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentChooseCityBinding.inflate(inflater, container, false)

        viewModel.value.findCityLiveData.observe(viewLifecycleOwner){
            println("---------------2----------------$it")
//            it.reduce { acc, any -> acc.toString() + any.toString() }.let {data->
            if (it.results.isEmpty()){
                Toast.makeText(requireContext(),"такого города нет", Toast.LENGTH_LONG).show()
            }
            else {
                var geometry = ".results${it.results[0].geometry.lat}, ${it.results[0].geometry.lng}"
                Toast.makeText(requireContext(), geometry, Toast.LENGTH_LONG).show()
                viewModel.value.findWeather(it)
            }
//            }
            //toRecyclerView
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

        return binding.root

    }
}