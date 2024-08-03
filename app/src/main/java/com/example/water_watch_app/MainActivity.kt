package com.example.water_watch_app

import android.os.Bundle
import android.view.ViewTreeObserver
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
import com.example.water_watch_app.ui.ProfileFragment
import com.example.water_watch_app.ui.SettingsFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var bottomNavigationView: BottomNavigationView
    private lateinit var container: ConstraintLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        container = findViewById(R.id.container)
        bottomNavigationView = findViewById(R.id.nav_view)



        // Inicializar la navegación
        init()

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.container)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    private fun init() {
        changeFrame(HomeFragment())
        bottomNavigationView.setOnItemSelectedListener { menuItem ->
            when(menuItem.itemId) {
                R.id.navigation_home -> {
                    changeFrame(HomeFragment())
                    true
                }
                R.id.navigation_profile -> {
                    changeFrame(ProfileFragment())
                    true
                }
                R.id.navigation_contacts -> {
                    changeFrame(ContactsFragment())
                    true
                }
                R.id.navigation_maintenance -> {
                    changeFrame(MaintenanceFragment())
                    true
                }
                R.id.navigation_settings -> {
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
        supportFragmentManager.beginTransaction().replace(R.id.nav_host_fragment_activity_main, fragment).commit()
    }

    private fun adjustBottomMargin(bottomNavHeight: Int) {
        val constraintSet = ConstraintSet()
        constraintSet.clone(container)
        constraintSet.connect(
            R.id.nav_host_fragment_activity_main,
            ConstraintSet.BOTTOM,
            R.id.nav_view,
            ConstraintSet.TOP,
            bottomNavHeight
        )
        constraintSet.applyTo(container)
    }
}
