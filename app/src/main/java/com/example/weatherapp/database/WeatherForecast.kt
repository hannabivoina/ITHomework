package com.example.weatherapp.database

import androidx.room.*
import com.example.weatherapp.wheather.CityWeather
import com.example.weatherapp.wheather.Current
import com.google.gson.Gson

@TypeConverters(WeatherConverter::class)
@Entity(tableName = "savedForecast")
data class WeatherForecast(
    @PrimaryKey @ColumnInfo(name = "id") val id: Int,
    var currentStatus: Boolean,
    @ColumnInfo(name = "cityName") val cityName: String,
    @ColumnInfo(name = "geoLat") val geoLat: Double,
    @ColumnInfo(name = "geoLng") val geoLng: Double,
    @ColumnInfo(name = "weather") val weather: CityWeather
)

class WeatherConverter{
    @TypeConverter
    fun fromWeather(cityWeather: CityWeather): String {
        val gson = Gson()
        return gson.toJson(cityWeather)
    }
    @TypeConverter
    fun toWeather(data: String): CityWeather{
        val gson = Gson()
        return gson.fromJson(data, CityWeather::class.java)
    }
}
