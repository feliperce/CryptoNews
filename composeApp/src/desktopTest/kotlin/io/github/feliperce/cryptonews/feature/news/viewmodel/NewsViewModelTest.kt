package io.github.feliperce.cryptonews.feature.news.viewmodel

import io.github.feliperce.cryptonews.data.remote.Resource
import io.github.feliperce.cryptonews.data.remote.mapper.ErrorData
import io.github.feliperce.cryptonews.feature.news.mapper.News
import io.github.feliperce.cryptonews.feature.news.repository.NewsRepository
import io.github.feliperce.cryptonews.feature.news.state.NewsEffect
import io.github.feliperce.cryptonews.feature.news.state.NewsIntent
import io.github.feliperce.cryptonews.feature.news.state.NewsUiState
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals

@OptIn(ExperimentalCoroutinesApi::class)
class NewsViewModelTest {

    private val testDispatcher = StandardTestDispatcher()

    @BeforeTest
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
    }

    @AfterTest
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `GIVEN success from repository WHEN GetNews intent THEN sets news and loading false`() = runTest {
        val repo = mockk<NewsRepository>()
        val news = News(articles = emptyList(), status = "ok", totalResults = 0)
        val flow: Flow<Resource<News, ErrorData>> = flowOf(
            Resource.Loading(true),
            Resource.Success(news),
            Resource.Loading(false)
        )
        coEvery { repo.getNews() } returns flow

        val vm = NewsViewModel(repo)
        testDispatcher.scheduler.advanceUntilIdle()
        vm.sendIntent(NewsIntent.GetNews)

        testDispatcher.scheduler.advanceUntilIdle()

        assertEquals(NewsUiState(loading = false, news = news), vm.newsState.value)
    }

    @Test
    fun `GIVEN error from repository WHEN GetNews intent THEN emits error effect and loading false`() = runTest {
        val repo = mockk<NewsRepository>()
        val flow: Flow<Resource<News, ErrorData>> = flowOf(
            Resource.Error(error = ErrorData(message = "Erro")),
            Resource.Loading(false)
        )
        coEvery { repo.getNews() } returns flow

        val vm = NewsViewModel(repo)
        testDispatcher.scheduler.advanceUntilIdle()

        val effects = mutableListOf<NewsEffect>()
        val job = launch { vm.effects.take(1).toList(effects) }

        vm.sendIntent(NewsIntent.GetNews)
        testDispatcher.scheduler.advanceUntilIdle()

        assertEquals(NewsUiState(loading = false, news = null), vm.newsState.value)
        assertEquals(1, effects.size)
        assertEquals(NewsEffect.ShowError("Erro"), effects.first())
    }
}
