package com.example.comandapp

data class Pedido(
    val id: String = "",
    val mesa: String = "",
    val detalle: String = "",
    var estado: String = ""
)
