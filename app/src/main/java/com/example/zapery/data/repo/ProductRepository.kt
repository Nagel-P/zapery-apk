package com.example.zapery.data.repositorio

import com.example.zapery.data.local.acesso_dados.ProdutoDao
import com.example.zapery.data.local.entidade.ProdutoEntidade

class ProdutoRepositorio(private val dao: ProdutoDao) {
    suspend fun insert(produto: ProdutoEntidade): Int = dao.insert(produto).toInt()
    suspend fun update(produto: ProdutoEntidade) = dao.update(produto)
    suspend fun getAll(): List<ProdutoEntidade> = dao.getAll()
    suspend fun getByMarket(marketId: Int): List<ProdutoEntidade> = dao.getByMarket(marketId)
    suspend fun findById(id: Int): ProdutoEntidade? = dao.findById(id)
    suspend fun deleteById(id: Int) = dao.deleteById(id)
}
