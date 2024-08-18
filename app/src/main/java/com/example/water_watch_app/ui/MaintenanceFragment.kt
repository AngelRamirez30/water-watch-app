package com.example.water_watch_app.ui

import android.app.AlertDialog
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.CalendarView
import android.widget.EditText
import android.widget.TextView
import com.example.water_watch_app.R
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale


class MaintenanceFragment : Fragment() {

    private lateinit var calendarView: CalendarView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_maintenance, container, false)

        calendarView = view.findViewById(R.id.calendarView)

        calendarView.setOnDateChangeListener { _, year, month, dayOfMonth ->
            val selectedDate = Calendar.getInstance().apply {
                set(year, month, dayOfMonth)
            }

            if (selectedDate.before(Calendar.getInstance())) {
                // Show message or handle the case when the selected date is today or in the past
                Log.i("MaintenanceFragment", "Fecha seleccionada no válida.")
            } else {
                showMaintenanceDialog(selectedDate)
            }
        }

        return view
    }

    private fun showMaintenanceDialog(date: Calendar) {
        val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        val dateString = dateFormat.format(date.time)

        val dialogView = LayoutInflater.from(context).inflate(R.layout.dialog_maintenance, null)
        val dialogMessage = dialogView.findViewById<TextView>(R.id.dialogMessage)
        val inputDetails = dialogView.findViewById<EditText>(R.id.inputDetails)
        val btnConfirm = dialogView.findViewById<Button>(R.id.btnConfirm)
        val btnCancel = dialogView.findViewById<Button>(R.id.btnCancel)

        dialogMessage.text = "¿Desea que le realicen mantenimiento el día $dateString?"

        val alertDialog = AlertDialog.Builder(requireContext())
            .setView(dialogView)
            .create()

        btnConfirm.setOnClickListener {
            // Handle confirm logic here
            val details = inputDetails.text.toString()
            if (details.isNotBlank()) { // Check if details are not empty
                Log.i("MaintenanceFragment", "Mantenimiento confirmado para el día $dateString con detalles: $details")
                alertDialog.dismiss()
            } else {
                inputDetails.error = "Ingrese detalles para continuar" // Provide feedback if empty
            }
        }

        btnCancel.setOnClickListener {
            alertDialog.dismiss()
        }

        alertDialog.show()
    }
}