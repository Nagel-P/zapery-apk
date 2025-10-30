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
import androidx.navigation.NavController
import android.widget.Toast
import com.example.zapery.viewmodel.AppViewModel
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.draw.clip
import com.example.zapery.ui.components.PrimaryButton
import com.example.zapery.ui.components.SecondaryButton
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material.icons.filled.CheckCircle

@Composable
fun TelaComprasRapidas(navController: NavController, viewModel: AppViewModel) {
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("Compras Rápidas") },
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
            .fillMaxSize()
            .padding(16.dp)) {
            Spacer(Modifier.height(16.dp))
            val selecionados = remember(viewModel.selecaoRapida, viewModel.produtos) {
                viewModel.produtos.filter { viewModel.selecaoRapida.contains(it.id) }
            }
            if (selecionados.isEmpty()) {
                Box(modifier = Modifier.weight(1f).fillMaxWidth()) {
                    Text("Nenhum produto selecionado. Marque a estrela na lista de produtos para incluir aqui.")
                }
            } else {
                LazyColumn(modifier = Modifier.weight(1f)) {
                    items(selecionados) { produto ->
                    val checked = viewModel.estaSelecionadoRapido(produto.id)
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
                                    Text(produto.nome, style = MaterialTheme.typography.bodyMedium)
                                    Text("R$ %,.2f".format(produto.preco), style = MaterialTheme.typography.bodySmall)
                                }
                            }
                            Checkbox(
                                checked = checked,
                                onCheckedChange = { viewModel.alternarSelecaoRapida(produto.id) }
                            )
                        }
                    }
                    }
                }
            }
            Spacer(Modifier.height(16.dp))
            Row(horizontalArrangement = Arrangement.spacedBy(12.dp), modifier = Modifier.fillMaxWidth()) {
                PrimaryButton(
                    text = "Enviar ao Carrinho",
                    onClick = {
                        viewModel.enviarSelecaoRapidaParaCarrinho()
                        navController.navigate("carrinho")
                    },
                    modifier = Modifier.weight(1f),
                    leadingIcon = Icons.Filled.ShoppingCart,
                    iconContentDescription = "Carrinho"
                )
                SecondaryButton(
                    text = "Finalizar Agora",
                    onClick = {
                        viewModel.enviarSelecaoRapidaParaCarrinho()
                        navController.navigate("confirmacao")
                    },
                    modifier = Modifier.weight(1f),
                    leadingIcon = Icons.Filled.CheckCircle,
                    iconContentDescription = "Finalizar"
                )
            }
        }
    }
}
