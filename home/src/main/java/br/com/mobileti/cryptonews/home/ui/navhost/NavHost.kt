package br.com.mobileti.cryptonews.home.ui.navhost

import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import br.com.mobileti.cryptonews.home.feature.detail.view.DetailScreen
import br.com.mobileti.cryptonews.feature.detail.viewmodel.DetailViewModel
import br.com.mobileti.cryptonews.home.feature.home.view.HomeScreen
import br.com.mobileti.cryptonews.home.feature.home.viewmodel.HomeViewModel

@Composable
fun CryptoNewsNavHost(
    navHostController: NavHostController,
    homeViewModel: HomeViewModel,
    detailViewModel: DetailViewModel
) {
    NavHost(navController = navHostController, startDestination = "home") {
        composable("home") {
            Surface(color = MaterialTheme.colors.background) {
                HomeScreen(
                    navController = navHostController,
                    homeViewModel = homeViewModel
                )
            }
        }
        composable(
            route = "detail/{articleId}",
            arguments = listOf(navArgument("articleId") { type = NavType.LongType })
        ) {
            it.arguments?.let { arg ->
                Surface(color = MaterialTheme.colors.background) {
                    DetailScreen(
                        navController = navHostController,
                        detailViewModel = detailViewModel,
                        articleId = arg.getLong("articleId")
                    )
                }
            }
        }
    }
}
