package com.example.comandapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.comandapp.databinding.ActivityCocineroBinding

class CocineroActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCocineroBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // ViewBinding
        binding = ActivityCocineroBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}
