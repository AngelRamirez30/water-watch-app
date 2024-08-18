package com.example.water_watch_app.utils

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import com.example.water_watch_app.MainActivity
import com.example.water_watch_app.R


class NoInternetDialogFragment : DialogFragment() {
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return AlertDialog.Builder(requireContext())
            .setTitle(R.string.no_internet_title)
            .setMessage(R.string.no_internet_message)
            .setPositiveButton(R.string.no_internet_button) { _, _ ->
                (activity as? MainActivity)?.checkInternetConnection()
            }
            .create()
    }
}