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
import androidx.compose.foundation.Image
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.Dp
import coil.compose.AsyncImage

@Composable
fun TelaProdutos(navController: NavController, viewModel: AppViewModel) {
    val context = LocalContext.current
    Scaffold(
        topBar = {
            TopAppBar(
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
            LazyColumn {
                items(viewModel.produtos) { produto ->
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
                                if (produto.imageUrl != null) {
                                    AsyncImage(
                                        model = produto.imageUrl,
                                        contentDescription = produto.nome,
                                        modifier = Modifier
                                            .size(56.dp)
                                            .clip(RoundedCornerShape(8.dp))
                                    )
                                    Spacer(Modifier.width(12.dp))
                                }
                                Column(modifier = Modifier.weight(1f)) {
                                    Text(produto.nome, style = MaterialTheme.typography.body1)
                                    Text("R$ %,.2f".format(produto.preco), style = MaterialTheme.typography.body2)
                                }
                            }
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
            Spacer(Modifier.height(16.dp))
            Button(onClick = { navController.navigate("carrinho") }) {
                Text("Ir para Carrinho")
            }
        }
    }
}

