package com.example.zapery.data.repositorio

import com.example.zapery.data.local.acesso_dados.MercadoDao
import com.example.zapery.data.local.entidade.MercadoEntidade

class MercadoRepositorio(private val dao: MercadoDao) {
    suspend fun insert(market: MercadoEntidade): Int = dao.insert(market).toInt()
    suspend fun update(market: MercadoEntidade) = dao.update(market)
    suspend fun getAll(): List<MercadoEntidade> = dao.getAll()
    suspend fun findById(id: Int): MercadoEntidade? = dao.findById(id)
    suspend fun deleteById(id: Int) = dao.deleteById(id)
}
