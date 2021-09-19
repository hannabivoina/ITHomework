package com.example.weatherapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import com.example.weatherapp.databinding.FragmentMainBinding

class MainFragment: Fragment(R.layout.fragment_main) {
    private val viewModel = viewModels<WeatherViewModel>()
    lateinit var binding: FragmentMainBinding
//    companion object{
//        fun create() = MainFragment
//    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMainBinding.inflate(inflater, container, false)

        binding.buttonAdd.setOnClickListener {
            contract().changeCity()
        }

        viewModel.value.findWeatherLiveData.observe(viewLifecycleOwner){
            Toast.makeText(requireContext(),"it", Toast.LENGTH_LONG).show()
        }

        println("-------------------------")
        println(viewModel.value.findWeatherLiveData.value?.daily.toString())


        return binding.root
    }
}