package com.ort.app.api.v1.villages

import com.ort.app.api.response.village.VillageRecordsView
import com.ort.app.application.service.PlayerService
import com.ort.app.application.service.VillageService
import com.ort.app.domain.model.village.VillageQuery
import com.ort.app.domain.model.village.VillageStatus
import com.ort.dbflute.allcommon.CDef
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

/**
 * 終了済村の戦績一覧 REST API。
 *
 * 旧 Thymeleaf `IndexController` (`/village-record/list`, `/village-record/latest-vid`)
 * の置き換え。エピローグ / 終了 / 廃村 の村のみを対象。
 *
 * 旧 API は snake_case + 文字列日時で外部連携 (Discord ボット等) が依存している
 * 可能性があるため、Thymeleaf 撤去 (Step 9) まで旧 endpoint は残置する方針。
 * 新 endpoint は他の REST API と整合的に camelCase + ISO 日時で返す。
 */
@RestController
@RequestMapping("/api/v1/village-records")
@Tag(name = "village-records", description = "終了村の戦績")
class VillageRecordRestController(
    private val villageService: VillageService,
    private val playerService: PlayerService,
) {

    private val finishedStatuses = listOf(
        VillageStatus(CDef.VillageStatus.エピローグ),
        VillageStatus(CDef.VillageStatus.終了),
        VillageStatus(CDef.VillageStatus.廃村),
    )

    @GetMapping
    @Operation(
        summary = "終了済 村戦績一覧",
        description = "エピローグ / 終了 / 廃村 の村を新しい順に返す。`vid` で対象村 ID を絞り込み可能。",
    )
    fun list(
        @Parameter(description = "対象村 ID (繰り返し指定可、未指定で全件)") @RequestParam(required = false) vid: List<Int>?,
    ): VillageRecordsView {
        val villages = villageService.findVillages(
            query = VillageQuery(
                statuses = finishedStatuses,
                ids = vid ?: emptyList(),
            ),
        )
        val reversed = villages.copy(list = villages.list.reversed())
        val players = playerService.findPlayers(villageIdList = reversed.list.map { it.id })
        return VillageRecordsView(reversed, players)
    }

    @GetMapping("/latest-vid")
    @Operation(
        summary = "最新の終了済村 ID",
        description = "エピローグ / 終了 / 廃村 の村のうち最新 (= 最大 ID) のものを返す。",
    )
    fun latestVid(): LatestVidView {
        return LatestVidView(vid = villageService.findLatestVillageId(finishedStatuses))
    }

    @io.swagger.v3.oas.annotations.media.Schema(description = "最新終了済村 ID")
    data class LatestVidView(val vid: Int)
}
