package com.example.water_watch_app.data.models

data class AllActiveSaaDataItem(
    val current_saa_capacity: Int,
    val current_saa_capacity2: Int,
    val days_since_last_maintenance: Int,
    val full_address: String,
    val is_good: String,
    val is_good_description: String,
    val max_saa_capacity: Int,
    val max_saa_capacity2: Int,
    val ph_level: Int,
    val saa_description: String,
    val saa_height: Int,
    val saa_height2: Int,
    val saa_id: Int,
    val saa_name: String,
    val serial_key: String,
    val water_level: Int,
    val water_level2: Int
)