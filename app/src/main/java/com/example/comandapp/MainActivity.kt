package com.example.comandapp

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.comandapp.databinding.ActivityMainBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // ViewBinding
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Inicio de firebaseAuth
        auth = FirebaseAuth.getInstance()

        binding.btnLogin.setOnClickListener {
            val email = binding.etEmail.text.toString().trim()
            val password = binding.etPassword.text.toString().trim()

            if (email.isNotEmpty() && password.isNotEmpty()) {
                auth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            val uid = auth.currentUser?.uid
                            if (uid != null) {
                                val database = FirebaseDatabase.getInstance().reference
                                database.child("users").child(uid).child("rol").get()
                                    .addOnSuccessListener { dataSnapshot ->
                                        val rol = dataSnapshot.getValue(String::class.java)
                                        when (rol) {
                                            "mesero" -> {
                                                val intent = Intent(this, MeseroActivity::class.java)
                                                startActivity(intent)
                                                finish()
                                            }
                                            "cocinero" -> {
                                                val intent = Intent(this, CocineroActivity::class.java)
                                                startActivity(intent)
                                                finish()
                                            }
                                            else -> {
                                                Toast.makeText(this, "Rol no reconocido", Toast.LENGTH_SHORT).show()
                                            }
                                        }
                                    }
                                    .addOnFailureListener {
                                        Toast.makeText(this, "Error al obtener el rol", Toast.LENGTH_SHORT).show()
                                    }
                            }
                        } else {
                            Toast.makeText(this, "Error al iniciar sesión: ${task.exception?.message}", Toast.LENGTH_LONG).show()
                        }
                    }
            } else {
                Toast.makeText(this, "Faltan campos por completar", Toast.LENGTH_SHORT).show()
            }
        }




    }
}