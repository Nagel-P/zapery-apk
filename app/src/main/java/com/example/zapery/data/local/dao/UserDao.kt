package com.example.zapery.data.local.acesso_dados

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.zapery.data.local.entidade.UsuarioEntidade

@Dao
interface UsuarioDao {
    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insert(user: UsuarioEntidade): Long

    @Update
    suspend fun update(user: UsuarioEntidade)

    @Query("SELECT * FROM users WHERE email = :email LIMIT 1")
    suspend fun findByEmail(email: String): UsuarioEntidade?

    @Query("SELECT * FROM users WHERE id = :id")
    suspend fun findById(id: Int): UsuarioEntidade?

    @Query("SELECT * FROM users")
    suspend fun getAll(): List<UsuarioEntidade>

    @Query("UPDATE users SET isAdmin = CASE WHEN email = :email THEN 1 ELSE 0 END")
    suspend fun setOnlyAdmin(email: String)
}
