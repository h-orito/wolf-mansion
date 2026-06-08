package com.ort.app.api.village

import com.ort.app.api.village.request.VillageSearchRequest
import com.ort.app.api.village.response.VillageListResponse
import com.ort.app.application.service.VillageService
import org.springdoc.core.annotations.ParameterObject
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

/**
 * 村一覧の REST (公開)。複数画面で共有する (トップページ = 未終了村、村一覧画面 = 全村 + 絞り込み)。
 * 状態・キャラセット・役職・編成での絞り込みと並び順は [VillageSearchRequest] で受ける。
 * 村作成可否は player の情報なので本 API では返さない (me の `canCreateVillage`)。
 */
@RestController
@RequestMapping("/api/v1/villages")
class VillageRestController(
    private val villageService: VillageService,
) {
    @GetMapping
    fun list(
        @ParameterObject request: VillageSearchRequest,
    ): VillageListResponse = VillageListResponse(villageService.findVillages(request.toQuery()))
}
