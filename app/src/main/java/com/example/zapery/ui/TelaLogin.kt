@file:OptIn(ExperimentalMaterial3Api::class)
package com.example.zapery.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.zapery.viewmodel.AppViewModel
import kotlinx.coroutines.launch
import com.example.zapery.ui.components.EmailField
import com.example.zapery.ui.components.PasswordField
import com.example.zapery.ui.components.PrimaryButton
import com.example.zapery.ui.components.SecondaryButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Login
import androidx.compose.material.icons.filled.PersonAdd

@Composable
fun TelaLogin(navController: NavController, viewModel: AppViewModel) {
    var email by remember { mutableStateOf("") }
    var senha by remember { mutableStateOf("") }
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    Scaffold(
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text(text = "Entrar", style = MaterialTheme.typography.titleLarge) }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(20.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = "Bem-vindo ao Zapery", style = MaterialTheme.typography.headlineSmall)
            Spacer(Modifier.height(20.dp))

            EmailField(
                value = email,
                onValueChange = { email = it },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(Modifier.height(12.dp))
            PasswordField(
                value = senha,
                onValueChange = { senha = it },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(Modifier.height(16.dp))
            PrimaryButton(
                text = "Entrar",
                onClick = {
                    if (viewModel.autenticar(email, senha)) {
                        navController.navigate("mercados")
                    } else {
                        scope.launch { snackbarHostState.showSnackbar("Email ou senha inválidos") }
                    }
                },
                modifier = Modifier.fillMaxWidth(),
                leadingIcon = Icons.Filled.Login,
                iconContentDescription = "Entrar"
            )
            Spacer(Modifier.height(8.dp))
            SecondaryButton(
                text = "Criar Conta",
                onClick = { navController.navigate("cadastro") },
                modifier = Modifier.fillMaxWidth(),
                leadingIcon = Icons.Filled.PersonAdd,
                iconContentDescription = "Criar Conta"
            )
        }
    }
}
