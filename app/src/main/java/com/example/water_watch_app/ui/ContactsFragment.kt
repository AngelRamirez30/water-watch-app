package com.example.water_watch_app.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.PermissionChecker
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.water_watch_app.R
import com.example.water_watch_app.data.repositories.ContactsRepository
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class ContactsFragment : BaseFragment() {

    @Inject
    lateinit var contactsRepository: ContactsRepository

    private lateinit var contactsRecyclerView: RecyclerView
    private lateinit var contactsAdapter: ContactsAdapter
    private lateinit var progressBar: ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_contacts, container, false)

        // Verifica y solicita permisos
        if (ContextCompat.checkSelfPermission(requireContext(), android.Manifest.permission.CALL_PHONE) != PermissionChecker.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(requireActivity(), arrayOf(android.Manifest.permission.CALL_PHONE), REQUEST_CALL_PERMISSION)
        }

        contactsRecyclerView = view.findViewById(R.id.contactsRecyclerView)
        progressBar = view.findViewById(R.id.progressBar)
        contactsRecyclerView.layoutManager = LinearLayoutManager(context)
        contactsAdapter = ContactsAdapter(emptyList(), requireContext()) { contact ->
            // Handle item click if needed
        }
        contactsRecyclerView.adapter = contactsAdapter

        loadContactsData()

        return view
    }

    private fun loadContactsData() {
        // Mostrar el ProgressBar y ocultar el RecyclerView
        progressBar.visibility = View.VISIBLE
        contactsRecyclerView.visibility = View.GONE

        lifecycleScope.launch {
            try {
                val contactsData = contactsRepository.getAllContacts()
                contactsAdapter.actualizarLista(contactsData)
                // Ocultar el ProgressBar y mostrar el RecyclerView
                progressBar.visibility = View.GONE
                contactsRecyclerView.visibility = View.VISIBLE
            } catch (e: Exception) {
                Log.e("ContactsFragment", "Error al obtener los datos de contacto", e)
                // Puedes manejar el error aquí, por ejemplo, mostrando un mensaje de error
                progressBar.visibility = View.GONE
            }
        }
    }

    private val REQUEST_CALL_PERMISSION = 1

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_CALL_PERMISSION) {
            if ((grantResults.isNotEmpty() && grantResults[0] == PermissionChecker.PERMISSION_GRANTED)) {
                // Permiso concedido
            } else {
                // Permiso denegado
                Toast.makeText(requireContext(), "Permission to make calls is not granted", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
