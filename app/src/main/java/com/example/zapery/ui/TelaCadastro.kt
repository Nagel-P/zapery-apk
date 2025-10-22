package com.example.zapery.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import android.widget.Toast
import androidx.compose.ui.platform.LocalContext
import com.example.zapery.model.Usuario
import com.example.zapery.viewmodel.AppViewModel
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack

@Composable
fun TelaCadastro(navController: NavController, viewModel: AppViewModel) {
    val context = LocalContext.current
    var nome by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var senha by remember { mutableStateOf("") }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Cadastro") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Filled.ArrowBack, contentDescription = "Voltar")
                    }
                }
            )
        }
    ) { padding ->
        Column(modifier = Modifier.padding(padding).padding(16.dp)) {
            Spacer(Modifier.height(8.dp))
            TextField(value = nome, onValueChange = { nome = it }, label = { Text("Nome") })
            Spacer(Modifier.height(8.dp))
            TextField(value = email, onValueChange = { email = it }, label = { Text("Email") })
            Spacer(Modifier.height(8.dp))
            TextField(value = senha, onValueChange = { senha = it }, label = { Text("Senha") })
            Spacer(Modifier.height(16.dp))
            Button(onClick = {
                if (nome.isNotEmpty() && email.isNotEmpty() && senha.isNotEmpty()) {
                    viewModel.cadastrar(Usuario(viewModel.usuarios.size + 1, nome, email, senha))
                    Toast.makeText(context, "Cadastro realizado com sucesso!", Toast.LENGTH_SHORT).show()
                    navController.navigate("login")
                } else {
                    Toast.makeText(context, "Preencha todos os campos", Toast.LENGTH_SHORT).show()
                }
            }) {
                Text("Cadastrar")
            }
        }
    }
}
