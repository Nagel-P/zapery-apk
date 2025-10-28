@file:OptIn(ExperimentalMaterial3Api::class)
package com.example.zapery.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.compose.runtime.remember
import com.example.zapery.viewmodel.AppViewModel
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.foundation.Image
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.Dp
import coil.compose.AsyncImage
import androidx.compose.ui.res.painterResource
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.StarBorder
import kotlinx.coroutines.launch

@Composable
fun TelaProdutos(navController: NavController, viewModel: AppViewModel, mercadoId: Int) {
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()
    Scaffold(
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("Produtos") },
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
            val lista = remember(viewModel.produtos, mercadoId) { viewModel.produtos.filter { it.mercadoId == mercadoId } }
            LazyColumn {
                items(lista) { produto ->
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Row(modifier = Modifier.weight(1f)) {
                                Column(modifier = Modifier.weight(1f)) {
                                    Text(produto.nome, style = MaterialTheme.typography.bodyMedium)
                                    Text("R$ %,.2f".format(produto.preco), style = MaterialTheme.typography.bodySmall)
                                }
                            }
                            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                                IconButton(onClick = {
                                    viewModel.alternarSelecaoRapida(produto.id)
                                    val selected = viewModel.estaSelecionadoRapido(produto.id)
                                    val msg = if (selected) "${produto.nome} adicionado à compra rápida" else "${produto.nome} removido da compra rápida"
                                    scope.launch { snackbarHostState.showSnackbar(msg) }
                                }) {
                                    val selected = viewModel.estaSelecionadoRapido(produto.id)
                                    Icon(
                                        imageVector = if (selected) Icons.Filled.Star else Icons.Outlined.StarBorder,
                                        contentDescription = "Compra rápida"
                                    )
                                }
                                Button(onClick = {
                                    viewModel.adicionarAoCarrinho(produto)
                                    scope.launch { snackbarHostState.showSnackbar("${produto.nome} adicionado ao carrinho") }
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

