package com.ort.app.api.charachip

import com.ort.app.api.charachip.response.CharachipListResponse
import com.ort.app.application.service.CharaService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

/**
 * キャラセット一覧の REST (公開)。村一覧の絞り込み候補や村作成のキャラ選択などで共有する。
 */
@RestController
@RequestMapping("/api/v1/charachips")
class CharachipRestController(
    private val charaService: CharaService,
) {
    @GetMapping
    fun list(): CharachipListResponse = CharachipListResponse(charaService.findCharachips())
}
