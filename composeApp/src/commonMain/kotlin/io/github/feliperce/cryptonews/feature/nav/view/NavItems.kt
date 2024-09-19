package io.github.feliperce.cryptonews.feature.nav.view

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.ui.graphics.vector.ImageVector

sealed class Screen(val title: String, val route: String) {
    data object NewsScreen : Screen("News", "news")
    data object NewsDetailScreen : Screen("Detail", "detail")
}

data class BottomNavigationItem(
    val route: String,
    val icon: ImageVector,
    val iconContentDescription: String,
    val label: String
)

val bottomNavigationItems = listOf(
    BottomNavigationItem(
        route = Screen.NewsScreen.route,
        icon = Icons.Default.Done,
        iconContentDescription = "Extractor",
        label = "Extractor"
    ),
    BottomNavigationItem(
        route = Screen.NewsDetailScreen.route,
        icon = Icons.Filled.Settings,
        iconContentDescription = "Settings",
        label = "Settings"
    )
)
