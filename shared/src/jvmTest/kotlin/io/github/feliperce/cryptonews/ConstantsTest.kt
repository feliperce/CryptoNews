package io.github.feliperce.cryptonews

import kotlin.test.Test
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class ConstantsTest {

    @Test
    fun `GIVEN constants WHEN validating base url THEN contains host and port`() {
        assertTrue(SERVER_BASE_URL.startsWith("http://"))
        assertTrue(SERVER_BASE_URL.contains("$SERVER_HOST:$SERVER_PORT"))
    }

    @Test
    fun `GIVEN server port WHEN checking range THEN is valid tcp port`() {
        assertTrue(SERVER_PORT in 1..65535)
        assertFalse(SERVER_PORT == 0)
    }
}