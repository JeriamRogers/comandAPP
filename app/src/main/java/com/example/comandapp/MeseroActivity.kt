package com.example.comandapp

import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.comandapp.databinding.ActivityMeseroBinding
import com.google.firebase.auth.FirebaseAuth

class MeseroActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMeseroBinding
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMeseroBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Iniciar firebaseAuth
        auth = FirebaseAuth.getInstance()

        // Boton FAB abre el dialogo para las mesas
        binding.fabNuevoPedido.setOnClickListener {
            val dialogView = layoutInflater.inflate(R.layout.dialog_seleccionar_mesa, null)
            val spinner = dialogView.findViewById<Spinner>(R.id.spinnerMesas)

            val mesas = listOf("Mesa 1", "Mesa 2", "Mesa 3", "Mesa 4")
            val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, mesas)
            spinner.adapter = adapter

            // Muestra el dialogo para ver las mesas
            AlertDialog.Builder(this).setTitle("Seleccionar Mesa").setView(dialogView).setPositiveButton("Aceptar") { _, _ ->
                    val mesaSeleccionada = spinner.selectedItem.toString()
                    Toast.makeText(this, "Seleccionaste: $mesaSeleccionada", Toast.LENGTH_SHORT).show()

                    // Muestra la lista de productos para la mesa seleccionada
                    val intent = Intent(this, ProductosActivity::class.java)
                    intent.putExtra("mesaSeleccionada", mesaSeleccionada) // Pasar la mesa seleccionada
                    startActivity(intent) // Iniciar ProductosActivity
                }
                .setNegativeButton("Cancelar", null)
                .show()
        }

        binding.btnCerrarSesion.setOnClickListener {
            auth.signOut()
            Toast.makeText(this, "Sesión cerrada", Toast.LENGTH_SHORT).show()

            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}
