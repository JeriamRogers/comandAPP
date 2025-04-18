package com.example.comandapp

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.comandapp.databinding.ActivityProductosBinding
import com.google.firebase.database.*

class ProductosActivity : AppCompatActivity() {

    private lateinit var binding: ActivityProductosBinding
    private lateinit var database: DatabaseReference
    public lateinit var productosAdapter: ProductosAdapter
    private lateinit var mesaSeleccionada: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProductosBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Configurar el Toolbar como ActionBar
        setSupportActionBar(binding.topAppBar)

        // Obtener el nombre de la mesa seleccionada desde el Intent
        mesaSeleccionada = intent.getStringExtra("mesaSeleccionada") ?: "Mesa no seleccionada"

        // Mostrar el nombre de la mesa seleccionada en el TopBar
        supportActionBar?.title = "Pedido: $mesaSeleccionada"


        database = FirebaseDatabase.getInstance().getReference("productos")
        productosAdapter = ProductosAdapter()
        binding.recyclerViewProductos.layoutManager = LinearLayoutManager(this)
        binding.recyclerViewProductos.adapter = productosAdapter


        cargarProductos()
    }

    private fun cargarProductos() {
        // Obtener productos desde Firebase
        database.addChildEventListener(object : ChildEventListener {
            override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
                val categoria = snapshot.key ?: return
                snapshot.children.forEach { productSnapshot ->
                    val nombre = productSnapshot.key
                    val precio = productSnapshot.child("precio").getValue(Int::class.java)

                    if (nombre != null && precio != null) {
                        val producto = Producto(nombre, precio, categoria)
                        productosAdapter.addProducto(producto)
                    }
                }
            }

            override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {}
            override fun onChildRemoved(snapshot: DataSnapshot) {}
            override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {}
            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(applicationContext, "Error cargando los productos", Toast.LENGTH_SHORT).show()
            }
        })
    }
}
