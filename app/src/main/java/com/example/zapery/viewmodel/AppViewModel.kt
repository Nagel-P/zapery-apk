package com.example.zapery.viewmodel

import androidx.lifecycle.ViewModel
import com.example.zapery.model.*
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.viewModelScope
import com.example.zapery.data.local.entidade.UsuarioEntidade
import com.example.zapery.data.local.entidade.MercadoEntidade
import com.example.zapery.data.local.entidade.ProdutoEntidade
import com.example.zapery.data.local.entidade.ItemCarrinhoEntidade
import com.example.zapery.data.repositorio.UsuarioRepositorio
import com.example.zapery.data.repositorio.MercadoRepositorio
import com.example.zapery.data.repositorio.ProdutoRepositorio
import com.example.zapery.data.repositorio.CarrinhoRepositorio
import com.example.zapery.data.repositorio.CompraRapidaRepositorio
import kotlinx.coroutines.launch

class AppViewModel(
    private val userRepo: UsuarioRepositorio,
    private val marketRepo: MercadoRepositorio,
    private val productRepo: ProdutoRepositorio,
    private val cartRepo: CarrinhoRepositorio,
    private val quickRepo: CompraRapidaRepositorio
) : ViewModel() {
    companion object {
        private const val ADMIN_EMAIL = "admin@zapery.local"
    }
    val usuarios = mutableStateListOf<Usuario>()
    val mercados = mutableStateListOf<Mercado>()
    val produtos = mutableStateListOf<Produto>()
    val carrinho = mutableStateListOf<ItemCarrinho>()
    val selecaoRapida = mutableStateListOf<Int>()

    var currentUserId: Int? = null
    var currentUserIsAdmin: Boolean = false

    init {
        viewModelScope.launch {
            seedIfNeeded()
            loadAll()
        }
    }

    fun cadastrar(usuario: Usuario) {
        viewModelScope.launch {
            val newId = userRepo.register(
                UsuarioEntidade(
                    nome = usuario.nome,
                    email = usuario.email,
                    senha = usuario.senha,
                    isAdmin = usuario.email.equals(ADMIN_EMAIL, ignoreCase = true)
                )
            )
            usuarios.add(usuario.copy(id = newId))
        }
    }

    fun adicionarAoCarrinho(produto: Produto) {
        val existente = carrinho.find { it.produto.id == produto.id }
        if (existente != null) {
            existente.quantidade++
            viewModelScope.launch {
                cartRepo.findByUserAndProduct(currentUserId, produto.id)?.let { found ->
                    cartRepo.update(found.copy(quantidade = existente.quantidade))
                }
            }
        } else {
            carrinho.add(ItemCarrinho(produto, 1))
            viewModelScope.launch {
                cartRepo.upsert(
                    ItemCarrinhoEntidade(
                        userId = currentUserId,
                        productId = produto.id,
                        quantidade = 1
                    )
                )
            }
        }
    }

    fun removerDoCarrinho(item: ItemCarrinho) {
        carrinho.remove(item)
        viewModelScope.launch {
            cartRepo.findByUserAndProduct(currentUserId, item.produto.id)?.let { found ->
                cartRepo.deleteById(found.id)
            }
        }
    }

    fun calcularTotal(): Double {
        return carrinho.sumOf { it.produto.preco * it.quantidade }
    }

    fun finalizarCompra() {
        carrinho.clear()
        viewModelScope.launch { cartRepo.clearAll() }
    }

    fun autenticar(email: String, senha: String): Boolean {
        if (email.isBlank() || senha.isBlank()) return false
        val user = usuarios.find { it.email.equals(email.trim(), ignoreCase = true) && it.senha == senha }
        currentUserId = user?.id
        if (currentUserId != null) {
            viewModelScope.launch {
                currentUserIsAdmin = userRepo.findById(currentUserId!!)?.isAdmin == true
                loadQuickForUser()
                loadCartForUser()
            }
        }
        return user != null
    }

    fun alternarSelecaoRapida(produtoId: Int) {
        if (selecaoRapida.contains(produtoId)) {
            selecaoRapida.remove(produtoId)
            currentUserId?.let { uid ->
                viewModelScope.launch { quickRepo.remove(uid, produtoId) }
            }
        } else {
            selecaoRapida.add(produtoId)
            currentUserId?.let { uid ->
                viewModelScope.launch { quickRepo.add(uid, produtoId) }
            }
        }
    }

    fun estaSelecionadoRapido(produtoId: Int): Boolean = selecaoRapida.contains(produtoId)

    fun enviarSelecaoRapidaParaCarrinho() {
        val selecionados = produtos.filter { selecaoRapida.contains(it.id) }
        selecionados.forEach { adicionarAoCarrinho(it) }
    }

    private suspend fun seedIfNeeded() {
        if (marketRepo.getAll().isEmpty()) {
            marketRepo.insert(MercadoEntidade(nome = "Mercado Central", endereco = "Rua A, 123"))
        }
    }

    private suspend fun loadAll() {
        usuarios.clear()
        usuarios.addAll(userRepo.all().map { Usuario(it.id, it.nome, it.email, it.senha) })
        mercados.clear()
        mercados.addAll(marketRepo.getAll().map { Mercado(it.id, it.nome, it.endereco) })
        produtos.clear()
        produtos.addAll(productRepo.getAll().map { Produto(it.id, it.nome, it.preco, it.mercadoId, it.imageUrl) })
    }

    fun adminAddMarket(nome: String, endereco: String) {
        if (!currentUserIsAdmin) return
        viewModelScope.launch {
            marketRepo.insert(MercadoEntidade(nome = nome, endereco = endereco))
            loadAll()
        }
    }

    fun adminUpdateMarket(id: Int, nome: String, endereco: String) {
        if (!currentUserIsAdmin) return
        viewModelScope.launch {
            marketRepo.update(MercadoEntidade(id = id, nome = nome, endereco = endereco))
            loadAll()
        }
    }

    fun adminDeleteMarket(id: Int) {
        if (!currentUserIsAdmin) return
        viewModelScope.launch {
            productRepo.deleteByMarket(id)
            marketRepo.deleteById(id)
            loadAll()
            loadQuickForUser()
            loadCartForUser()
        }
    }

    fun adminAddProduct(mercadoId: Int, nome: String, preco: Double, imageUrl: String?) {
        if (!currentUserIsAdmin) return
        viewModelScope.launch {
            productRepo.insert(ProdutoEntidade(nome = nome, preco = preco, mercadoId = mercadoId, imageUrl = imageUrl))
            loadAll()
        }
    }

    fun adminUpdateProduct(id: Int, mercadoId: Int, nome: String, preco: Double, imageUrl: String?) {
        if (!currentUserIsAdmin) return
        viewModelScope.launch {
            productRepo.update(ProdutoEntidade(id = id, nome = nome, preco = preco, mercadoId = mercadoId, imageUrl = imageUrl))
            loadAll()
        }
    }

    fun adminDeleteProduct(id: Int) {
        if (!currentUserIsAdmin) return
        viewModelScope.launch {
            productRepo.deleteById(id)
            loadAll()
        }
    }

    private suspend fun loadQuickForUser() {
        selecaoRapida.clear()
        currentUserId?.let { uid ->
            selecaoRapida.addAll(quickRepo.list(uid).map { it.productId })
        }
    }

    private suspend fun loadCartForUser() {
        carrinho.clear()
        val uid = currentUserId
        val items = if (uid == null) cartRepo.getGuestCart() else cartRepo.getUserCart(uid)
        val produtoMap = produtos.associateBy { it.id }
        items.forEach { ci ->
            produtoMap[ci.productId]?.let { p -> carrinho.add(ItemCarrinho(p, ci.quantidade)) }
        }
    }
}


