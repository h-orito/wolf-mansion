package com.ort.app.fw.util

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Test

internal class CharaImageTypeTest {
    private fun bytes(vararg b: Int): ByteArray = b.map { it.toByte() }.toByteArray()

    @Test
    fun `先頭バイトから画像形式を判定する`() {
        assertEquals(CharaImageType.PNG, CharaImageType.detect(bytes(0x89, 0x50, 0x4E, 0x47, 0x0D, 0x0A, 0x1A, 0x0A, 0x00)))
        assertEquals(CharaImageType.JPEG, CharaImageType.detect(bytes(0xFF, 0xD8, 0xFF, 0xE0)))
        assertEquals(CharaImageType.GIF, CharaImageType.detect("GIF89a".toByteArray()))
        assertEquals(CharaImageType.WEBP, CharaImageType.detect("RIFF\u0000\u0000\u0000\u0000WEBPVP8 ".toByteArray()))
    }

    @Test
    fun `画像でない中身は拒否する`() {
        assertNull(CharaImageType.detect("<script>alert(1)</script>".toByteArray()))
        assertNull(CharaImageType.detect("<svg xmlns=\"http://www.w3.org/2000/svg\"/>".toByteArray()))
        // WAV など WEBP 以外の RIFF
        assertNull(CharaImageType.detect("RIFF\u0000\u0000\u0000\u0000WAVEfmt ".toByteArray()))
        assertNull(CharaImageType.detect(bytes(0x89, 0x50, 0x4E)))
        assertNull(CharaImageType.detect(ByteArray(0)))
    }

    @Test
    fun `保存する拡張子は形式から決まる`() {
        assertEquals(".png", CharaImageType.PNG.extension)
        assertEquals(".jpg", CharaImageType.JPEG.extension)
        assertEquals(".gif", CharaImageType.GIF.extension)
        assertEquals(".webp", CharaImageType.WEBP.extension)
    }
}
