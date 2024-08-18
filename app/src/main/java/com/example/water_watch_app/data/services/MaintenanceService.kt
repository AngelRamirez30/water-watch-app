package com.example.water_watch_app.data.services

import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.POST

interface MaintenanceService {
    @GET("get-all-maintenance-appointments-done")
    suspend fun getAllMaintenanceAppointmentsDone()
    @POST("create-maintenance-appointment")
    suspend fun createMaintenanceAppointment()
    @GET("get-pending-maintenance-appointment")
    suspend fun getPendingMaintenanceAppointment()
    @PATCH("update-maintenance-appointment")
    suspend fun updateMaintenanceAppointment()
    @DELETE("delete-maintenance-appointment")
    suspend fun deleteMaintenanceAppointment()
}