package com.ort.app.api.v1.support

import com.ort.app.fw.exception.WolfMansionBusinessException
import org.springframework.web.multipart.MultipartFile

/**
 * REST 層でアップロード画像 (ダミーキャラ / 入村キャラ画像 / 表情差分) を共通検証するヘルパー。
 *
 * 検証内容:
 * - サイズ: 1〜100,000 byte (旧 Thymeleaf 系 `NewVillageFormValidator` / `VillageParticipateFormValidator` /
 *   `VillageFaceTypeFormValidator` と同条件)。1KB = 1000 byte 換算で「最大 100KB」相当。
 * - 拡張子ホワイトリスト: png / jpg / jpeg / gif / webp。`CharaDataSource.uploadCharaImage` が
 *   `originalFilename.lastIndexOf('.')` で拡張子を切り出すため、`.hidden` のような
 *   ドット始まりや拡張子なしのファイルを弾く + パストラバーサル / 想定外フォーマット防御。
 *
 * 旧 Thymeleaf 経路は `CharaDataSource.uploadCharaImage` 内で同等のガードを持つが、
 * REST 経路では境界 (controller) で先回り検証してエラーメッセージを統一する。
 */
object ImageUploadValidator {

    /** 1 byte 〜 100,000 byte (≒ 100 KB)。0 byte (空ファイル) も reject。 */
    const val MAX_SIZE_BYTES: Long = 100_000L

    val ALLOWED_EXTENSIONS: Set<String> = setOf(".png", ".jpg", ".jpeg", ".gif", ".webp")

    /**
     * 画像をサイズ + 拡張子の両面で検証する。違反があれば `WolfMansionBusinessException` (= 400)。
     */
    fun validate(image: MultipartFile) {
        if (image.size <= 0L || image.size > MAX_SIZE_BYTES) {
            throw WolfMansionBusinessException("画像サイズは最大 ${MAX_SIZE_BYTES} byte までです")
        }
        val filename = image.originalFilename
        val dotIndex = filename?.lastIndexOf('.') ?: -1
        if (filename == null || dotIndex < 1 ||
            filename.substring(dotIndex).lowercase() !in ALLOWED_EXTENSIONS
        ) {
            throw WolfMansionBusinessException(
                "画像ファイルの拡張子は ${ALLOWED_EXTENSIONS.joinToString(" / ")} のみ対応しています"
            )
        }
    }
}
