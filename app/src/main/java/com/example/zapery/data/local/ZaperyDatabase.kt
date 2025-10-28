package com.example.zapery.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.zapery.data.local.acesso_dados.ItemCarrinhoDao
import com.example.zapery.data.local.acesso_dados.MercadoDao
import com.example.zapery.data.local.acesso_dados.ProdutoDao
import com.example.zapery.data.local.acesso_dados.CompraRapidaDao
import com.example.zapery.data.local.acesso_dados.UsuarioDao
import com.example.zapery.data.local.entidade.ItemCarrinhoEntidade
import com.example.zapery.data.local.entidade.MercadoEntidade
import com.example.zapery.data.local.entidade.ProdutoEntidade
import com.example.zapery.data.local.entidade.CompraRapidaItemEntidade
import com.example.zapery.data.local.entidade.UsuarioEntidade

@Database(
    entities = [
        UsuarioEntidade::class,
        MercadoEntidade::class,
        ProdutoEntidade::class,
        ItemCarrinhoEntidade::class,
        CompraRapidaItemEntidade::class
    ],
    version = 1,
    exportSchema = false
)
abstract class ZaperyDatabase : RoomDatabase() {
    abstract fun usuarioDao(): UsuarioDao
    abstract fun mercadoDao(): MercadoDao
    abstract fun produtoDao(): ProdutoDao
    abstract fun itemCarrinhoDao(): ItemCarrinhoDao
    abstract fun compraRapidaDao(): CompraRapidaDao
}
