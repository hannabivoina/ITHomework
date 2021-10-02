package com.example.weatherapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.weatherapp.databinding.ItemCityBinding

class CityAdapter() : RecyclerView.Adapter<CityAdapter.CityViewHolder>()/*, View.OnClickListener*/ {
    private var cityList = ArrayList<String>()

    override fun getItemCount(): Int = cityList.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CityAdapter.CityViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_city, parent, false)
//        view.setOnClickListener(this)
        return CityViewHolder(view)
    }

    override fun onBindViewHolder(holder: CityAdapter.CityViewHolder, position: Int) {
        val city = cityList[position]
//        holder.itemView.tag = city
        holder.bind(city)
    }

//    override fun onClick(view: View) {
//        val city = view.tag
//
//     }

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

    fun updateList(newList:ArrayList<String>){
        cityList = newList
        notifyDataSetChanged()
    }

}