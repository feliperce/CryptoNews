package io.github.feliperce.cryptonews

import io.github.feliperce.cryptonews.data.remote.Resource
import io.mockk.mockk
import io.mockk.verify
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNull
import kotlin.test.assertTrue

class DataManagerTest {

    private interface Observer<T, E> {
        fun onResource(resource: Resource<T, E>)
    }

    @Test
    fun `GIVEN success resource WHEN observing THEN observer receives success with data`() {
        val observer = mockk<Observer<String, String>>(relaxed = true)
        val resource: Resource<String, String> = Resource.Success("OK")

        observer.onResource(resource)

        verify { observer.onResource(match { it is Resource.Success && it.data == "OK" && !it.isLoading && it.error == null }) }
        assertTrue(resource is Resource.Success)
        assertEquals("OK", resource.data)
        assertNull(resource.error)
        assertEquals(false, resource.isLoading)
    }

    @Test
    fun `GIVEN error resource WHEN observing THEN observer receives error with message`() {
        val observer = mockk<Observer<String, String>>(relaxed = true)
        val resource: Resource<String, String> = Resource.Error(error = "Boom", data = null)

        observer.onResource(resource)

        verify { observer.onResource(match { it is Resource.Error && it.error == "Boom" && it.data == null && !it.isLoading }) }
        assertTrue(resource is Resource.Error)
        assertEquals("Boom", resource.error)
        assertNull(resource.data)
        assertEquals(false, resource.isLoading)
    }

    @Test
    fun `GIVEN loading resource WHEN created THEN has loading true`() {
        val resource: Resource<Unit, String> = Resource.Loading(isLoading = true)
        assertTrue(resource is Resource.Loading)
        assertTrue(resource.isLoading)
        assertNull(resource.data)
        assertNull(resource.error)
    }
}