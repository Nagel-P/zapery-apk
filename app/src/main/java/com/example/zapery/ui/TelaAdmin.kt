@file:OptIn(ExperimentalMaterial3Api::class)
package com.example.zapery.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.navigation.NavController
import com.example.zapery.viewmodel.AppViewModel
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.draw.clip
import com.example.zapery.ui.components.PrimaryButton
import com.example.zapery.ui.components.SecondaryButton

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
    var editNome by remember { mutableStateOf("") }
    var editEndereco by remember { mutableStateOf("") }
    var showEditDialog by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(title = { Text("Admin") }, navigationIcon = {
                IconButton(onClick = { navController.popBackStack() }) {
                    Icon(Icons.Filled.ArrowBack, contentDescription = "Voltar")
                }
            })
        }
    ) { padding ->
        LazyColumn(modifier = Modifier.padding(padding).padding(16.dp)) {
            if (presetMarketId == null) {
                item {
                    Text(
                        "Cadastrar Mercado",
                        style = MaterialTheme.typography.titleLarge,
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Center
                    )
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
                    Text(
                        "Produtos",
                        style = MaterialTheme.typography.titleLarge,
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Center
                    )
                    Spacer(Modifier.height(8.dp))
                }

                items(viewModel.produtos.filter { produto -> viewModel.mercados.any { it.id == produto.mercadoId } }) { p ->
                    OutlinedCard(
                        modifier = Modifier.fillMaxWidth().padding(vertical = 6.dp),
                        colors = CardDefaults.cardColors(containerColor = Color(0xFFF8FAFC)),
                        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
                    ) {
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
            } else {
                // Gestão de um mercado específico
                val mercado = viewModel.mercados.find { it.id == presetMarketId }
                item {
                    Text(
                        "Gerenciar Mercado",
                        style = MaterialTheme.typography.titleLarge,
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Center
                    )
                    Spacer(Modifier.height(8.dp))
                    if (mercado != null) {
                        OutlinedCard(
                            modifier = Modifier.fillMaxWidth(),
                            colors = CardDefaults.cardColors(containerColor = Color(0xFFF8FAFC)),
                            elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
                        ) {
                            Column(modifier = Modifier.padding(16.dp)) {
                                Text(mercado.nome, style = MaterialTheme.typography.titleMedium)
                                Spacer(Modifier.height(4.dp))
                                Text(mercado.endereco, style = MaterialTheme.typography.bodySmall)
                                Spacer(Modifier.height(12.dp))
                                Row(horizontalArrangement = Arrangement.spacedBy(12.dp), modifier = Modifier.fillMaxWidth()) {
                                    SecondaryButton(text = "Editar Mercado", onClick = {
                                        editNome = mercado.nome
                                        editEndereco = mercado.endereco
                                        showEditDialog = true
                                    }, modifier = Modifier.weight(1f))
                                    OutlinedButton(onClick = { viewModel.adminDeleteMarket(mercado.id); navController.popBackStack() }, modifier = Modifier.weight(1f)) {
                                        Text("Excluir Mercado")
                                    }
                                }
                            }
                        }
                        Spacer(Modifier.height(16.dp))
                        Text(
                            "Produtos deste mercado",
                            style = MaterialTheme.typography.titleLarge,
                            modifier = Modifier.fillMaxWidth(),
                            textAlign = TextAlign.Center
                        )
                        Spacer(Modifier.height(8.dp))
                    } else {
                        Text("Mercado não encontrado")
                    }
                }
                items(viewModel.produtos.filter { it.mercadoId == presetMarketId }) { p ->
                    OutlinedCard(
                        modifier = Modifier.fillMaxWidth().padding(vertical = 6.dp),
                        colors = CardDefaults.cardColors(containerColor = Color(0xFFF8FAFC)),
                        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
                    ) {
                        Row(modifier = Modifier.fillMaxWidth().padding(12.dp), horizontalArrangement = Arrangement.SpaceBetween) {
                            Column(modifier = Modifier.weight(1f)) {
                                Text(p.nome)
                                Text("R$ %,.2f".format(p.preco), style = MaterialTheme.typography.bodySmall)
                            }
                            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                                OutlinedButton(onClick = { navController.navigate("produto_form/${p.mercadoId}/${p.id}") }) { Text("Editar") }
                                OutlinedButton(onClick = { viewModel.adminDeleteProduct(p.id) }) { Text("Excluir") }
                            }
                        }
                    }
                }

                // Dialog movido para fora do escopo de LazyColumn
            }
        }
        if (showEditDialog) {
            AlertDialog(
                onDismissRequest = { showEditDialog = false },
                title = { Text("Editar Mercado") },
                text = {
                    Column {
                        OutlinedTextField(value = editNome, onValueChange = { editNome = it }, label = { Text("Nome") })
                        Spacer(Modifier.height(8.dp))
                        OutlinedTextField(value = editEndereco, onValueChange = { editEndereco = it }, label = { Text("Endereço") })
                    }
                },
                confirmButton = {
                    PrimaryButton(text = "Salvar", onClick = {
                        val id = presetMarketId
                        if (id != null && editNome.isNotBlank() && editEndereco.isNotBlank()) {
                            viewModel.adminUpdateMarket(id, editNome.trim(), editEndereco.trim())
                            showEditDialog = false
                        }
                    })
                },
                dismissButton = {
                    SecondaryButton(text = "Cancelar", onClick = { showEditDialog = false })
                }
            )
        }
    }
}
