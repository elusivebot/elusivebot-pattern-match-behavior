package com.sirnuke.elusivebot.behaviors.pattern.match.lib

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Test

/**
 * Unit tests for the Pattern Match service at the API level.
 */
class PatternMatchServiceTests {
    private val service = PatternMatchService.create()

    /**
     * Does it respond with an F with an F input?
     */
    @Test
    @Suppress("FUNCTION_NAME_INCORRECT_CASE")
    fun doesFAfterF() {
        assertEquals("F", service.process("F"))
    }

    /**
     * Does it respond with a null string with a empty string input?
     */
    @Test
    fun ignoreEmpty() {
        assertNull(service.process(""))
    }
}
