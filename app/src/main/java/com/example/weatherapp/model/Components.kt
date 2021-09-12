package com.example.weatherapp.model

import com.google.gson.annotations.SerializedName

data class Components(
    @SerializedName("ISO_3166-1_alpha-2")
    val iso1: String,
    @SerializedName("ISO_3166-1_alpha-3")
    val iso2: String,
    val _category: String,
    val _type: String,
    val city: String,
    val continent: String,
    val country: String,
    val country_code: String
)