package com.example.comandapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.comandapp.databinding.ActivityPaginaBinding

class Pagina : AppCompatActivity() {

    private lateinit var binding: ActivityPaginaBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPaginaBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Aquí puedes personalizar la pantalla de inicio
        binding.textViewBienvenida.text = "¡Bienvenido!"
    }
}
