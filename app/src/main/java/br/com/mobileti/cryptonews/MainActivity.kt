package br.com.mobileti.cryptonews

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.rememberNavController
import br.com.mobileti.cryptonews.design.theme.CryptoNewsTheme
import br.com.mobileti.cryptonews.feature.detail.viewmodel.DetailViewModel
import br.com.mobileti.cryptonews.home.feature.home.viewmodel.HomeViewModel
import br.com.mobileti.cryptonews.navhost.CryptoNewsNavHost
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : ComponentActivity() {

    private val homeViewModel: HomeViewModel by viewModel()
    private val detailViewModel: DetailViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CryptoNewsTheme {
                val navController = rememberNavController()

                CryptoNewsNavHost(
                    navHostController = navController,
                    homeViewModel = homeViewModel,
                    detailViewModel = detailViewModel
                )
            }
        }
    }
}