package br.com.mobileti.cryptonews.home.feature.home.repository

import br.com.mobileti.cryptonews.data.local.dao.NewsDao
import br.com.mobileti.cryptonews.data.local.entity.NewsWithArticles
import br.com.mobileti.cryptonews.data.remote.response.NewsResponse
import br.com.mobileti.cryptonews.data.remote.service.NewsService
import io.mockk.*
import io.mockk.impl.annotations.RelaxedMockK
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.runBlocking
import org.hamcrest.CoreMatchers.*
import org.hamcrest.MatcherAssert.*
import org.junit.Before
import org.junit.Test
import retrofit2.Response

class HomeRepositoryTest {

    private lateinit var repository: HomeRepository
    private lateinit var newsWithArticlesList: List<NewsWithArticles>
    @RelaxedMockK
    lateinit var newsWithArticles: NewsWithArticles
    @RelaxedMockK
    lateinit var newsResponse: Response<NewsResponse>
    @RelaxedMockK
    lateinit var newsDao: NewsDao
    @RelaxedMockK
    lateinit var newsService: NewsService

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        newsWithArticlesList = listOf(newsWithArticles)
        repository = HomeRepository(
            newsDao, newsService
        )
    }

    @Test
    fun `SHOULD resource success WHEN getCurrentNews local success call`() = runBlocking {
        coEvery {
            newsDao.getNewsWithArticle()
        } returns newsWithArticlesList

        val currentNewsResList = repository.getCurrentNews().toList()
        val res = currentNewsResList.filter {
            it.data != null
        }

        coVerify {
            newsService.getCurrentNews() wasNot Called
        }

        assertThat(res.size, equalTo(1))
        assertThat(res, notNullValue())
    }

    @Test
    fun `SHOULD resource error WHEN getCurrentNews remote error call`() = runBlocking {
        coEvery {
            newsDao.getNewsWithArticle()
        } returns emptyList()

        coEvery {
            newsService.getCurrentNews()
        } returns newsResponse

        val currentNewsResList = repository.getCurrentNews().toList()
        val res = currentNewsResList.filter {
            it.error != null
        }

        assertThat(res.size, equalTo(1))
        assertThat(res, notNullValue())
    }
}