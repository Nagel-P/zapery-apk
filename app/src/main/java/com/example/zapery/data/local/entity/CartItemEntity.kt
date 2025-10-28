package com.example.zapery.data.local.entidade

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import androidx.room.Index

@Entity(
    tableName = "cart_items",
    indices = [Index("userId"), Index("productId")],
    foreignKeys = [
        ForeignKey(
            entity = ProdutoEntidade::class,
            parentColumns = ["id"],
            childColumns = ["productId"],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = UsuarioEntidade::class,
            parentColumns = ["id"],
            childColumns = ["userId"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class ItemCarrinhoEntidade(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val userId: Int?,
    val productId: Int,
    val quantidade: Int
)
