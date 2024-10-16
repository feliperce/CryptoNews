package io.github.feliperce.cryptonews.feature.nav.view

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import io.github.feliperce.cryptonews.feature.detail.view.NewsDetailScreen
import io.github.feliperce.cryptonews.feature.news.mapper.Article
import io.github.feliperce.cryptonews.feature.news.view.NewsScreen

@Composable
fun NavScreen() {
    val navController = rememberNavController()
    var currentScreen: Screen? by remember { mutableStateOf(null) }
    val snackbarHostState = remember { SnackbarHostState() }

    Scaffold(
        scaffoldState = rememberScaffoldState(),
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
        topBar = {
            currentScreen?.let {
                TopAppBar(
                    title = {
                        Text(text = it.title)
                    }
                )
            }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier.padding(paddingValues)
        ) {
            NavHost(navController, startDestination = Screen.NewsScreen.route) {
                composable(route = Screen.NewsScreen.route) {
                    NewsScreen(
                        navHostController = navController,
                        snackbarHostState = snackbarHostState
                    )
                    currentScreen = Screen.NewsScreen
                }
                composable<Article> { backStackEntry ->
                    val article: Article = backStackEntry.toRoute()

                    NewsDetailScreen(
                        article = article,
                        navHostController = navController
                    )
                    currentScreen = Screen.NewsDetailScreen
                }
            }
        }
    }
}
