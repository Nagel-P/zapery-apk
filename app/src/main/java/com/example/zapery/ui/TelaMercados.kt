package com.example.zapery.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.zapery.viewmodel.AppViewModel
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack

@Composable
fun TelaMercados(navController: NavController, viewModel: AppViewModel) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Mercados") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Filled.ArrowBack, contentDescription = "Voltar")
                    }
                }
            )
        }
    ) { padding ->
        Column(modifier = Modifier.padding(padding).padding(16.dp)) {
            Text("Selecione um Mercado", style = MaterialTheme.typography.h6)
            Spacer(Modifier.height(16.dp))
            LazyColumn {
                items(viewModel.mercados) { mercado ->
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp)
                            .clickable { navController.navigate("produtos/${mercado.id}") },
                        elevation = 4.dp
                    ) {
                        Text(
                            text = mercado.nome,
                            modifier = Modifier.padding(16.dp),
                            style = MaterialTheme.typography.body1
                        )
                    }
                }
            }
            Spacer(Modifier.height(16.dp))
            Button(onClick = { navController.navigate("compras_rapidas") }) {
                Text("Compras Rápidas")
            }
        }
    }
}
