package br.com.mobileti.cryptonews.design.component

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertIsNotDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import br.com.mobileti.cryptonews.design.theme.CryptoNewsTheme
import org.junit.Rule
import org.junit.Test

class ScreenComponentTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    private val fakeTitle = "myApp"

    @Test
    fun shouldShowTitle_whenHasTitleText() {
        composeTestRule.setContent {
            CryptoNewsTheme {
                CryptoNewsAppBar(title = fakeTitle)
            }
        }

        composeTestRule.onNodeWithText(fakeTitle).assertIsDisplayed()
    }

    @Test
    fun shouldNotShowTitle_whenHasNoTitleText() {
        composeTestRule.setContent {
            CryptoNewsTheme {
                CryptoNewsAppBar(title = "")
            }
        }

        composeTestRule.onNodeWithText("").assertIsNotDisplayed()
    }

}