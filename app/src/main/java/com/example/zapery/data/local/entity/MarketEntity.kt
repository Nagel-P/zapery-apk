package com.example.zapery.data.local.entidade

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "markets")
data class MercadoEntidade(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val nome: String,
    val endereco: String
)
