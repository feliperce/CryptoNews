package io.github.feliperce.cryptonews.feature.nav.view

sealed class Screen(val title: String, val route: String) {
    data object NewsScreen : Screen("News", "news")
    data object NewsDetailScreen : Screen("Detail", "detail")
}
