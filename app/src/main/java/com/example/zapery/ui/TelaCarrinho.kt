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
import com.example.zapery.ui.components.PrimaryButton
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign

@Composable
fun TelaCarrinho(navController: NavController, viewModel: AppViewModel) {
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("Carrinho") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Filled.ArrowBack, contentDescription = "Voltar")
                    }
                }
            )
        }
    ) { padding ->
        Column(modifier = Modifier.padding(padding).padding(16.dp)) {
            if (viewModel.carrinho.isEmpty()) {
                Text(
                    "Carrinho vazio",
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center
                )
            } else {
                LazyColumn {
                    items(viewModel.carrinho) { item ->
                        OutlinedCard(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(8.dp),
                            shape = RoundedCornerShape(12.dp),
                            colors = CardDefaults.cardColors(containerColor = Color(0xFFF8FAFC)),
                            elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(16.dp),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Row(modifier = Modifier.weight(1f)) {
                                    Column(modifier = Modifier.weight(1f)) {
                                        Text(item.produto.nome, style = MaterialTheme.typography.bodyMedium)
                                        Text(
                                            "R$ %,.2f x ${item.quantidade}".format(item.produto.preco),
                                            style = MaterialTheme.typography.bodySmall
                                        )
                                        Text(
                                            "Subtotal: R$ %,.2f".format(item.produto.preco * item.quantidade),
                                            style = MaterialTheme.typography.bodySmall
                                        )
                                    }
                                }
                                Row {
                                    Button(onClick = { item.quantidade++ }) { Text("+") }
                                    Spacer(Modifier.width(8.dp))
                                    Button(onClick = {
                                        if (item.quantidade > 1) item.quantidade-- else viewModel.removerDoCarrinho(item)
                                    }) { Text("-") }
                                }
                            }
                        }
                    }
                }
                Spacer(Modifier.height(16.dp))
                Text(
                    "Total: R$ %,.2f".format(viewModel.calcularTotal()),
                    style = MaterialTheme.typography.titleLarge,
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center
                )
                Spacer(Modifier.height(16.dp))
                PrimaryButton(
                    text = "Finalizar Compra",
                    onClick = { navController.navigate("confirmacao") },
                    modifier = Modifier.fillMaxWidth(),
                    leadingIcon = Icons.Filled.CheckCircle,
                    iconContentDescription = "Finalizar"
                )
            }
        }
    }
}
