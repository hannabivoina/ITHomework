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
    companion object{
        fun create() = MainFragment
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMainBinding.inflate(inflater, container, false)


        viewModel.value.findWeatherLiveData.observe(viewLifecycleOwner){
            if (it.daily.isEmpty()){
                Toast.makeText(requireContext(),"такой погоды нет", Toast.LENGTH_LONG).show()
            }
            else {
                Toast.makeText(requireContext(), it.toString(), Toast.LENGTH_LONG).show()
            }
        }

        viewModel.value.findCityLiveData.observe(viewLifecycleOwner){
            println("-------------------------------$it")
//            it.reduce { acc, any -> acc.toString() + any.toString() }.let {data->
                if (it.results.isEmpty()){
                    Toast.makeText(requireContext(),"такого города нет", Toast.LENGTH_LONG).show()
                }
            else {var geometry = "${it.results[0].geometry.lat}, ${it.results[0].geometry.lng}"
                Toast.makeText(requireContext(), geometry, Toast.LENGTH_LONG).show()


            }
//            }
            //toRecyclerView
        }

        viewModel.value.errorCityLiveData.observe(viewLifecycleOwner){
            Toast.makeText(requireContext(), "ошибка"+it, Toast.LENGTH_LONG).show()
            println("-------ошибка------------------------$it")
        }

        binding.mainFragmentEditText.doOnTextChanged { text, _, _, count ->
            text?.let {
                if (text.length > 2){
                    viewModel.value.findCity(text)
                }
            }
        }

        binding.buttonAdd.setOnClickListener {
            AppContract.changeCity()
        }

        return binding.root
    }
}