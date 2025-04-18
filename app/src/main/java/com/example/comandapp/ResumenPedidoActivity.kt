package com.example.comandapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.comandapp.databinding.ActivityResumenPedidoBinding

class ResumenPedidoActivity : AppCompatActivity() {

    private lateinit var binding: ActivityResumenPedidoBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityResumenPedidoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Obtener datos del intent
        val resumen = intent.getStringExtra("resumen") ?: "Sin resumen"
        val mesa = intent.getStringExtra("mesa") ?: "Sin mesa"


        binding.tvResumenDetalle.text = "Mesa: $mesa\n\n$resumen"

        // Botón para volver (debo agregar finalizar y volver al principio)
        binding.btnFinalizar.setOnClickListener {
            finish()
        }
    }
}
