package com.example.weatherapp


import com.example.weatherapp.model.CityGeo
import com.example.weatherapp.model.License
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.HttpUrl
import okhttp3.OkHttpClient
import okhttp3.Request
import java.lang.Exception
import java.util.Date.from

//https://api.opencagedata.com/geocode/v1/json?q=minsk&key=67849701dd23440a85ccca03eb079a5b

//const val baseUrl = "https://api.opencagedata.com/geocode/v1/json?key=67849701dd23440a85ccca03eb079a5b&no_annotations=1&add_request=1&limit=1&q=minsk"

private const val HTTPS = "https"
private const val BASE_GEO_HOST = "api.opencagedata.com"
private const val BASE_GEO_CODE = "geocode"
private const val BASE_GEO_VERSION = "v1"
private const val BASE_GEO_TYPE = "json"
private const val PARAM_QUERY = "q"
private const val PARAM_ANNOTATION = "no_annotations"
private const val PARAM_REQUEST = "add_request"
private const val PARAM_LIMIT = "limit"
private const val PARAM_API_KEY = "key"
class WeatherRepository(private val geoApi: GeoApi) {

    suspend fun findCityGeo(query: String): Result<List<com.example.weatherapp.model.Result>>{
        return withContext(Dispatchers.IO){
            runCatching {
                geoApi.findCityGeoAsync(query = query)
                    .await()
                    ?.takeIf { it.isSuccessful }
                    ?.body()
                    ?.results
                    ?: throw Exception("Empty Data")
            }
        }
    }
}