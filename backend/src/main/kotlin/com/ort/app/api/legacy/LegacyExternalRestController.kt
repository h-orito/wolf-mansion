package com.ort.app.api.legacy

import com.ort.app.application.service.CampService
import com.ort.app.application.service.CharaService
import com.ort.app.application.service.PlayerService
import com.ort.app.application.service.VillageService
import com.ort.app.domain.model.skill.Skill
import com.ort.app.domain.model.skill.SkillTag
import com.ort.app.domain.model.skill.Skills
import com.ort.app.domain.model.village.Village
import com.ort.app.domain.model.village.VillageQuery
import com.ort.app.domain.model.village.VillageStatus
import com.ort.dbflute.allcommon.CDef
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

/**
 * 旧 Thymeleaf 実装で外部システム (Discord ボット / スクレイパー) 向けに公開していた
 * snake_case JSON エンドポイントを、Thymeleaf 撤去後も継続提供するための互換コントローラ。
 *
 * パス・レスポンス schema は旧実装と完全同一に維持すること (外部依存があるため)。
 * camelCase + ISO 日時の新 API は別途 api/v1 配下に存在する。
 */
@RestController
class LegacyExternalRestController(
    private val villageService: VillageService,
    private val playerService: PlayerService,
    private val charaService: CharaService,
    private val campService: CampService,
) {

    @GetMapping("/recruiting")
    fun recruiting(): RecruitingContent {
        var villages = villageService.findVillages(
            query = VillageQuery(statuses = VillageStatus.notFinishedStatusList.map { VillageStatus(it) })
        )
        villages = villages.copy(
            list = villages.list.filterNot { it.name == "【サンプル】インターフェース確認用" }
        )
        val charachips = charaService.findCharachips()
        return RecruitingContent(villages, charachips)
    }

    @GetMapping("/village-record/list")
    fun villageRecordList(
        @RequestParam("vid", required = false) vid: List<Int>?
    ): VillageRecordListContent {
        var villages = villageService.findVillages(
            query = VillageQuery(
                statuses = listOf(
                    VillageStatus(CDef.VillageStatus.エピローグ),
                    VillageStatus(CDef.VillageStatus.終了),
                    VillageStatus(CDef.VillageStatus.廃村)
                ),
                ids = vid ?: emptyList()
            )
        )
        villages = villages.copy(list = villages.list.reversed())
        val players = playerService.findPlayers(
            villageIdList = villages.list.map { it.id }
        )
        return VillageRecordListContent(villages, players)
    }

    @GetMapping("/village-record/latest-vid")
    fun latestVillageRecordVid(): VillageRecordLatestVidContent {
        val id = villageService.findLatestVillageId(
            statusList = listOf(
                VillageStatus(CDef.VillageStatus.エピローグ),
                VillageStatus(CDef.VillageStatus.終了),
                VillageStatus(CDef.VillageStatus.廃村)
            )
        )
        return VillageRecordLatestVidContent(id)
    }

    @GetMapping("/skill/list")
    fun skillList(): SkillListContent {
        val campSkills = campService.findCampSkills()
        return SkillListContent(
            list = campSkills.map {
                SkillListContent.CampSkillName(
                    campName = it.camp.name,
                    skillList = it.skillList.map { it.name }
                )
            }
        )
    }

    @GetMapping("/skill-list")
    fun skillCodeList(
        @RequestParam("tags", required = false) tags: String?,
        @RequestParam("name", required = false) name: String?,
        @RequestParam("villageId", required = false) villageId: Int?,
    ): List<String> {
        var tagSkills =
            if (tags.isNullOrBlank()) Skills.all().list
            else SkillTag.of(tags.split(",")).flatMap { it.getSkillList() }.distinct()
        // 村IDが指定されている場合はその村の役職で絞る
        if (villageId != null) {
            villageService.findVillage(villageId)?.let { village ->
                tagSkills = tagSkills.filterByVillageSkill(village)
            }
        }
        return if (name.isNullOrBlank()) {
            tagSkills.map { it.code.lowercase() }
        } else {
            tagSkills.filter { it.name.contains(name) }.map { it.code.lowercase() }
        }
    }

    private fun List<Skill>.filterByVillageSkill(village: Village): List<Skill> {
        // 闇鍋は非対応
        if (village.setting.rule.isRandomOrganization) return this
        return when {
            village.status.isPrologue() || village.status.isCanceled() -> {
                this.filter { village.setting.organize.allRequestableSkillList().any { s -> it.code == s.code } }
            }

            else -> {
                val organizationSkillCodes = village.setting.organize.fixedOrganization
                    .replace("\r\n", "\n").split("\n")
                    .first { it.length == village.participants.count }
                    .map { Skill.byShortName(it.toString())!!.code }
                    .distinct()
                this.filter { organizationSkillCodes.contains(it.code) }
            }
        }
    }
}
