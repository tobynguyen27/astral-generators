package dev.tobynguyen27.astralgenerators.utils

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class FormattingUtilTest {
    @Test
    fun `test superscript`() {
        val expected = "a²"

        Assertions.assertEquals(expected, FormattingUtil.toSuperscript("a2"))
    }

    @Test
    fun `test subscript`() {
        val expected = "h₂"

        Assertions.assertEquals(expected, FormattingUtil.toSubscript("h2"))
    }
}
