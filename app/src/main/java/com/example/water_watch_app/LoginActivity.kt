package com.example.water_watch_app

import android.content.Intent
import android.content.SharedPreferences
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import com.example.water_watch_app.data.repositories.AuthRepository
import com.google.android.material.imageview.ShapeableImageView
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class LoginActivity : AppCompatActivity() {

    @Inject
    lateinit var authRepository: AuthRepository

    @Inject
    lateinit var sharedPreferences: SharedPreferences

    private lateinit var usernameEditText: EditText
    private lateinit var passwordEditText: EditText
    private lateinit var loginButton: ShapeableImageView
    private lateinit var arrowToPassword: ImageView
    private lateinit var arrowToLogin: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        usernameEditText = findViewById(R.id.usernameEditText)
        passwordEditText = findViewById(R.id.passwordEditText)
        loginButton = findViewById(R.id.loginButton)
        arrowToPassword = findViewById(R.id.arrowToPassword)
        arrowToLogin = findViewById(R.id.arrowToLogin)

        // Tamaño deseado para los íconos en píxeles
        val iconSize = 48 // Ajusta este valor según tus necesidades

        // Configurar el tamaño de los íconos
        setDrawableSize(usernameEditText, R.drawable.ic_profile_black_24dp, iconSize)
        setDrawableSize(passwordEditText, R.drawable.ic_lock, iconSize)

        // Inicialmente deshabilitar las flechas
        arrowToPassword.isEnabled = false
        arrowToLogin.isEnabled = false
        loginButton.isEnabled = false

        // Añadir TextWatcher para habilitar/deshabilitar las flechas según el texto ingresado
        usernameEditText.addTextChangedListener(textWatcher)
        passwordEditText.addTextChangedListener(textWatcher)

        // Configurar los listeners para los botones de navegación
        arrowToPassword.setOnClickListener {
            if (arrowToPassword.isEnabled) {
                passwordEditText.requestFocus()
            }
        }

        arrowToLogin.setOnClickListener {
            if (arrowToLogin.isEnabled) {
                performLogin()
            }
        }

        loginButton.setOnClickListener {
            if (loginButton.isEnabled) {
                performLogin()
            }
        }
    }

    private val textWatcher = object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

        override fun afterTextChanged(s: Editable?) {
            val username = usernameEditText.text.toString().trim()
            val password = passwordEditText.text.toString().trim()

            // Habilitar/deshabilitar flecha de usuario
            arrowToPassword.isEnabled = username.isNotEmpty()

            // Habilitar/deshabilitar flecha de login
            val isFormValid = username.isNotEmpty() && password.isNotEmpty()
            arrowToLogin.isEnabled = isFormValid
            loginButton.isEnabled = isFormValid
        }
    }

    private fun performLogin() {
        val username = usernameEditText.text.toString()
        val password = passwordEditText.text.toString()

        lifecycleScope.launch {
            try {
                val response = authRepository.login(username, password)
                saveTokenToSharedPreferences(response.token)
                val mainIntent = Intent(this@LoginActivity, MainActivity::class.java)
                startActivity(mainIntent)
                finish()
            } catch (e: Exception) {
                Toast.makeText(this@LoginActivity, "Login failed: ${e.message}", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun setDrawableSize(editText: EditText, drawableResId: Int, size: Int) {
        val drawable: Drawable? = ContextCompat.getDrawable(this, drawableResId)
        drawable?.setBounds(0, 0, size, size)
        editText.setCompoundDrawables(drawable, null, null, null)
    }

    private fun saveTokenToSharedPreferences(token: String) {
        val editor = sharedPreferences.edit()
        editor.putString("token", token)
        editor.apply()
    }
}
