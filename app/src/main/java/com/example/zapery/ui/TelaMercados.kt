@file:OptIn(ExperimentalMaterial3Api::class)
package com.example.zapery.ui

import androidx.compose.foundation.clickable
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
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.Settings
import com.example.zapery.ui.components.PrimaryButton
import com.example.zapery.ui.components.SecondaryButton
import kotlinx.coroutines.launch
import androidx.compose.material3.AssistChip
import androidx.compose.material3.AssistChipDefaults
import androidx.compose.ui.graphics.Color
import java.util.Calendar

@Composable
fun TelaMercados(navController: NavController, viewModel: AppViewModel) {
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()
    var query by remember { mutableStateOf("") }
    var sortExpanded by remember { mutableStateOf(false) }
    var sortOption by remember { mutableStateOf("Nome (A-Z)") }

    Scaffold(
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("Mercados") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Filled.ArrowBack, contentDescription = "Voltar")
                    }
                }
            )
        },
        floatingActionButton = {
            if (viewModel.currentUserIsAdmin) {
                ExtendedFloatingActionButton(onClick = { navController.navigate("admin") }) {
                    Icon(Icons.Filled.Add, contentDescription = null)
                    Spacer(Modifier.width(8.dp))
                    Text("Cadastrar Mercado")
                }
            }
        }
    ) { padding ->
        Column(modifier = Modifier
            .padding(padding)
            .padding(16.dp)
            .fillMaxSize()) {
            Text("Selecione um Mercado", style = MaterialTheme.typography.titleLarge)
            Spacer(Modifier.height(16.dp))

            // Busca e ordenação (em Card para melhor organização visual)
            OutlinedCard(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = Color(0xFFF8FAFC)),
                elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
            ) {
                Column(modifier = Modifier.padding(12.dp)) {
                    OutlinedTextField(
                        value = query,
                        onValueChange = { query = it },
                        label = { Text("Buscar por nome ou endereço") },
                        leadingIcon = { Icon(Icons.Filled.Search, contentDescription = null) },
                        trailingIcon = {
                            if (query.isNotBlank()) {
                                IconButton(onClick = { query = "" }) {
                                    Icon(Icons.Filled.Close, contentDescription = "Limpar busca")
                                }
                            }
                        },
                        modifier = Modifier.fillMaxWidth()
                    )
                    Spacer(Modifier.height(8.dp))
                    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        AssistChip(
                            onClick = { sortOption = "Nome (A-Z)" },
                            label = { Text("Nome (A-Z)") },
                            colors = AssistChipDefaults.assistChipColors(
                                containerColor = if (sortOption == "Nome (A-Z)") MaterialTheme.colorScheme.primary.copy(alpha = 0.12f) else MaterialTheme.colorScheme.surface,
                                labelColor = MaterialTheme.colorScheme.onSurface
                            )
                        )
                        AssistChip(
                            onClick = { sortOption = "Nome (Z-A)" },
                            label = { Text("Nome (Z-A)") },
                            colors = AssistChipDefaults.assistChipColors(
                                containerColor = if (sortOption == "Nome (Z-A)") MaterialTheme.colorScheme.primary.copy(alpha = 0.12f) else MaterialTheme.colorScheme.surface,
                                labelColor = MaterialTheme.colorScheme.onSurface
                            )
                        )
                        AssistChip(
                            onClick = {
                                scope.launch { snackbarHostState.showSnackbar("Ordenação por proximidade requer latitude/longitude.") }
                            },
                            label = { Text("Proximidade") }
                        )
                    }
                }
            }
            Spacer(Modifier.height(12.dp))

            val filtered = remember(viewModel.mercados, query) {
                viewModel.mercados.filter { m ->
                    val q = query.trim().lowercase()
                    q.isBlank() || m.nome.lowercase().contains(q) || m.endereco.lowercase().contains(q)
                }
            }
            val ordered = remember(filtered, sortOption) {
                when (sortOption) {
                    "Nome (Z-A)" -> filtered.sortedByDescending { it.nome }
                    else -> filtered.sortedBy { it.nome }
                }
            }
            LazyColumn {
                items(ordered) { mercado ->
                    OutlinedCard(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp)
                            .clickable { navController.navigate("produtos/${mercado.id}") },
                        colors = CardDefaults.cardColors(containerColor = Color(0xFFF8FAFC)),
                        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
                    ) {
                        Column(modifier = Modifier.fillMaxWidth().padding(12.dp)) {
                            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                                Column(modifier = Modifier.weight(1f)) {
                                    Text(
                                        text = mercado.nome,
                                        style = MaterialTheme.typography.titleMedium,
                                        modifier = Modifier.fillMaxWidth()
                                    )
                                    Spacer(Modifier.height(2.dp))
                                    Text(mercado.endereco, style = MaterialTheme.typography.bodySmall)
                                }
                                val open = remember { isMarketOpen() }
                                AssistChip(
                                    onClick = {},
                                    label = { Text(if (open) "Aberto agora" else "Fechado") },
                                    colors = AssistChipDefaults.assistChipColors(
                                        containerColor = if (open) Color(0xFFE6F4EA) else Color(0xFFFCE8E6),
                                        labelColor = if (open) Color(0xFF1E7A34) else Color(0xFFB3261E)
                                    )
                                )
                            }
                            if (viewModel.currentUserIsAdmin) {
                                Spacer(Modifier.height(12.dp))
                                Row(horizontalArrangement = Arrangement.spacedBy(12.dp), modifier = Modifier.fillMaxWidth()) {
                                    SecondaryButton(
                                        text = "Gerenciar Mercado",
                                        onClick = { navController.navigate("admin/${mercado.id}") },
                                        modifier = Modifier.weight(1f),
                                        compact = true,
                                        leadingIcon = Icons.Filled.Edit,
                                        iconContentDescription = "Editar"
                                    )
                                    PrimaryButton(
                                        text = "Cadastrar Produto",
                                        onClick = { navController.navigate("produto_form/${mercado.id}") },
                                        modifier = Modifier.weight(1f),
                                        compact = true,
                                        leadingIcon = Icons.Filled.Add,
                                        iconContentDescription = "Adicionar"
                                    )
                                }
                            }
                        }
                    }
                }
            }
            Spacer(Modifier.height(16.dp))
            PrimaryButton(
                text = "Compras Rápidas",
                onClick = { navController.navigate("compras_rapidas") },
                leadingIcon = Icons.Filled.Star,
                iconContentDescription = "Compras Rápidas"
            )
            if (viewModel.currentUserIsAdmin) {
                Spacer(Modifier.height(8.dp))
                PrimaryButton(
                    text = "Área do Administrador",
                    onClick = { navController.navigate("admin") },
                    leadingIcon = Icons.Filled.Settings,
                    iconContentDescription = "Admin"
                )
            }
        }
    }
}

private fun isMarketOpen(): Boolean {
    val hour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY) // 0..23
    return hour in 8..21 // Aberto 08:00 até 21:59
}

