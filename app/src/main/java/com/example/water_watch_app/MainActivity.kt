package com.example.water_watch_app

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import com.example.water_watch_app.ui.HomeFragment
import com.example.water_watch_app.ui.MaintenanceFragment
import com.example.water_watch_app.ui.ProfileFragment
import com.example.water_watch_app.ui.ServicesFragment
import com.example.water_watch_app.ui.SettingsFragment

class MainActivity : AppCompatActivity() {

    private lateinit var bottomNavigationView: BottomNavigationView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        // init
        init();

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.container)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    private fun init() {
        bottomNavigationView = findViewById(R.id.nav_view)

        changeFrame(HomeFragment())
        bottomNavigationView.setOnItemSelectedListener {
                menuItem ->
            when(menuItem.itemId) {
                R.id.navigation_home-> {
                    changeFrame(HomeFragment())
                    true
                }
                R.id.navigation_profile-> {
                    changeFrame(ProfileFragment())
                    true
                }
                R.id.navigation_services-> {
                    changeFrame(ServicesFragment())
                    true
                }
                R.id.navigation_maintenance-> {
                    changeFrame(MaintenanceFragment())
                    true
                }
                R.id.navigation_settings-> {
                    changeFrame(SettingsFragment())
                    true
                }
                else -> {
                    false
                }
            }
        }
    }
    private fun changeFrame(fragment: Fragment) {
        supportFragmentManager.beginTransaction().replace(R.id.container, fragment).commit()
    }
}