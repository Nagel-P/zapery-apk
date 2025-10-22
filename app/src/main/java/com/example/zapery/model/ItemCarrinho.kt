package com.example.zapery.model

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue

class ItemCarrinho(
    val produto: Produto,
    quantidade: Int = 1
) {
    var quantidade: Int by mutableStateOf(quantidade)
}
