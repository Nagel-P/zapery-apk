package com.example.zapery

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.zapery.ui.theme.ZaperyThemeM3
import com.example.zapery.viewmodel.AppViewModel
import com.example.zapery.viewmodel.AppViewModelFactory
import com.example.zapery.ui.*
import com.example.zapery.data.local.DatabaseProvider
import com.example.zapery.data.repositorio.*

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ZaperyThemeM3 {
                val navController = rememberNavController()
                val db = DatabaseProvider.get(applicationContext)
                val appViewModel: AppViewModel = viewModel(
                    factory = AppViewModelFactory(
                        UsuarioRepositorio(db.usuarioDao()),
                        MercadoRepositorio(db.mercadoDao()),
                        ProdutoRepositorio(db.produtoDao()),
                        CarrinhoRepositorio(db.itemCarrinhoDao()),
                        CompraRapidaRepositorio(db.compraRapidaDao())
                    )
                )

                Surface(color = MaterialTheme.colorScheme.background) {
                    NavHost(navController = navController, startDestination = "login") {
                        composable("login") { TelaLogin(navController, appViewModel) }
                        composable("cadastro") { TelaCadastro(navController, appViewModel) }
                        composable("mercados") { TelaMercados(navController, appViewModel) }
                        composable("produtos/{mercadoId}") { backStackEntry ->
                            val mercadoId = backStackEntry.arguments?.getString("mercadoId")?.toIntOrNull() ?: 0
                            TelaProdutos(navController, appViewModel, mercadoId)
                        }
                        composable("carrinho") { TelaCarrinho(navController, appViewModel) }
                        composable("compras_rapidas") { TelaComprasRapidas(navController, appViewModel) }
                        composable("confirmacao") { TelaConfirmacao(navController, appViewModel) }
                        composable("pedido_finalizado") { TelaPedidoFinalizado(navController, appViewModel) }
                        composable("admin") { TelaAdmin(navController, appViewModel, null) }
                        composable("admin/{marketId}") { backStackEntry ->
                            val marketId = backStackEntry.arguments?.getString("marketId")?.toIntOrNull()
                            TelaAdmin(navController, appViewModel, marketId)
                        }
                        composable("produto_form/{marketId}") { backStackEntry ->
                            val marketId = backStackEntry.arguments?.getString("marketId")?.toIntOrNull() ?: 0
                            TelaProdutoForm(navController, appViewModel, marketId, null)
                        }
                        composable("produto_form/{marketId}/{productId}") { backStackEntry ->
                            val marketId = backStackEntry.arguments?.getString("marketId")?.toIntOrNull() ?: 0
                            val productId = backStackEntry.arguments?.getString("productId")?.toIntOrNull()
                            TelaProdutoForm(navController, appViewModel, marketId, productId)
                        }
                    }
                }
            }
        }
    }
}

