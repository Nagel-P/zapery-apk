@file:OptIn(ExperimentalMaterial3Api::class)
package com.example.zapery.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.zapery.viewmodel.AppViewModel
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.draw.clip
import com.example.zapery.ui.components.PrimaryButton

@Composable
fun TelaAdmin(navController: NavController, viewModel: AppViewModel, presetMarketId: Int?) {
    if (!viewModel.currentUserIsAdmin) {
        Scaffold(topBar = {
            CenterAlignedTopAppBar(title = { Text("Admin") }, navigationIcon = {
                IconButton(onClick = { navController.popBackStack() }) {
                    Icon(Icons.Filled.ArrowBack, contentDescription = "Voltar")
                }
            })
        }) { padding ->
            Box(modifier = Modifier.padding(padding).padding(16.dp)) {
                Text("Acesso restrito a administradores")
            }
        }
        return
    }

    var mercadoNome by remember { mutableStateOf("") }
    var mercadoEndereco by remember { mutableStateOf("") }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(title = { Text("Admin") }, navigationIcon = {
                IconButton(onClick = { navController.popBackStack() }) {
                    Icon(Icons.Filled.ArrowBack, contentDescription = "Voltar")
                }
            })
        }
    ) { padding ->
        val produtos = viewModel.produtos
        LazyColumn(modifier = Modifier.padding(padding).padding(16.dp)) {
            item {
                Text("Cadastrar Mercado", style = MaterialTheme.typography.titleLarge)
                Spacer(Modifier.height(8.dp))
                OutlinedTextField(value = mercadoNome, onValueChange = { mercadoNome = it }, label = { Text("Nome do Mercado") }, modifier = Modifier.fillMaxWidth())
                Spacer(Modifier.height(8.dp))
                OutlinedTextField(value = mercadoEndereco, onValueChange = { mercadoEndereco = it }, label = { Text("Endereço") }, modifier = Modifier.fillMaxWidth())
                Spacer(Modifier.height(8.dp))
                PrimaryButton(
                    text = "Salvar Mercado",
                    onClick = {
                        if (mercadoNome.isNotBlank() && mercadoEndereco.isNotBlank()) {
                            viewModel.adminAddMarket(mercadoNome.trim(), mercadoEndereco.trim())
                            mercadoNome = ""; mercadoEndereco = ""
                        }
                    },
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(Modifier.height(24.dp))
                Divider()
                Spacer(Modifier.height(16.dp))
                Text("Produtos", style = MaterialTheme.typography.titleLarge)
                Spacer(Modifier.height(8.dp))
            }

            items(produtos) { p ->
                Card(modifier = Modifier.fillMaxWidth().padding(vertical = 6.dp)) {
                    Row(modifier = Modifier.fillMaxWidth().padding(12.dp), horizontalArrangement = Arrangement.SpaceBetween) {
                        Column(modifier = Modifier.weight(1f)) {
                            Text(p.nome)
                            Text("R$ %,.2f".format(p.preco), style = MaterialTheme.typography.bodySmall)
                        }
                        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                            OutlinedButton(onClick = {
                                navController.navigate("produto_form/${p.mercadoId}/${p.id}")
                            }) { Text("Editar") }
                            OutlinedButton(onClick = { viewModel.adminDeleteProduct(p.id) }) { Text("Excluir") }
                        }
                    }
                }
            }
        }
    }
}
