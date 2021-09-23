package com.example.weatherapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.weatherapp.databinding.ItemCityBinding

class CityAdapter() : RecyclerView.Adapter<CityAdapter.CityViewHolder>() {
    private var cityList = ArrayList<String>()
//    set(newCity){
//        field = newCity
//        notifyDataSetChanged()
//    }

    override fun getItemCount(): Int = cityList.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CityAdapter.CityViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_city, parent, false)
        return CityViewHolder(view)
    }

    override fun onBindViewHolder(holder: CityAdapter.CityViewHolder, position: Int) {
        val city = cityList[position]
        holder.bind(city)
    }

    class CityViewHolder(item: View) : RecyclerView.ViewHolder(item) {
        val binding = ItemCityBinding.bind(item)
        fun bind(cityCode: String) = with(binding) {
            cityName.text = cityCode
        }
    }

    fun addCity(city: String){
        cityList.add(city)
        notifyDataSetChanged()
    }

}