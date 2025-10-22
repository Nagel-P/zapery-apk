package com.example.zapery.model

data class Produto(
    val id: Int,
    val nome: String,
    val preco: Double,
    val mercadoId: Int,
    val imageUrl: String? = null
)
