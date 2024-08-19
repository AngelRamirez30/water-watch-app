package com.example.water_watch_app

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity

class GenericActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_LAYOUT_ID = "extra_layout_id"

        fun startActivity(context: Context, layoutId: Int) {
            val intent = Intent(context, GenericActivity::class.java)
            intent.putExtra(EXTRA_LAYOUT_ID, layoutId)
            context.startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Obtener el layoutId pasado en el intent
        val layoutId = intent.getIntExtra(EXTRA_LAYOUT_ID, 0)
        if (layoutId != 0) {
            // Establecer el layout según el layoutId
            setContentView(layoutId)
            val btnClose: ImageView = findViewById(R.id.btnClose)
            btnClose.setOnClickListener {
                finish()
            }
        } else {
            // Si no se pasa un layout válido, cerrar la actividad
            finish()
        }
    }
}
