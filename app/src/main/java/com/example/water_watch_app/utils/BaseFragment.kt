package com.example.water_watch_app.ui

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import androidx.lifecycle.lifecycleScope
import com.example.water_watch_app.MainActivity
import kotlinx.coroutines.cancelChildren

abstract class BaseFragment : Fragment(), LifecycleObserver {
    override fun onAttach(context: Context) {
        super.onAttach(context)
        lifecycle.addObserver(this)
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    fun onFragmentResume() {
        val mainActivity = activity as? MainActivity
        if (mainActivity != null && !mainActivity.checkInternetConnection()) {
            // Si no hay conexión, cancelar cualquier operación pendiente
            viewLifecycleOwner.lifecycleScope.coroutineContext.cancelChildren()
        }
    }
}
