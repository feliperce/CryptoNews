package br.com.mobileti.cryptonews.home.feature.home.view

import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createComposeRule
import br.com.mobileti.cryptonews.datasource.fakeArticle
import br.com.mobileti.cryptonews.design.theme.CryptoNewsTheme
import org.junit.Rule
import org.junit.Test


class HomeScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun shouldShowNewsItemTexts_whenArticle() {
        composeTestRule.setContent {
            CryptoNewsTheme {
                NewsItem(article = fakeArticle, onItemClick = {})
            }
        }

        composeTestRule.onRoot(useUnmergedTree = true).printToLog("NewsItem")

        composeTestRule.onNodeWithContentDescription("description")
            .assertTextContains(fakeArticle.description)
        composeTestRule.onNodeWithContentDescription("title")
            .assertTextContains(fakeArticle.title)
        composeTestRule.onNodeWithContentDescription("publishedAt")
            .assertTextContains(fakeArticle.publishedAt)
    }

    @Test
    fun shouldClickNewsItem_whenIsClickable() {
        composeTestRule.setContent {
            CryptoNewsTheme {
                NewsItem(article = fakeArticle, onItemClick = {})
            }
        }

        composeTestRule.onNodeWithContentDescription("root").assertHasClickAction()
    }

}