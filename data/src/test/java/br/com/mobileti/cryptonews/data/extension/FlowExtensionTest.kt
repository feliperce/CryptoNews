package br.com.mobileti.cryptonews.data.extension

import io.mockk.MockKAnnotations
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.flow.count
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.runBlocking
import org.hamcrest.CoreMatchers.equalTo
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Before
import org.junit.Test
import retrofit2.Response

class FlowExtensionTest {

    @MockK
    lateinit var response: Response<Unit>

    @Before
    fun setUp() {
        MockKAnnotations.init()
    }

    @Test
    fun `SHOULD loading true WHEN first syncData call`() = runBlocking {
        val data = syncData(
            local = {},
            remote = { response },
            mapper = {},
            onRemote = {},
            shouldFetchFromRemote = { true }
        )

        val firstItem = data.first()

        assertThat(firstItem.isLoading, equalTo(true))
    }

    @Test
    fun `SHOULD loading false WHEN last syncData call`() = runBlocking {
        val data = syncData(
            local = {},
            remote = { response },
            mapper = {},
            onRemote = {},
            shouldFetchFromRemote = { true }
        )

        val lastItem = data.toList().last()

        assertThat(lastItem.isLoading, equalTo(false))
    }

    @Test
    fun `SHOULD call two resources WHEN syncData call shouldFetchFromRemote true`() = runBlocking {
        val data = syncData(
            local = {},
            remote = { response },
            mapper = {},
            onRemote = {},
            shouldFetchFromRemote = { true }
        )

        val count = data.count()

        assertThat(count, equalTo(2))
    }

    @Test
    fun `SHOULD call three resources WHEN syncData call shouldFetchFromRemote false`() = runBlocking {
        val data = syncData(
            local = {},
            remote = { response },
            mapper = {},
            onRemote = {},
            shouldFetchFromRemote = { false }
        )

        val count = data.count()

        assertThat(count, equalTo(3))
    }
}