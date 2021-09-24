package com.example.weatherapp

import android.os.Bundle
import android.service.autofill.Validators.or
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.weatherapp.databinding.FragmentMainBinding
import com.example.weatherapp.wheather.Daily
import com.example.weatherapp.wheather.Temp
import com.example.weatherapp.wheather.WeatherX

class MainFragment : Fragment(R.layout.fragment_main) {
    private val viewModel = viewModels<WeatherViewModel>()
    lateinit private var adapter: WeatherAdapter
    lateinit var binding: FragmentMainBinding

    var list = listOf<Int>(1, 2, 3)

    private val weatherListTest = ArrayList<Daily>()

    private val day: Daily = Daily(
        1632477600,
        Temp(day = 282.4),
        listOf(WeatherX(description = "moderate rain", icon = "10d", id = 501, main = "Rain"))
    )
    private val day2: Daily = Daily(
        1632477600,
        Temp(day = 282.4),
        listOf(WeatherX(description = "moderate rain", icon = "10d", id = 501, main = "Rain"))
    )
    private val day3: Daily = Daily(
        1632477600,
        Temp(day = 282.4),
        listOf(WeatherX(description = "moderate rain", icon = "10d", id = 501, main = "Rain"))
    )

//    companion object{
//        fun create() = MainFragment
//    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMainBinding.inflate(inflater, container, false)
        weatherListTest.add(day)
        weatherListTest.add(day2)
        weatherListTest.add(day3)

        adapter = WeatherAdapter(weatherListTest)

        binding.buttonAdd.setOnClickListener {
            contract().changeCity()
        }

        viewModel.value.findWeatherLiveData.observe(viewLifecycleOwner) {
            Toast.makeText(requireContext(), "it", Toast.LENGTH_LONG).show()
        }

        println("-------------------------")
        println(viewModel.value.findWeatherLiveData.value?.daily.toString())

        setupRecyclerView()
        return binding.root
    }

    private fun setupRecyclerView() {
        binding.apply {
            recyclerViewMain.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            recyclerViewMain.adapter = adapter

        }
    }
}