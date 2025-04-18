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

    class ProductoViewHolder(private val binding: ItemProductoBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(producto: Producto) {
            binding.productoNombre.text = producto.nombre
            binding.productoPrecio.text = "${producto.precio} CLP"
        }
    }
}
