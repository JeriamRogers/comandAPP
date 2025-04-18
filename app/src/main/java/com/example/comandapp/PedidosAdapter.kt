package com.example.comandapp

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.comandapp.databinding.ItemPedidoBinding
import com.google.firebase.database.FirebaseDatabase

class PedidosAdapter : RecyclerView.Adapter<PedidosAdapter.PedidoViewHolder>() {

    private val pedidosList = mutableListOf<Pedido>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PedidoViewHolder {
        val binding = ItemPedidoBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PedidoViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PedidoViewHolder, position: Int) {
        val pedido = pedidosList[position]
        holder.bind(pedido)
    }

    override fun getItemCount(): Int = pedidosList.size

    fun setPedidos(pedidos: List<Pedido>) {
        pedidosList.clear()
        pedidosList.addAll(pedidos)
        notifyDataSetChanged()
    }

    inner class PedidoViewHolder(private val binding: ItemPedidoBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(pedido: Pedido) {
            binding.tvMesaPedido.text = "Mesa: ${pedido.mesa}"
            binding.tvDetallePedido.text = pedido.detalle

            binding.btnListo.setOnClickListener {
                // cambia en fb el estado a listo"
                val ref = FirebaseDatabase.getInstance().getReference("pedidos").child(pedido.id)
                ref.child("estado").setValue("listo")
            }
        }
    }
}
