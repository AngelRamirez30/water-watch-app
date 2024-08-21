package com.example.water_watch_app.ui

import android.app.AlertDialog
import android.content.Intent
import android.content.res.Resources
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ImageView
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


    private lateinit var ivTinaco: ImageView
    private lateinit var ivTinaco2: ImageView
    private lateinit var btnDetails1: Button
    private lateinit var btnDetails2: Button
    private lateinit var btnEstado: Button
    private lateinit var lblHome: TextView
    private lateinit var saaSpinner: Spinner
    private lateinit var tvUbicacion: TextView
    private lateinit var tvSerialKey: TextView
    private lateinit var tvPh: TextView
    private lateinit var tvUltimoMantenimiento: TextView
    private lateinit var progressBar: ProgressBar

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_home, container, false)

        // Inicializar las vistas
        ivTinaco = view.findViewById(R.id.ivTinaco)
        ivTinaco2 = view.findViewById(R.id.ivTinaco2)
        btnDetails1 = view.findViewById(R.id.btnDetails1)
        btnDetails2 = view.findViewById(R.id.btnDetails2)
        btnEstado = view.findViewById(R.id.btnEstado)
        lblHome = view.findViewById(R.id.lblHome)
        saaSpinner = view.findViewById(R.id.saaSpinner)
        tvUbicacion = view.findViewById(R.id.tvUbicacion)
        tvPh = view.findViewById(R.id.tvPh)
        tvSerialKey = view.findViewById(R.id.tvSerialKey)
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
        tvSerialKey.text = "Serial: ${saaData.serial_key}"
        tvPh.text = "Ph: ${saaData.ph_level}"
        tvUltimoMantenimiento.text = "Último Mantenimiento hace: ${saaData.days_since_last_maintenance} dias"

        btnEstado.setText("Estado: ${saaData.is_good}")
        btnEstado.setOnClickListener{
            Toast.makeText(context, "${saaData.is_good_description}", Toast.LENGTH_LONG).show()
        }
        btnDetails1.setOnClickListener{
            val dialog = AlertDialog.Builder(requireContext())
                .setTitle("Detalles del primer contenedor")
                .setMessage("Capacidad máxima: ${saaData.max_saa_capacity}\n" +
                        "Capacidad actual: ${saaData.current_saa_capacity}\n" +
                        "Altura: ${saaData.saa_height}\n" +
                        "Nivel del agua: ${saaData.water_level}%")
                .setPositiveButton("Cerrar", null)
                .create()
            dialog.show()
        }

        btnDetails2.setOnClickListener{
            val dialog = AlertDialog.Builder(requireContext())
                .setTitle("Detalles del segundo contenedor")
                .setMessage("Capacidad máxima: ${saaData.max_saa_capacity2}\n" +
                        "Capacidad actual: ${saaData.current_saa_capacity2}\n" +
                        "Altura: ${saaData.saa_height2}\n" +
                        "Nivel del agua: ${saaData.water_level2}%")
                .setPositiveButton("Cerrar", null)
                .create()
            dialog.show()

        }
        if (saaData.water_level < 5)
            ivTinaco.setImageResource(R.drawable.tinaco_0)
        else if (saaData.water_level < 10)
            ivTinaco.setImageResource(R.drawable.tinaco_5)
        else if (saaData.water_level < 20)
            ivTinaco.setImageResource(R.drawable.tinaco_10)
        else if (saaData.water_level < 30)
            ivTinaco.setImageResource(R.drawable.tinaco_20)
        else if (saaData.water_level < 40)
            ivTinaco.setImageResource(R.drawable.tinaco_30)
        else if (saaData.water_level < 50)
            ivTinaco.setImageResource(R.drawable.tinaco_40)
        else if (saaData.water_level < 60)
            ivTinaco.setImageResource(R.drawable.tinaco_50)
        else if (saaData.water_level < 70)
            ivTinaco.setImageResource(R.drawable.tinaco_60)
        else if (saaData.water_level < 80)
            ivTinaco.setImageResource(R.drawable.tinaco_70)
        else if (saaData.water_level < 90)
            ivTinaco.setImageResource(R.drawable.tinaco_80)
        else if (saaData.water_level < 95)
            ivTinaco.setImageResource(R.drawable.tinaco_90)
        else
            ivTinaco.setImageResource(R.drawable.tinaco_100)

        if (saaData.water_level2 < 5)
            ivTinaco2.setImageResource(R.drawable.tinaco_0)
        else if (saaData.water_level2 < 10)
            ivTinaco2.setImageResource(R.drawable.tinaco_5)
        else if (saaData.water_level2 < 20)
            ivTinaco2.setImageResource(R.drawable.tinaco_10)
        else if (saaData.water_level2 < 30)
            ivTinaco2.setImageResource(R.drawable.tinaco_20)
        else if (saaData.water_level2 < 40)
            ivTinaco2.setImageResource(R.drawable.tinaco_30)
        else if (saaData.water_level2 < 50)
            ivTinaco2.setImageResource(R.drawable.tinaco_40)
        else if (saaData.water_level2 < 60)
            ivTinaco2.setImageResource(R.drawable.tinaco_50)
        else if (saaData.water_level2 < 70)
            ivTinaco2.setImageResource(R.drawable.tinaco_60)
        else if (saaData.water_level2 < 80)
            ivTinaco2.setImageResource(R.drawable.tinaco_70)
        else if (saaData.water_level2 < 90)
            ivTinaco2.setImageResource(R.drawable.tinaco_80)
        else if (saaData.water_level2 < 95)
            ivTinaco2.setImageResource(R.drawable.tinaco_90)
        else
            ivTinaco2.setImageResource(R.drawable.tinaco_100)
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
