package com.example.comandapp

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.comandapp.databinding.ActivityCocineroBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class CocineroActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCocineroBinding
    private lateinit var database: DatabaseReference
    private lateinit var adapter: PedidosAdapter
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCocineroBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Iniciar firebaseAuth
        auth = FirebaseAuth.getInstance()

        // Configurar Toolbar
        setSupportActionBar(binding.toolbarCocinero)
        supportActionBar?.title = "Pedidos"


        // Inicializar Firebase y RecyclerView
        database = FirebaseDatabase.getInstance().getReference("pedidos")
        adapter = PedidosAdapter()
        binding.recyclerViewPedidos.layoutManager = LinearLayoutManager(this)
        binding.recyclerViewPedidos.adapter = adapter

        // Cargar pedidos desde Firebase
        cargarPedidos()

        binding.btnCerrarSesion.setOnClickListener {
            auth.signOut()
            Toast.makeText(this, "Sesión cerrada", Toast.LENGTH_SHORT).show()

            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
    }


    private fun cargarPedidos() {
        database.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val listaPedidos = mutableListOf<Pedido>()

                snapshot.children.forEach { pedidoSnapshot ->
                    val id = pedidoSnapshot.key ?: return@forEach
                    val mesa = pedidoSnapshot.child("mesa").getValue(String::class.java) ?: ""
                    val detalle = pedidoSnapshot.child("detalle").getValue(String::class.java) ?: ""
                    val estado = pedidoSnapshot.child("estado").getValue(String::class.java) ?: ""

                    if (estado == "pendiente") {
                        val pedido = Pedido(id, mesa, detalle, estado)
                        listaPedidos.add(pedido)
                    }
                }

                adapter.setPedidos(listaPedidos)
            }

            override fun onCancelled(error: DatabaseError) {
                Log.e("CocineroActivity", "Error al leer pedidos: ${error.message}")
            }
        })
    }






}
