package com.example.water_watch_app.data.models

data class AllActiveSaaDataItem(
    val full_address: String,
    val is_good: String,
    val is_good_description: String,
    val saa_description: String,
    val saa_id: Int,
    val saa_name: String,
    val serial_key: String
)