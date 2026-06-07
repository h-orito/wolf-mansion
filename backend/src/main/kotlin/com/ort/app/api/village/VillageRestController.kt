package com.ort.app.api.village

import com.ort.app.api.village.response.VillageListResponse
import com.ort.app.application.service.VillageService
import com.ort.app.domain.model.village.VillageQuery
import com.ort.app.domain.model.village.VillageStatus
import com.ort.app.fw.exception.WolfMansionBusinessException
import com.ort.dbflute.allcommon.CDef
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

/**
 * 村一覧の REST (公開)。複数画面で共有する (トップページ = 未終了村、村一覧画面 = 全村 + 絞り込み)。
 * status で状態を絞り込める。村作成可否は player の情報なので本 API では返さない (me の `canCreateVillage`)。
 */
@RestController
@RequestMapping("/api/v1/villages")
class VillageRestController(
    private val villageService: VillageService,
) {
    /**
     * 村一覧を返す。
     * @param status `notFinished` (募集中/進行中/エピローグ) / `finished` (終了/廃村) / 省略・`all` (全件)。
     */
    @GetMapping
    fun list(
        @RequestParam(required = false) status: String?,
    ): VillageListResponse {
        val statusCodes = toStatusCodes(status)
        val villages =
            villageService.findVillages(
                query = VillageQuery(statuses = statusCodes.map { VillageStatus(it) }),
            )
        return VillageListResponse(villages)
    }

    private fun toStatusCodes(status: String?): List<CDef.VillageStatus> =
        when (status) {
            null, "", "all" -> emptyList() // 絞り込みなし = 全件
            "notFinished" -> VillageStatus.notFinishedStatusList
            "finished" -> VillageStatus.finishedStatusList
            else -> throw WolfMansionBusinessException("不正な status です: $status (all/notFinished/finished)")
        }
}
