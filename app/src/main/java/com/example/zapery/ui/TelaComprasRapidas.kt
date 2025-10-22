package com.example.zapery.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
fun TelaComprasRapidas(navController: NavController, viewModel: AppViewModel) {
    val context = LocalContext.current
    val comprasRapidas = listOf("Leite", "Pão", "Ovos")

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Compras Rápidas") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Filled.ArrowBack, contentDescription = "Voltar")
                    }
                }
            )
        }
    ) { padding ->
        Column(modifier = Modifier.padding(padding).padding(16.dp)) {
            Spacer(Modifier.height(16.dp))
            LazyColumn {
                items(comprasRapidas) { nome ->
                    val produto = viewModel.produtos.find { it.nome == nome }
                    if (produto != null) {
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(8.dp),
                            elevation = 4.dp
                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(16.dp),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text(produto.nome, style = MaterialTheme.typography.body1)
                                Button(onClick = {
                                    viewModel.adicionarAoCarrinho(produto)
                                    Toast.makeText(context, "${produto.nome} adicionado ao carrinho", Toast.LENGTH_SHORT).show()
                                }) {
                                    Text("Adicionar")
                                }
                            }
                        }
                    }
                }
            }
            Spacer(Modifier.height(16.dp))
            Button(onClick = { navController.navigate("carrinho") }) {
                Text("Ir para Carrinho")
            }
        }
    }
}

