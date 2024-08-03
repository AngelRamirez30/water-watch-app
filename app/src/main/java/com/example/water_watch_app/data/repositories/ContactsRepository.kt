package com.example.water_watch_app.data.repositories

import com.example.water_watch_app.data.models.ContactsData
import com.example.water_watch_app.data.services.ContactsService
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ContactsRepository @Inject constructor(private val contactsService: ContactsService){
    suspend fun getAllContacts(): ContactsData {
        return contactsService.getAllContacts()
    }
}