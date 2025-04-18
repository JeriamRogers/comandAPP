package com.example.comandapp

data class Producto(
    val nombre: String,
    val precio: Int,
    val categoria: String,
    var cantidad: Int = 0
)
