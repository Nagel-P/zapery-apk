package com.example.zapery.data.local.acesso_dados

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.zapery.data.local.entidade.ProdutoEntidade

@Dao
interface ProdutoDao {
    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insert(product: ProdutoEntidade): Long

    @Update
    suspend fun update(product: ProdutoEntidade)

    @Query("SELECT * FROM products ORDER BY nome ASC")
    suspend fun getAll(): List<ProdutoEntidade>

    @Query("SELECT * FROM products WHERE mercadoId = :marketId ORDER BY nome ASC")
    suspend fun getByMarket(marketId: Int): List<ProdutoEntidade>

    @Query("SELECT * FROM products WHERE id = :id")
    suspend fun findById(id: Int): ProdutoEntidade?

    @Query("DELETE FROM products WHERE id = :id")
    suspend fun deleteById(id: Int)

    @Query("DELETE FROM products WHERE mercadoId = :marketId")
    suspend fun deleteByMarket(marketId: Int)
}
