package com.example.zapery.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.zapery.data.repositorio.CarrinhoRepositorio
import com.example.zapery.data.repositorio.MercadoRepositorio
import com.example.zapery.data.repositorio.ProdutoRepositorio
import com.example.zapery.data.repositorio.CompraRapidaRepositorio
import com.example.zapery.data.repositorio.UsuarioRepositorio

class AppViewModelFactory(
    private val userRepo: UsuarioRepositorio,
    private val marketRepo: MercadoRepositorio,
    private val productRepo: ProdutoRepositorio,
    private val cartRepo: CarrinhoRepositorio,
    private val quickRepo: CompraRapidaRepositorio
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AppViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return AppViewModel(userRepo, marketRepo, productRepo, cartRepo, quickRepo) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
