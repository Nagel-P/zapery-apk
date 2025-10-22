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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.draw.clip
import coil.compose.AsyncImage

@Composable
fun TelaCarrinho(navController: NavController, viewModel: AppViewModel) {
    val context = LocalContext.current
    Scaffold(
        topBar = {
            TopAppBar(
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
                Text("Carrinho vazio")
            } else {
                LazyColumn {
                    items(viewModel.carrinho) { item ->
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(8.dp),
                            elevation = 4.dp,
                            shape = RoundedCornerShape(12.dp)
                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(16.dp),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Row(modifier = Modifier.weight(1f)) {
                                    item.produto.imageUrl?.let { url ->
                                        AsyncImage(
                                            model = url,
                                            contentDescription = item.produto.nome,
                                            modifier = Modifier
                                                .size(56.dp)
                                                .clip(RoundedCornerShape(8.dp))
                                        )
                                        Spacer(Modifier.width(12.dp))
                                    }
                                    Column(modifier = Modifier.weight(1f)) {
                                        Text(item.produto.nome, style = MaterialTheme.typography.body1)
                                        Text(
                                            "R$ %,.2f x ${item.quantidade}".format(item.produto.preco),
                                            style = MaterialTheme.typography.body2
                                        )
                                        Text(
                                            "Subtotal: R$ %,.2f".format(item.produto.preco * item.quantidade),
                                            style = MaterialTheme.typography.body2
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
                Text("Total: R$ %,.2f".format(viewModel.calcularTotal()), style = MaterialTheme.typography.h6)
                Spacer(Modifier.height(16.dp))
                Button(onClick = { navController.navigate("confirmacao") }) {
                    Text("Finalizar Compra")
                }
            }
        }
    }
}

