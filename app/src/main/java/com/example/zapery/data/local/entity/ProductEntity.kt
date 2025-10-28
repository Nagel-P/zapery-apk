package com.example.zapery.data.local.entidade

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "products",
    indices = [Index(value = ["nome"], unique = false)]
)
data class ProdutoEntidade(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val nome: String,
    val preco: Double,
    val mercadoId: Int,
    val imageUrl: String? = null
)
