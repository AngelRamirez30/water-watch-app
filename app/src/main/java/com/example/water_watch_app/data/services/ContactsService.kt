package com.example.water_watch_app.data.services

import com.example.water_watch_app.data.models.ContactsData
import retrofit2.http.GET

interface ContactsService {
    @GET("get-all-contacts")
    suspend fun getAllContacts(): ContactsData
}