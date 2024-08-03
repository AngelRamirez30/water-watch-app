package com.example.water_watch_app.data.models

import com.google.gson.annotations.SerializedName

data class ContactsDataItem(
    @SerializedName("StrPhoneNumbers") val strPhoneNumbers: List<String>,
    val emails: List<String>,
    val id: Int,
    val name: String,
    @SerializedName("photo_url") val photoUrl: String
)
