package com.example.zapery.data.repositorio

import com.example.zapery.data.local.acesso_dados.CompraRapidaDao
import com.example.zapery.data.local.entidade.CompraRapidaItemEntidade

class CompraRapidaRepositorio(private val dao: CompraRapidaDao) {
    suspend fun add(userId: Int, productId: Int): Int = dao.insert(CompraRapidaItemEntidade(userId = userId, productId = productId)).toInt()
    suspend fun remove(userId: Int, productId: Int) = dao.deleteByUserAndProduct(userId, productId)
    suspend fun list(userId: Int) = dao.getByUser(userId)
    suspend fun exists(userId: Int, productId: Int) = dao.exists(userId, productId)
}
