package com.ort.app.api.v1.villages

import com.ort.app.api.response.village.VillagesView
import com.ort.app.application.service.VillageService
import com.ort.app.domain.model.village.VillageQuery
import com.ort.app.domain.model.village.VillageStatus
import com.ort.app.domain.model.village.toModel
import com.ort.dbflute.allcommon.CDef
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/villages")
@Tag(name = "villages", description = "村")
class VillageRestController(
    private val villageService: VillageService,
) {

    @GetMapping
    @Operation(
        summary = "村一覧取得",
        description = "ステータスでフィルタ可能。指定なしなら全村を返す。新着順 (id 降順)。",
    )
    fun list(
        @Parameter(
            description = "ステータスコードのカンマ区切り (例: 募集中,進行中)。未指定なら全件。",
        )
        @RequestParam(required = false) status: String?,
    ): VillagesView {
        val statuses = parseStatuses(status)
        val villages = villageService.findVillages(VillageQuery(statuses = statuses))
        return VillagesView(villages)
    }

    private fun parseStatuses(raw: String?): List<VillageStatus> {
        if (raw.isNullOrBlank()) return emptyList()
        val all = CDef.VillageStatus.listAll()
        return raw.split(",")
            .map { it.trim() }
            .filter { it.isNotEmpty() }
            .mapNotNull { token ->
                CDef.VillageStatus.codeOf(token)
                    ?: all.firstOrNull { it.alias() == token }
            }
            .map { it.toModel() }
    }
}
