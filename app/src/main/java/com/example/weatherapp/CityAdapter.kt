package com.example.weatherapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.weatherapp.database.WeatherForecast
import com.example.weatherapp.databinding.ItemCityBinding

interface CityInterface{
    fun changeCurrent(id: Int)
}

class CityAdapter(val cityInterface: CityInterface) : RecyclerView.Adapter<CityAdapter.CityViewHolder>()/*, View.OnClickListener*/ {
    private var cityList = ArrayList<WeatherForecast>()

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

    inner class CityViewHolder(item: View) : RecyclerView.ViewHolder(item) {
        val binding = ItemCityBinding.bind(item)

        fun bind(city: WeatherForecast) = with(binding) {
            cityName.text = city.cityName
            cityLay.setOnClickListener {
                cityInterface.changeCurrent(city.id)
                notifyDataSetChanged()
            }
            imageCheckCurrent.setImageResource(checkCurrent(city))
        }
    }

    fun addCity(city: WeatherForecast){
        cityList.toMutableList().add(city)
        notifyDataSetChanged()
    }

    fun updateList(newList:List<WeatherForecast>){
        cityList.clear()
        cityList.addAll(newList)
        notifyDataSetChanged()
    }

    private fun checkCurrent(city: WeatherForecast): Int{
        if(city.currentStatus){
            return R.drawable.check_true
        } else{
            return R.drawable.check_false
        }
    }

}