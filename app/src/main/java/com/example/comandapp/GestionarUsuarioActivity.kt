package com.example.comandapp

//noinspection SuspiciousImport
import android.R
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.comandapp.databinding.ActivityGestionarUsuariosBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

class GestionarUsuariosActivity : AppCompatActivity() {

    private lateinit var binding: ActivityGestionarUsuariosBinding
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGestionarUsuariosBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Iniciar firebaseAuth
        auth = FirebaseAuth.getInstance()

        // ArrayAdapter para el Spinner
        val roles = arrayOf("mesero", "cocinero")
        val adapter = ArrayAdapter(this, R.layout.simple_spinner_item, roles)
        adapter.setDropDownViewResource(R.layout.simple_spinner_dropdown_item)

        binding.spinnerRol.adapter = adapter

        binding.btnRegistrarUsuario.setOnClickListener {
            val email = binding.etEmail.text.toString().trim()
            val password = binding.etPassword.text.toString().trim()
            val rol = binding.spinnerRol.selectedItem.toString()

            if (email.isNotEmpty() && password.isNotEmpty()) {
                auth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            val uid = task.result?.user?.uid
                            val database = FirebaseDatabase.getInstance().reference

                            val usuario = mapOf(
                                "email" to email,
                                "rol" to rol
                            )

                            if (uid != null) {
                                database.child("users").child(uid).setValue(usuario)
                                    .addOnSuccessListener {
                                        Toast.makeText(this, "Usuario registrado exitosamente", Toast.LENGTH_SHORT).show()
                                    }
                                    .addOnFailureListener {
                                        Toast.makeText(this, "Error al registrar usuario", Toast.LENGTH_SHORT).show()
                                    }
                            }
                        } else {
                            Toast.makeText(this, "Error al registrar usuario: ${task.exception?.message}", Toast.LENGTH_SHORT).show()
                        }
                    }
            } else {
                Toast.makeText(this, "Faltan campos por completar", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
