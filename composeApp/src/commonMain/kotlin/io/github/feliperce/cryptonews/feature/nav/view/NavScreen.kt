package io.github.feliperce.cryptonews.feature.nav.view

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
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
        },
        bottomBar = {
            DefaultBottomNavigation(navController)
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

@Composable
fun DefaultBottomNavigation(navController: NavHostController) {
    NavigationBar {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route

        bottomNavigationItems.forEach { bottomNavigationItem ->
            NavigationBarItem(
                label = {
                    Text(
                        text = bottomNavigationItem.label
                    )
                },
                icon = {
                    Icon(
                        bottomNavigationItem.icon,
                        contentDescription = bottomNavigationItem.iconContentDescription
                    )
                },
                selected = currentRoute == bottomNavigationItem.route,
                onClick = {
                    navController.navigate(bottomNavigationItem.route) {
                        launchSingleTop = true
                    }
                }
            )
        }
    }
}