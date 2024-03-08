package com.sirnuke.elusivebot.behaviors.pattern.match.lib

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Test

class PatternMatchServiceTests {
    private val service = PatternMatchService.create()
    @Test
    fun doesF() {
        assertEquals("F", service.process("F"))
    }

    @Test
    fun ignoreEmpty() {
        assertNull(service.process(""))
    }
}