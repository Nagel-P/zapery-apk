package com.example.zapery

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.zapery.ui.theme.ZaperyTheme
import com.example.zapery.viewmodel.AppViewModel
import com.example.zapery.ui.*

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ZaperyTheme {
                val navController = rememberNavController()
                val appViewModel: AppViewModel = viewModel()

                Surface(color = MaterialTheme.colors.background) {
                    NavHost(navController = navController, startDestination = "login") {
                        composable("login") { TelaLogin(navController, appViewModel) }
                        composable("cadastro") { TelaCadastro(navController, appViewModel) }
                        composable("mercados") { TelaMercados(navController, appViewModel) }
                        composable("produtos/{mercadoId}") { TelaProdutos(navController, appViewModel) }
                        composable("carrinho") { TelaCarrinho(navController, appViewModel) }
                        composable("compras_rapidas") { TelaComprasRapidas(navController, appViewModel) }
                        composable("confirmacao") { TelaConfirmacao(navController, appViewModel) }
                        composable("pedido_finalizado") { TelaPedidoFinalizado(navController, appViewModel) }
                    }
                }
            }
        }
    }
}
