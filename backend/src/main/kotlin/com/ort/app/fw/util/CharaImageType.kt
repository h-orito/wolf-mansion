package com.ort.app.fw.util

import org.springframework.web.multipart.MultipartFile

/**
 * アップロードを許可するキャラ画像の形式。
 *
 * 保存時の拡張子はクライアントのファイル名ではなく、先頭バイト (マジックバイト) から判定した形式で決める。
 * 拡張子をクライアントに任せると .html / .svg が同一オリジンでスクリプトとして配信されるため。
 */
enum class CharaImageType(
    val extension: String,
) {
    PNG(".png"),
    JPEG(".jpg"),
    GIF(".gif"),
    WEBP(".webp"),
    ;

    companion object {
        fun detect(bytes: ByteArray): CharaImageType? =
            when {
                bytes.startsWith(0x89, 'P', 'N', 'G', 0x0D, 0x0A, 0x1A, 0x0A) -> PNG
                bytes.startsWith(0xFF, 0xD8, 0xFF) -> JPEG
                bytes.startsWith('G', 'I', 'F', '8') -> GIF
                bytes.startsWith('R', 'I', 'F', 'F') && bytes.startsWith('W', 'E', 'B', 'P', offset = 8) -> WEBP
                else -> null
            }

        fun detect(file: MultipartFile): CharaImageType? = detect(file.bytes)

        private fun ByteArray.startsWith(
            vararg expected: Any,
            offset: Int = 0,
        ): Boolean {
            if (size < offset + expected.size) return false
            return expected.withIndex().all { (i, e) ->
                val b = this[offset + i].toInt() and 0xFF
                when (e) {
                    is Int -> b == e
                    is Char -> b == e.code
                    else -> false
                }
            }
        }
    }
}
