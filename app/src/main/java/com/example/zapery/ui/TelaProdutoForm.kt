@file:OptIn(ExperimentalMaterial3Api::class)
package com.example.zapery.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
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
fun TelaProdutoForm(
    navController: NavController,
    viewModel: AppViewModel,
    marketId: Int,
    productId: Int?
) {
    val isEdit = productId != null
    val mercado = viewModel.mercados.find { it.id == marketId }
    var nome by remember { mutableStateOf("") }
    var precoText by remember { mutableStateOf("") }

    LaunchedEffect(productId) {
        if (isEdit) {
            val p = viewModel.produtos.find { it.id == productId }
            if (p != null) {
                nome = p.nome
                precoText = p.preco.toString()
            }
        }
    }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text(if (isEdit) "Editar Produto" else "Cadastrar Produto") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Filled.ArrowBack, contentDescription = "Voltar")
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .padding(16.dp)
                .verticalScroll(rememberScrollState())
        ) {
            Text("Mercado", style = MaterialTheme.typography.titleSmall)
            Spacer(Modifier.height(4.dp))
            Text(mercado?.nome ?: "Mercado #$marketId", style = MaterialTheme.typography.bodyMedium)
            Spacer(Modifier.height(16.dp))

            OutlinedTextField(
                value = nome,
                onValueChange = { nome = it },
                label = { Text("Nome do Produto") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(Modifier.height(12.dp))

            OutlinedTextField(
                value = precoText,
                onValueChange = { precoText = it.filter { ch -> ch.isDigit() || ch == '.' || ch == ',' }.replace(',', '.') },
                label = { Text("Preço (ex: 12.34)") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(Modifier.height(24.dp))

            Row(horizontalArrangement = Arrangement.spacedBy(12.dp), modifier = Modifier.fillMaxWidth()) {
                PrimaryButton(
                    text = if (isEdit) "Salvar Alterações" else "Cadastrar",
                    onClick = {
                        val preco = precoText.toDoubleOrNull()
                        if (nome.isNotBlank() && preco != null) {
                            if (isEdit && productId != null) {
                                viewModel.adminUpdateProduct(productId, marketId, nome.trim(), preco, null)
                            } else {
                                viewModel.adminAddProduct(marketId, nome.trim(), preco, null)
                            }
                            navController.popBackStack()
                        }
                    },
                    modifier = Modifier.weight(1f)
                )

                if (isEdit && productId != null) {
                    OutlinedButton(onClick = {
                        viewModel.adminDeleteProduct(productId)
                        navController.popBackStack()
                    }, modifier = Modifier.weight(1f)) {
                        Text("Excluir")
                    }
                }
            }
        }
    }
}

