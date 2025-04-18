package com.example.comandapp

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.comandapp.databinding.ActivityProductosBinding
import com.google.firebase.database.*

class ProductosActivity : AppCompatActivity() {

    private lateinit var binding: ActivityProductosBinding
    private lateinit var database: DatabaseReference
    lateinit var productosAdapter: ProductosAdapter
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

        // Inicializar Firebase
        database = FirebaseDatabase.getInstance().getReference("productos")
        productosAdapter = ProductosAdapter()
        binding.recyclerViewProductos.layoutManager = LinearLayoutManager(this)
        binding.recyclerViewProductos.adapter = productosAdapter

        // Cargar productos desde Firebase
        cargarProductos()

        // Confirmar pedido y pasar a ResumenPedidoActivity
        binding.btnConfirmarPedido.setOnClickListener {
            val resumen = generarResumenPedido()

            if (resumen.isNotEmpty()) {
                // Lanzar nueva pantalla con resumen
                val intent = Intent(this, ResumenPedidoActivity::class.java)
                intent.putExtra("resumen", resumen)
                intent.putExtra("mesa", mesaSeleccionada)
                startActivity(intent)

                // Guardar en Firebase
                val pedido = mapOf(
                    "mesa" to mesaSeleccionada,
                    "detalle" to resumen,
                    "estado" to "pendiente"
                )

                FirebaseDatabase.getInstance().getReference("pedidos")
                    .push().setValue(pedido)
            } else {
                Toast.makeText(this, "No hay productos seleccionados", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun cargarProductos() {
        Log.d("DEBUG", "Entrando a cargarProductos()...")

        database.addChildEventListener(object : ChildEventListener {
            override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
                val categoria = snapshot.key ?: return
                Log.d("DEBUG", "Categoría encontrada: $categoria")

                snapshot.children.forEach { productSnapshot ->
                    val nombre = productSnapshot.key
                    val precio = productSnapshot.child("precio").getValue(Int::class.java)

                    Log.d("DEBUG", "Producto leído: $nombre - Precio: $precio")

                    if (nombre != null && precio != null) {
                        val producto = Producto(nombre, precio, categoria)
                        productosAdapter.addProducto(producto)
                        Log.d("DEBUG", "Producto agregado al adapter: $producto")
                    }
                }
            }

            override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {}
            override fun onChildRemoved(snapshot: DataSnapshot) {}
            override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {}
            override fun onCancelled(error: DatabaseError) {
                Log.e("DEBUG", "Error en cargarProductos(): ${error.message}")
                Toast.makeText(applicationContext, "Error cargando los productos", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun generarResumenPedido(): String {
        val resumen = StringBuilder()
        for (producto in productosAdapter.obtenerProductos()) {
            if (producto.cantidad > 0) {
                resumen.append("${producto.nombre} x${producto.cantidad} - ${producto.precio * producto.cantidad} CLP\n")
            }
        }
        return resumen.toString().trim()
    }
}
