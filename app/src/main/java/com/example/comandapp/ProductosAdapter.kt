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

    class ProductoViewHolder(private val binding: ItemProductoBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(producto: Producto) {
            binding.productoNombre.text = producto.nombre
            binding.productoPrecio.text = "${producto.precio} CLP"
            var cantidad = producto.cantidad // Asegúrate de que cada producto tenga una cantidad inicial

            // Mostrar la cantidad en el TextView
            binding.tvQuantity.text = cantidad.toString()

            // Deshabilitar el botón "menos" si la cantidad es 0
            binding.botonmenos.isEnabled = cantidad > 0

            // Botón de "más"
            binding.botonmas.setOnClickListener {
                cantidad++
                binding.tvQuantity.text = cantidad.toString()
                binding.botonmenos.isEnabled = cantidad > 0

                // Notificar al adaptador que se ha actualizado la cantidad
                (binding.root.context as ProductosActivity).productosAdapter.actualizarCantidad(adapterPosition, cantidad)
            }

            // Botón de "menos"
            binding.botonmenos.setOnClickListener {
                if (cantidad > 0) {
                    cantidad--
                    binding.tvQuantity.text = cantidad.toString()
                    binding.botonmenos.isEnabled = cantidad > 0

                    // Notificar al adaptador que se ha actualizado la cantidad
                    (binding.root.context as ProductosActivity).productosAdapter.actualizarCantidad(adapterPosition, cantidad)
                }
            }
        }
    }
}

