package com.example.water_watch_app.data.models

import com.google.gson.annotations.SerializedName

data class HomeData(
    val email: String,
    @SerializedName("full_phone_number") val fullPhoneNumber: String,
    val username: String
)