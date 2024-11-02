package io.github.feliperce.cryptonews.feature.nav.view

import androidx.compose.runtime.*
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import io.github.aakira.napier.DebugAntilog
import io.github.aakira.napier.Napier
import io.github.feliperce.cryptonews.feature.detail.view.NewsDetailScreen
import io.github.feliperce.cryptonews.feature.nav.ArticleNavType
import io.github.feliperce.cryptonews.feature.news.mapper.Article
import io.github.feliperce.cryptonews.feature.news.view.NewsScreen
import kotlin.reflect.typeOf

@Composable
fun NavScreen() {
    val navController = rememberNavController()

    Napier.base(DebugAntilog())

    NavHost(navController, startDestination = Screen.NewsScreen) {
        composable<Screen.NewsScreen> {
            NewsScreen(
                navHostController = navController
            )
        }
        composable<Screen.NewsDetailScreen>(
            typeMap = mapOf(typeOf<Article>() to ArticleNavType),
        ) { backStackEntry ->
            val newsDetailScreen: Screen.NewsDetailScreen = backStackEntry.toRoute()

            NewsDetailScreen(
                article = newsDetailScreen.article,
                navHostController = navController
            )
        }
    }
}
