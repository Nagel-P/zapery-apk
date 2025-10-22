package com.example.zapery.model

data class ItemCarrinho(
    val produto: Produto,
    var quantidade: Int = 1
)
