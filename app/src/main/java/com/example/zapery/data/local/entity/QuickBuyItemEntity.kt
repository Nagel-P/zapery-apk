package com.example.zapery.data.local.entidade

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "quick_buy_items",
    indices = [Index(value = ["userId", "productId"], unique = true), Index("productId")],
    foreignKeys = [
        ForeignKey(
            entity = UsuarioEntidade::class,
            parentColumns = ["id"],
            childColumns = ["userId"],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = ProdutoEntidade::class,
            parentColumns = ["id"],
            childColumns = ["productId"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class CompraRapidaItemEntidade(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val userId: Int,
    val productId: Int
)
