package com.example.weatherapp.model

import com.google.gson.annotations.SerializedName

data class Components(
    @SerializedName("ISO_3166-1_alpha-2")
    val countryCode: String,
    val city: String
)