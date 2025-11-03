package com.example.zapery.data.local.acesso_dados

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.zapery.data.local.entidade.ItemCarrinhoEntidade

@Dao
interface ItemCarrinhoDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(item: ItemCarrinhoEntidade): Long

    @Update
    suspend fun update(item: ItemCarrinhoEntidade)

    @Query("DELETE FROM cart_items WHERE id = :id")
    suspend fun deleteById(id: Int)

    @Query("DELETE FROM cart_items")
    suspend fun clearAll()

    @Query("SELECT * FROM cart_items WHERE userId IS NULL")
    suspend fun getGuestCart(): List<ItemCarrinhoEntidade>

    @Query("SELECT * FROM cart_items WHERE userId = :userId")
    suspend fun getUserCart(userId: Int): List<ItemCarrinhoEntidade>

    @Query("SELECT * FROM cart_items WHERE userId = :userId AND productId = :productId LIMIT 1")
    suspend fun findByUserAndProduct(userId: Int?, productId: Int): ItemCarrinhoEntidade?
}
