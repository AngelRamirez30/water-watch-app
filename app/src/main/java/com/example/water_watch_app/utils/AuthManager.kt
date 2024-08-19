package com.example.water_watch_app.utils

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import com.example.water_watch_app.LoginActivity
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthManager @Inject constructor(
    @ApplicationContext private val context: Context,
    private val sharedPreferences: SharedPreferences
) {

    fun logout() {
        // Borrar el token de SharedPreferences
        val editor = sharedPreferences.edit()
        editor.remove("token")
        editor.apply()



        // Redirigir a LoginActivity
        val intent = Intent(context, LoginActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
        context.startActivity(intent)
    }
}
