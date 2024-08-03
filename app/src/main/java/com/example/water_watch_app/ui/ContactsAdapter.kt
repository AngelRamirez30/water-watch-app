package com.example.water_watch_app.ui

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.content.PermissionChecker
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.water_watch_app.R
import com.example.water_watch_app.data.models.ContactsDataItem
import com.google.android.material.imageview.ShapeableImageView

class ContactsAdapter(
    private var contactsList: List<ContactsDataItem>,
    private val context: Context,
    private val itemClickListener: (ContactsDataItem) -> Unit
) : RecyclerView.Adapter<ContactsAdapter.ViewHolder>() {

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val contactPhoto: ImageView = itemView.findViewById(R.id.contactPhoto)
        val contactEmails: TextView = itemView.findViewById(R.id.contactEmails)
        val contactPhoneNumbers: TextView = itemView.findViewById(R.id.contactPhoneNumbers)
        val callButton: ShapeableImageView = itemView.findViewById(R.id.callButton)

        fun bind(contact: ContactsDataItem) {
            contactEmails.text = contact.emails.joinToString(", ")
            contactPhoneNumbers.text = contact.strPhoneNumbers.joinToString(", ")

            if (contact.photoUrl.isNotEmpty()) {
                Glide.with(itemView.context)
                    .load(Uri.parse(contact.photoUrl))
                    .placeholder(R.drawable.placeholder_image)
                    .error(R.drawable.placeholder_image)
                    .into(contactPhoto)
            } else {
                contactPhoto.setImageResource(R.drawable.placeholder_image)
            }

            itemView.setOnClickListener {
                itemClickListener.invoke(contact)
            }

            callButton.setOnClickListener {
                if (contact.strPhoneNumbers.isNotEmpty()) {
                    val phoneNumber = contact.strPhoneNumbers[0]
                    makeCall(phoneNumber)
                } else {
                    Toast.makeText(context, "No phone number available", Toast.LENGTH_SHORT).show()
                }
            }
        }

        private fun makeCall(phoneNumber: String) {
            val intent = Intent(Intent.ACTION_CALL, Uri.parse("tel:$phoneNumber"))
            if (ContextCompat.checkSelfPermission(context, android.Manifest.permission.CALL_PHONE) == PermissionChecker.PERMISSION_GRANTED) {
                context.startActivity(intent)
            } else {
                Toast.makeText(context, "Permission to make calls is not granted", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_contact, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val contact = contactsList[position]
        holder.bind(contact)
    }

    override fun getItemCount(): Int {
        return contactsList.size
    }

    fun actualizarLista(nuevaLista: List<ContactsDataItem>) {
        contactsList = nuevaLista
        notifyDataSetChanged()
    }
}
