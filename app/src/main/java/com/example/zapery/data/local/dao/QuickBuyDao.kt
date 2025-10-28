package com.example.zapery.data.local.acesso_dados

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Delete
import com.example.zapery.data.local.entidade.CompraRapidaItemEntidade

@Dao
interface CompraRapidaDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(item: CompraRapidaItemEntidade): Long

    @Delete
    suspend fun delete(item: CompraRapidaItemEntidade)

    @Query("DELETE FROM quick_buy_items WHERE userId = :userId AND productId = :productId")
    suspend fun deleteByUserAndProduct(userId: Int, productId: Int)

    @Query("SELECT * FROM quick_buy_items WHERE userId = :userId")
    suspend fun getByUser(userId: Int): List<CompraRapidaItemEntidade>

    @Query("SELECT EXISTS(SELECT 1 FROM quick_buy_items WHERE userId = :userId AND productId = :productId)")
    suspend fun exists(userId: Int, productId: Int): Boolean
}
