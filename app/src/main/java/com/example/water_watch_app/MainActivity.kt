package com.example.water_watch_app

import SettingsFragment
import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import com.example.water_watch_app.ui.ContactsFragment
import com.example.water_watch_app.ui.HomeFragment
import com.example.water_watch_app.ui.MaintenanceFragment
import com.example.water_watch_app.utils.NoInternetDialogFragment
import com.example.water_watch_app.utils.ConnectivityManagerHelper
import com.google.android.material.bottomnavigation.BottomNavigationView
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var bottomNavigationView: BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        init()

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.container)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    fun checkInternetConnection(): Boolean {
        return if (!ConnectivityManagerHelper.isNetworkAvailable(this)) {
            // Mostrar el diálogo de no conexión a Internet
            NoInternetDialogFragment().show(supportFragmentManager, "NoInternetDialog")
            findViewById<View>(R.id.blocking_view).visibility = View.VISIBLE
            false
        } else {
            findViewById<View>(R.id.blocking_view).visibility = View.GONE
            true
        }
    }

    private fun init() {
        bottomNavigationView = findViewById(R.id.nav_view)

        if (checkInternetConnection()) {
            changeFrame(HomeFragment())
        }

        bottomNavigationView.setOnItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.navigation_home -> {
                    if (checkInternetConnection()) {
                        changeFrame(HomeFragment())
                    }
                    true
                }
                R.id.navigation_contacts -> {
                    if (checkInternetConnection()) {
                        changeFrame(ContactsFragment())
                    }
                    true
                }
                R.id.navigation_maintenance -> {
                    if (checkInternetConnection()) {
                        changeFrame(MaintenanceFragment())
                    }
                    true
                }
                R.id.navigation_settings -> {
                    if (checkInternetConnection()) {
                        changeFrame(SettingsFragment())
                    }
                    true
                }
                else -> {
                    false
                }
            }
        }
    }

    private fun changeFrame(fragment: Fragment) {
        supportFragmentManager.beginTransaction().replace(R.id.container, fragment).commitAllowingStateLoss()
    }
}
