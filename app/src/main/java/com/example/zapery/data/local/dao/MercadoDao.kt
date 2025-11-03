package com.example.zapery.data.local.acesso_dados

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.zapery.data.local.entidade.MercadoEntidade

@Dao
interface MercadoDao {
    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insert(market: MercadoEntidade): Long

    @Update
    suspend fun update(market: MercadoEntidade)

    @Query("SELECT * FROM markets ORDER BY nome ASC")
    suspend fun getAll(): List<MercadoEntidade>

    @Query("SELECT * FROM markets WHERE id = :id")
    suspend fun findById(id: Int): MercadoEntidade?

    @Query("DELETE FROM markets WHERE id = :id")
    suspend fun deleteById(id: Int)
}
