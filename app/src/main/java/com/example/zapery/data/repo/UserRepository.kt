package com.example.zapery.data.repositorio

import com.example.zapery.data.local.acesso_dados.UsuarioDao
import com.example.zapery.data.local.entidade.UsuarioEntidade

class UsuarioRepositorio(private val dao: UsuarioDao) {
    suspend fun register(user: UsuarioEntidade): Int = dao.insert(user).toInt()
    suspend fun update(user: UsuarioEntidade) = dao.update(user)
    suspend fun findByEmail(email: String): UsuarioEntidade? = dao.findByEmail(email)
    suspend fun findById(id: Int): UsuarioEntidade? = dao.findById(id)
    suspend fun all(): List<UsuarioEntidade> = dao.getAll()
}
