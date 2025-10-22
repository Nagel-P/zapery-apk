package com.example.zapery.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import android.widget.Toast
import androidx.compose.ui.platform.LocalContext
import com.example.zapery.viewmodel.AppViewModel
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack

@Composable
fun TelaConfirmacao(navController: NavController, viewModel: AppViewModel) {
    val context = LocalContext.current
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Confirmação") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Filled.ArrowBack, contentDescription = "Voltar")
                    }
                }
            )
        }
    ) { padding ->
        Column(modifier = Modifier.padding(padding).padding(16.dp)) {
            Text("Resumo da Compra", style = MaterialTheme.typography.h6)
            Spacer(Modifier.height(16.dp))
            viewModel.carrinho.forEach { item ->
                Text("${item.produto.nome} x ${item.quantidade} = R$ ${item.produto.preco * item.quantidade}")
            }
            Spacer(Modifier.height(16.dp))
            Text("Total: R$ ${viewModel.calcularTotal()}", style = MaterialTheme.typography.h6)
            Spacer(Modifier.height(16.dp))
            Button(onClick = {
                viewModel.finalizarCompra()
                navController.navigate("pedido_finalizado")
            }) {
                Text("Finalizar Compra")
            }
        }
    }
}

