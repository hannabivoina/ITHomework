package com.example.weatherapp

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.weatherapp.wheather.Daily

private const val imageUrl = "https://openweathermap.org/img/wn/"

class ItemViewHolder(view: View):RecyclerView.ViewHolder(view) {
    private val date = view.findViewById<TextView>(R.id.itemDate)
    private val icon = view.findViewById<ImageView>(R.id.itemImage)
    private val degree = view.findViewById<TextView>(R.id.itemDegree)
    private val type = view.findViewById<TextView>(R.id.itemType)

    fun getImageUrl(icon: String): String {
        val url = imageUrl + icon + "@2x.png"
        return url
    }

    fun bind(weather: Daily){
        date.text = weather.dt.toString()
        degree.text = weather.temp.day.toString()
        type.text = weather.weather[0].main
        Glide
            .with(itemView)
            .load(getImageUrl(weather.weather[0].icon))
            .centerCrop()
            .into(icon);
    }
}