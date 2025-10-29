@file:OptIn(ExperimentalMaterial3Api::class)
package com.example.zapery.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.zapery.viewmodel.AppViewModel
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import com.example.zapery.ui.components.PrimaryButton

@Composable
fun TelaConfirmacao(navController: NavController, viewModel: AppViewModel) {
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("Confirmação") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Filled.ArrowBack, contentDescription = "Voltar")
                    }
                }
            )
        }
    ) { padding ->
        Column(modifier = Modifier
            .padding(padding)
            .padding(16.dp)
            .fillMaxWidth()) {
            Text("Resumo da Compra", style = MaterialTheme.typography.titleLarge)
            Spacer(Modifier.height(16.dp))
            viewModel.carrinho.forEach { item ->
                Text("${item.produto.nome} x ${item.quantidade} = R$ ${item.produto.preco * item.quantidade}")
            }
            Spacer(Modifier.height(16.dp))
            Text("Total: R$ ${viewModel.calcularTotal()}", style = MaterialTheme.typography.titleLarge)
            Spacer(Modifier.height(16.dp))
            PrimaryButton(
                text = "Finalizar Compra",
                onClick = {
                    viewModel.finalizarCompra()
                    navController.navigate("pedido_finalizado")
                },
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}

