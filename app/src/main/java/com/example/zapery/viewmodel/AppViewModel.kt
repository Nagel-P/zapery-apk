package com.example.zapery.viewmodel

import androidx.lifecycle.ViewModel
import com.example.zapery.model.*

class AppViewModel : ViewModel() {
    val usuarios = mutableListOf<Usuario>()
    val mercados = mutableListOf<Mercado>()
    val produtos = mutableListOf<Produto>()
    val carrinho = mutableListOf<ItemCarrinho>()

    init {
        // Mock de dados
        mercados.add(Mercado(1, "Mercado Central", "Rua A, 123"))
        produtos.add(Produto(1, "Arroz 5kg", 25.99, 1))
        produtos.add(Produto(2, "Feijão 1kg", 7.50, 1))
    }

    fun cadastrar(usuario: Usuario) {
        usuarios.add(usuario)
    }

    fun adicionarAoCarrinho(produto: Produto) {
        val existente = carrinho.find { it.produto.id == produto.id }
        if (existente != null) {
            existente.quantidade++
        } else {
            carrinho.add(ItemCarrinho(produto, 1))
        }
    }

    fun removerDoCarrinho(item: ItemCarrinho) {
        carrinho.remove(item)
    }

    fun calcularTotal(): Double {
        return carrinho.sumOf { it.produto.preco * it.quantidade }
    }

    fun finalizarCompra() {
        carrinho.clear()
    }
}
