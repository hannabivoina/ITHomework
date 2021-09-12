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
class WeatherRepository(private val okHttpClient: OkHttpClient) {

    suspend fun findCityGeo(query: String): Result<List<com.example.weatherapp.model.Result>>{
        return withContext(Dispatchers.IO){
            runCatching {
                HttpUrl.Builder()
                    .scheme(HTTPS)
                    .host(BASE_GEO_HOST)
                    .addEncodedPathSegment(BASE_GEO_CODE)
                    .addPathSegment(BASE_GEO_VERSION)
                    .addPathSegment(BASE_GEO_TYPE)
                    .addQueryParameter(PARAM_QUERY, query)
                    .addQueryParameter(PARAM_ANNOTATION, "1")
                    .addQueryParameter(PARAM_REQUEST, "1")
                    .addQueryParameter(PARAM_LIMIT, "1")
                    .addQueryParameter(PARAM_API_KEY, "67849701dd23440a85ccca03eb079a5b")
                    .build()
                    .let {
                        Request.Builder().get().url(it).build()
                    }
                    .let {
                        okHttpClient.newCall(it).execute()
                    }.body?.let {
                        Gson().fromJson(it.string(), CityGeo::class.java).results
                    } ?: throw Exception("Empty Data")
            }
        }
    }
}