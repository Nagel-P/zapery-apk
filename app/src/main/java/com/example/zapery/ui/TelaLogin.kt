package com.example.zapery.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.zapery.viewmodel.AppViewModel
import android.widget.Toast
import androidx.compose.ui.platform.LocalContext

@Composable
fun TelaLogin(navController: NavController, viewModel: AppViewModel) {
    var email by remember { mutableStateOf("") }
    var senha by remember { mutableStateOf("") }
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Login", style = MaterialTheme.typography.h4)
        Spacer(Modifier.height(16.dp))

        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Email") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(Modifier.height(8.dp))
        OutlinedTextField(
            value = senha,
            onValueChange = { senha = it },
            label = { Text("Senha") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(Modifier.height(16.dp))

        Button(onClick = {
            if (viewModel.autenticar(email, senha)) {
                navController.navigate("mercados")
            } else {
                Toast.makeText(context, "Email ou senha inválidos", Toast.LENGTH_SHORT).show()
            }
        }) {
            Text("Entrar")
        }
        Spacer(Modifier.height(8.dp))
        TextButton(onClick = { navController.navigate("cadastro") }) {
            Text("Criar Conta")
        }
    }
}
