@file:OptIn(ExperimentalMaterial3Api::class)
package com.example.zapery.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavController
import com.example.zapery.model.Usuario
import com.example.zapery.viewmodel.AppViewModel
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.PersonAdd
import kotlinx.coroutines.launch
import com.example.zapery.ui.components.EmailField
import com.example.zapery.ui.components.PasswordField
import com.example.zapery.ui.components.PrimaryButton

@Composable
fun TelaCadastro(navController: NavController, viewModel: AppViewModel) {
    var nome by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var senha by remember { mutableStateOf("") }

    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    Scaffold(
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("Cadastro") },
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
            .fillMaxWidth()) {
            Spacer(Modifier.height(8.dp))
            OutlinedCard(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = Color(0xFFF8FAFC)),
                elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    OutlinedTextField(
                        value = nome,
                        onValueChange = { nome = it },
                        label = { Text("Nome") },
                        modifier = Modifier.fillMaxWidth()
                    )
                    Spacer(Modifier.height(8.dp))
                    EmailField(value = email, onValueChange = { email = it }, modifier = Modifier.fillMaxWidth())
                    Spacer(Modifier.height(8.dp))
                    PasswordField(value = senha, onValueChange = { senha = it }, modifier = Modifier.fillMaxWidth())
                    Spacer(Modifier.height(16.dp))
                    PrimaryButton(text = "Cadastrar", onClick = {
                        if (nome.isNotEmpty() && email.isNotEmpty() && senha.isNotEmpty()) {
                            viewModel.cadastrar(Usuario(viewModel.usuarios.size + 1, nome, email, senha))
                            scope.launch { snackbarHostState.showSnackbar("Cadastro realizado com sucesso!") }
                            navController.navigate("login")
                        } else {
                            scope.launch { snackbarHostState.showSnackbar("Preencha todos os campos") }
                        }
                    }, modifier = Modifier.fillMaxWidth(), leadingIcon = Icons.Filled.PersonAdd, iconContentDescription = "Cadastrar")
                }
            }
        }
    }
}
