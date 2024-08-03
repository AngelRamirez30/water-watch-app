package com.example.water_watch_app.ui

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.lifecycleScope
import com.example.water_watch_app.R
import com.example.water_watch_app.data.repositories.HomeRepository
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class HomeFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    @Inject
    lateinit var homeRepository: HomeRepository

    lateinit var lblHome: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_home, container, false)

        lblHome = view.findViewById(R.id.lblHome)
        loadHomeData()

        return view
    }

    companion object {
        // TODO: Rename and change types and number of parameters
    }

    private fun loadHomeData() {
        lifecycleScope.launch {
            try {
                val homeData = homeRepository.getHomeData()
                Log.i("Home data","Datos recibidos: $homeData")
                lblHome.text = homeData.fullPhoneNumber // Asegúrate de que 'fullPhoneNumber' existe en tu modelo
            } catch (e: Exception) {
                lblHome.text = e.message
            }
        }
    }
}
