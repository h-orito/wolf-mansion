package com.ort.app.api.v1.charachips

import com.ort.app.api.response.chara.CharachipDetailView
import com.ort.app.api.response.chara.CharachipView
import com.ort.app.application.service.CharaService
import com.ort.app.fw.exception.WolfMansionRecordNotFoundException
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

/**
 * キャラチップ / キャラの参照 REST API。
 *
 * 旧 Thymeleaf `CharaController` (`/chara-group`, `/chara-group/{id}`) の置き換え。
 * フロントエンドの「キャラチップ一覧 / 詳細」画面、および新規村作成 (Step 8h) でのダミーキャラ
 * 選択候補取得に使う。
 *
 * オリジナルキャラチップ (`isOriginal=true`) はプレイヤーが登録した画像セットで、
 * 認可されたユーザのみが見えるべき。現状は read-only / 公開済みのみを返す前提で
 * `isOriginal=false` 固定で実装する (オリジナルキャラチップの管理機能は Step 8h 以降)。
 */
@RestController
@RequestMapping("/api/v1/charachips")
@Tag(name = "charachips", description = "キャラチップ / キャラ")
class CharachipRestController(
    private val charaService: CharaService,
) {

    @GetMapping
    @Operation(
        summary = "キャラチップ一覧",
        description = "公式キャラチップ (= プレイヤー登録によるオリジナルでないもの) を全件返す。",
    )
    fun list(): List<CharachipView> {
        return charaService.findCharachips().list.map { CharachipView(it) }
    }

    @GetMapping("/{charachipId}")
    @Operation(
        summary = "キャラチップ詳細",
        description = "指定キャラチップに属するキャラ一覧 (デフォルト画像のみ) と作者・説明 URL を返す。" +
                "オリジナルキャラチップは現時点では公開しないので `isOriginal=false` 固定で問い合わせる。",
    )
    fun detail(@PathVariable charachipId: Int): CharachipDetailView {
        val charachip = charaService.findCharachip(charachipId, isOriginal = false)
            ?: throw WolfMansionRecordNotFoundException("charachip not found. id=$charachipId")
        return CharachipDetailView(charachip)
    }
}
