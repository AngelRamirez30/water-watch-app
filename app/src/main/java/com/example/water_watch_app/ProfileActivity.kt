package com.example.water_watch_app
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.water_watch_app.data.models.ProfileData
import com.example.water_watch_app.data.repositories.ProfileRepository
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@AndroidEntryPoint
class ProfileActivity : AppCompatActivity() {

    @Inject
    lateinit var profileRepository: ProfileRepository

    private lateinit var usernameTextView: TextView
    private lateinit var emailTextView: TextView
    private lateinit var phoneTextView: TextView
    private lateinit var closeButton: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_profile)

        // Initialize views
        usernameTextView = findViewById(R.id.username)
        emailTextView = findViewById(R.id.email)
        phoneTextView = findViewById(R.id.phone)
        closeButton = findViewById(R.id.btnClose)

        closeButton.setOnClickListener {
            finish() // Close the activity when the close button is clicked
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.mainProfile)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Load profile data
        loadProfileData()
    }

    private fun loadProfileData() {
        CoroutineScope(Dispatchers.IO).launch {
            val profileData = getProfileData()
            profileData?.let {
                withContext(Dispatchers.Main) {
                    updateUI(it)
                }
            }
        }
    }

    private suspend fun getProfileData(): ProfileData? {
        return try {
            profileRepository.getProfileData()
        } catch (e: Exception) {
            null
        }
    }

    private fun updateUI(profileData: ProfileData) {
        usernameTextView.text = profileData.username
        emailTextView.text = profileData.email
        phoneTextView.text = profileData.full_phone_number
    }
}
