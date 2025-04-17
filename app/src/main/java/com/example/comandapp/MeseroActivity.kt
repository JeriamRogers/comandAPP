package com.example.comandapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.comandapp.databinding.ActivityMeseroBinding

class MeseroActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMeseroBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // ViewBinding
        binding = ActivityMeseroBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}
