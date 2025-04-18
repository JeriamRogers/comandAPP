package com.example.comandapp

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.comandapp.databinding.ItemProductoBinding

class ProductosAdapter : RecyclerView.Adapter<ProductosAdapter.ProductoViewHolder>() {

    private val productosList = mutableListOf<Producto>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductoViewHolder {
        val binding = ItemProductoBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ProductoViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ProductoViewHolder, position: Int) {
        val producto = productosList[position]
        holder.bind(producto)
    }

    override fun getItemCount(): Int = productosList.size

    fun addProducto(producto: Producto) {
        productosList.add(producto)
        notifyItemInserted(productosList.size - 1)
    }

    fun actualizarCantidad(position: Int, cantidad: Int) {
        productosList[position].cantidad = cantidad
        notifyItemChanged(position)
    }

    // ✅ Esta función es la que necesitas para generar el resumen del pedido
    fun obtenerProductos(): List<Producto> {
        return productosList
    }

    class ProductoViewHolder(private val binding: ItemProductoBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(producto: Producto) {
            binding.productoNombre.text = producto.nombre
            binding.productoPrecio.text = "${producto.precio} CLP"
            var cantidad = producto.cantidad

            binding.tvQuantity.text = cantidad.toString()
            binding.botonmenos.isEnabled = cantidad > 0

            binding.botonmas.setOnClickListener {
                cantidad++
                binding.tvQuantity.text = cantidad.toString()
                binding.botonmenos.isEnabled = cantidad > 0
                (binding.root.context as ProductosActivity).productosAdapter.actualizarCantidad(adapterPosition, cantidad)
            }

            binding.botonmenos.setOnClickListener {
                if (cantidad > 0) {
                    cantidad--
                    binding.tvQuantity.text = cantidad.toString()
                    binding.botonmenos.isEnabled = cantidad > 0
                    (binding.root.context as ProductosActivity).productosAdapter.actualizarCantidad(adapterPosition, cantidad)
                }
            }
        }
    }
}
