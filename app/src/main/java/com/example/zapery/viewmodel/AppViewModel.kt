package com.example.zapery.viewmodel

import androidx.lifecycle.ViewModel
import com.example.zapery.model.*
import androidx.compose.runtime.mutableStateListOf

class AppViewModel : ViewModel() {
    val usuarios = mutableStateListOf<Usuario>()
    val mercados = mutableStateListOf<Mercado>()
    val produtos = mutableStateListOf<Produto>()
    val carrinho = mutableStateListOf<ItemCarrinho>()

    // Seleção de Compras Rápidas (IDs de produtos salvos)
    val selecaoRapida = mutableStateListOf<Int>()

    init {
        // Mock de dados
        mercados.add(Mercado(1, "Mercado Central", "Rua A, 123"))
        produtos.add(Produto(1, "Arroz 5kg", 25.99, 1, imageUrl = "https://images.unsplash.com/photo-1604908812240-8aabbd2c1eab"))
        produtos.add(Produto(2, "Feijão 1kg", 7.50, 1, imageUrl = "https://images.unsplash.com/photo-1604908177241-1b5ea7f62035"))
        produtos.add(Produto(3, "Leite 1L", 5.39, 1, imageUrl = "https://images.unsplash.com/photo-1582719478250-c89cae4dc85b"))
        produtos.add(Produto(4, "Pão Francês (kg)", 14.90, 1, imageUrl = "https://images.unsplash.com/photo-1549931319-4f1b6c7a9a2c"))
        // Seleção inicial opcional
        selecaoRapida.addAll(listOf(3, 4))
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

    fun autenticar(email: String, senha: String): Boolean {
        if (email.isBlank() || senha.isBlank()) return false
        return usuarios.any { it.email.equals(email.trim(), ignoreCase = true) && it.senha == senha }
    }

    // Compras rápidas
    fun alternarSelecaoRapida(produtoId: Int) {
        if (selecaoRapida.contains(produtoId)) selecaoRapida.remove(produtoId) else selecaoRapida.add(produtoId)
    }

    fun estaSelecionadoRapido(produtoId: Int): Boolean = selecaoRapida.contains(produtoId)

    fun enviarSelecaoRapidaParaCarrinho() {
        val selecionados = produtos.filter { selecaoRapida.contains(it.id) }
        selecionados.forEach { adicionarAoCarrinho(it) }
    }
}

