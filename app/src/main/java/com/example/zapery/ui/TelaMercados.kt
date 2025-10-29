@file:OptIn(ExperimentalMaterial3Api::class)
package com.example.zapery.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.zapery.viewmodel.AppViewModel
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import com.example.zapery.ui.components.PrimaryButton

@Composable
fun TelaMercados(navController: NavController, viewModel: AppViewModel) {
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("Mercados") },
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
            .fillMaxSize()) {
            Text("Selecione um Mercado", style = MaterialTheme.typography.titleLarge)
            Spacer(Modifier.height(16.dp))
            LazyColumn {
                items(viewModel.mercados) { mercado ->
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp),
                    ) {
                        Column(modifier = Modifier.fillMaxWidth().padding(12.dp)) {
                            Text(
                                text = mercado.nome,
                                style = MaterialTheme.typography.bodyMedium,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clickable { navController.navigate("produtos/${mercado.id}") }
                            )
                            if (viewModel.currentUserIsAdmin) {
                                Spacer(Modifier.height(8.dp))
                                PrimaryButton(
                                    text = "Cadastrar Produto neste Mercado",
                                    onClick = { navController.navigate("produto_form/${mercado.id}") }
                                )
                            }
                        }
                    }
                }
            }
            Spacer(Modifier.height(16.dp))
            PrimaryButton(text = "Compras Rápidas", onClick = { navController.navigate("compras_rapidas") })
            if (viewModel.currentUserIsAdmin) {
                Spacer(Modifier.height(8.dp))
                PrimaryButton(text = "Admin", onClick = { navController.navigate("admin") })
            }
        }
    }
}

