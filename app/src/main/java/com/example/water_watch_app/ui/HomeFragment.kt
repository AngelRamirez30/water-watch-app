package com.example.water_watch_app.ui

import android.content.Intent
import android.content.res.Resources
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ProgressBar
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import com.example.water_watch_app.LoginActivity
import com.example.water_watch_app.R
import com.example.water_watch_app.data.models.AllActiveSaaDataItem
import com.example.water_watch_app.data.repositories.HomeRepository
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import retrofit2.HttpException
import javax.inject.Inject

@AndroidEntryPoint
class HomeFragment : BaseFragment() {

    @Inject
    lateinit var homeRepository: HomeRepository

    private lateinit var lblHome: TextView
    private lateinit var saaSpinner: Spinner
    private lateinit var tvUbicacion: TextView
    private lateinit var tvEstado: TextView
    private lateinit var tvTipo: TextView
    private lateinit var tvCapacidad: TextView
    private lateinit var tvPh: TextView
    private lateinit var tvPet: TextView
    private lateinit var tvUltimoMantenimiento: TextView
    private lateinit var progressBar: ProgressBar

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_home, container, false)

        // Inicializar las vistas
        lblHome = view.findViewById(R.id.lblHome)
        saaSpinner = view.findViewById(R.id.saaSpinner)
        tvUbicacion = view.findViewById(R.id.tvUbicacion)
        tvEstado = view.findViewById(R.id.tvEstado)
        tvTipo = view.findViewById(R.id.tvTipo)
        tvCapacidad = view.findViewById(R.id.tvCapacidad)
        tvPh = view.findViewById(R.id.tvPh)
        tvPet = view.findViewById(R.id.tvPet)
        tvUltimoMantenimiento = view.findViewById(R.id.tvUltimoMantenimiento)
        progressBar = view.findViewById(R.id.progressBar)

        val locationDrawable = ContextCompat.getDrawable(requireContext(), R.drawable.ic_location)
        locationDrawable?.setBounds(0, 0, 24.dpToPx(), 24.dpToPx()) // Ajusta el tamaño
        tvUbicacion.setCompoundDrawables(locationDrawable, null, null, null)

        val dateDrawable = ContextCompat.getDrawable(requireContext(), R.drawable.ic_maintenance_black_24dp)
        dateDrawable?.setBounds(0, 0, 24.dpToPx(), 24.dpToPx()) // Ajusta el tamaño
        tvUltimoMantenimiento.setCompoundDrawables(dateDrawable, null, null, null)

        loadHomeData()

        return view
    }

    private fun loadHomeData() {
        // Mostrar el ProgressBar antes de iniciar la carga
        progressBar.visibility = View.VISIBLE

        lifecycleScope.launch {
            try {
                val allActiveSaa = homeRepository.getAllActiveSaa()
                Log.i("Home data", "Datos recibidos: $allActiveSaa")
                if (allActiveSaa.isNotEmpty()) {
                    val firstSaa = allActiveSaa[0]
                    populateFields(firstSaa)
                }
                populateSpinner(allActiveSaa)
            } catch (e: Exception) {
                when (e) {
                    is HttpException -> {
                        if (e.code() == 401) {
                            Log.e("HomeFragment", "Token no válido o expirado.")
                            Toast.makeText(requireContext(), "Sesión expirada. Por favor, inicia sesión de nuevo.", Toast.LENGTH_SHORT).show()
                            redirectToLogin()
                        } else {
                            val errorMessage = "Error al cargar los datos: ${e.message()} (HTTP ${e.code()})"
                            Log.e("HomeFragment", errorMessage, e)
                            lblHome.text = errorMessage
                        }
                    }
                    else -> {
                        Log.e("HomeFragment", "Error al cargar los datos", e)
                        lblHome.text = "Error al cargar los datos: ${e.message}"
                    }
                }
            } finally {
                // Ocultar el ProgressBar después de que la carga haya finalizado
                progressBar.visibility = View.GONE
            }
        }
    }

    private fun redirectToLogin() {
        val intent = Intent(requireContext(), LoginActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
        startActivity(intent)
        requireActivity().finish()
    }

    private fun populateFields(saaData: AllActiveSaaDataItem) {
        // Rellenar los campos con la información recibida
        tvUbicacion.text = saaData.full_address
        tvEstado.text = "Estado: ${saaData.is_good}"
        tvTipo.text = "Tipo: ${saaData.saa_name}"
        tvCapacidad.text = "Capacidad: 1200L"
        tvPh.text = "Ph: 7"
        tvPet.text = "Pet: 0"
        tvUltimoMantenimiento.text = "Último Mantenimiento: 14/07/2024"
    }

    private fun populateSpinner(descriptions: List<AllActiveSaaDataItem>) {
        val adapter = ArrayAdapter(
            requireContext(),
            android.R.layout.simple_spinner_item,
            descriptions.map { it.saa_name }
        )
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        saaSpinner.adapter = adapter
    }

    fun Int.dpToPx(): Int {
        return (this * Resources.getSystem().displayMetrics.density).toInt()
    }
}
