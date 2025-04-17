package com.example.comandapp

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.comandapp.databinding.ActivityMeseroBinding

class MeseroActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMeseroBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMeseroBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // FAB que abre el diálogo
        binding.fabNuevoPedido.setOnClickListener {
            val dialogView = layoutInflater.inflate(R.layout.dialog_seleccionar_mesa, null)
            val spinner = dialogView.findViewById<Spinner>(R.id.spinnerMesas)

            val mesas = listOf("Mesa 1", "Mesa 2")
            val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, mesas)
            spinner.adapter = adapter

            AlertDialog.Builder(this)
                .setTitle("Nuevo Pedido")
                .setView(dialogView)
                .setPositiveButton("Aceptar") { _, _ ->
                    val mesaSeleccionada = spinner.selectedItem.toString()
                    Toast.makeText(this, "Seleccionaste: $mesaSeleccionada", Toast.LENGTH_SHORT).show()

                    // Aquí después abrirás el diálogo de productos
                }
                .setNegativeButton("Cancelar", null)
                .show()
        }
    }
}
