package com.example.zapery.data.repositorio

import com.example.zapery.data.local.acesso_dados.ItemCarrinhoDao
import com.example.zapery.data.local.entidade.ItemCarrinhoEntidade

class CarrinhoRepositorio(private val dao: ItemCarrinhoDao) {
    suspend fun upsert(item: ItemCarrinhoEntidade): Int = dao.upsert(item).toInt()
    suspend fun update(item: ItemCarrinhoEntidade) = dao.update(item)
    suspend fun deleteById(id: Int) = dao.deleteById(id)
    suspend fun clearAll() = dao.clearAll()
    suspend fun getGuestCart(): List<ItemCarrinhoEntidade> = dao.getGuestCart()
    suspend fun getUserCart(userId: Int): List<ItemCarrinhoEntidade> = dao.getUserCart(userId)
    suspend fun findByUserAndProduct(userId: Int?, productId: Int): ItemCarrinhoEntidade? = dao.findByUserAndProduct(userId, productId)
}
