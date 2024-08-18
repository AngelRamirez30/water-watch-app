package com.example.water_watch_app

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.water_watch_app.data.models.TokenWrapper
import com.example.water_watch_app.data.repositories.HomeRepository
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class SplashActivity : AppCompatActivity() {

    @Inject
    lateinit var tokenWrapper: TokenWrapper

    @Inject
    lateinit var homeRepository: HomeRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Verificar el token y redirigir al usuario
        lifecycleScope.launch {
            if (isTokenValid()) {
                // Redirigir al MainActivity
                val mainIntent = Intent(this@SplashActivity, MainActivity::class.java)
                startActivity(mainIntent)
            } else {
                // Redirigir al LoginActivity
                val loginIntent = Intent(this@SplashActivity, LoginActivity::class.java)
                startActivity(loginIntent)
            }
            // Finalizar SplashActivity
            finish()
        }
    }

    private suspend fun isTokenValid(): Boolean {
        return try {
            val response = homeRepository.getAllActiveSaa()
            // Si obtenemos una respuesta válida, el token es válido
            if(response is List<*>){
                true
            } else {
                false
            }

        } catch (e: Exception) {
            // Si obtenemos un error 401, el token no es válido
            if (e.message?.contains("401") == true) {
                false
            } else {
                true // Si es otro tipo de error, consideramos que el token es válido
            }
        }
    }
}
