package io.github.feliperce.cryptonews.feature.nav.view

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
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
                        snackbarHostState = snackbarHostState
                    )
                    currentScreen = Screen.NewsScreen
                }
            }
        }
    }
}
