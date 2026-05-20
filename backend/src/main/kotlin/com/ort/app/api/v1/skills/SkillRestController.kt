package com.ort.app.api.v1.skills

import com.ort.app.api.response.skill.SkillCatalogView
import com.ort.app.application.service.CampService
import com.ort.app.application.service.VillageService
import com.ort.app.domain.model.skill.Skill
import com.ort.app.domain.model.skill.SkillTag
import com.ort.app.domain.model.skill.Skills
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

/**
 * 役職カタログの参照 REST API。
 *
 * 旧 Thymeleaf `SkillController` (`/skill`, `/skill-list`) と
 * `IndexController.skillList` (`/skill/list`) の置き換え。
 *
 * - `GET /api/v1/skills`: 全役職を陣営別 + タグ一覧つきで返す (カタログ表示用)
 * - `GET /api/v1/skills/search`: タグ / キーワード / 村 ID で絞り込んだ役職コード一覧を返す
 *   (旧 `/skill-list` 互換: 役職検索フィルタ用)
 */
@RestController
@RequestMapping("/api/v1/skills")
@Tag(name = "skills", description = "役職カタログ")
class SkillRestController(
    private val campService: CampService,
    private val villageService: VillageService,
) {

    @GetMapping
    @Operation(
        summary = "役職カタログ",
        description = "陣営別の役職一覧 + タグ一覧 (絞り込みフィルタ用) を返す。",
    )
    fun catalog(): SkillCatalogView {
        return SkillCatalogView(campService.findCampSkills())
    }

    @GetMapping("/search")
    @Operation(
        summary = "役職検索 (絞り込み)",
        description = "tags / name / villageId による絞り込み結果の役職コード一覧を返す (lowercase)。" +
                "旧 `/skill-list` 互換。tags はカンマ区切り。villageId 指定時は固定構成村の場合のみ" +
                "その村に出る役職に絞り込む (闇鍋村は絞り込まず全件返す)。",
    )
    fun search(
        @Parameter(description = "カンマ区切りタグ (SkillTag enum 名)") @RequestParam(required = false) tags: String?,
        @Parameter(description = "役職名部分一致") @RequestParam(required = false) name: String?,
        @Parameter(description = "村 ID (固定構成村のみ絞り込み対象、闇鍋村は無視)") @RequestParam(required = false) villageId: Int?,
    ): List<String> {
        val tagSkills: List<Skill> =
            if (tags.isNullOrBlank()) Skills.all().list
            else SkillTag.of(tags.split(",")).flatMap { it.getSkillList() }.distinct()
        val byVillage = villageId
            ?.let { filterByVillageSkill(tagSkills, it) }
            ?: tagSkills
        val byName = if (name.isNullOrBlank()) byVillage
        else byVillage.filter { it.name.contains(name) }
        return byName.map { it.code.lowercase() }
    }

    private fun filterByVillageSkill(skills: List<Skill>, villageId: Int): List<Skill> {
        val village = villageService.findVillage(villageId) ?: return skills
        // 闇鍋は非対応 (= 絞り込まずに全件返す、旧実装踏襲)
        if (village.setting.rule.isRandomOrganization) return skills
        return when {
            village.status.isPrologue() || village.status.isCanceled() -> {
                skills.filter {
                    village.setting.organize.allRequestableSkillList().any { s -> it.code == s.code }
                }
            }
            else -> {
                // `fixedOrganization` の 1 文字が役職 shortName に一致しない場合
                // (設定不備 / 旧データ等) は該当役職をスキップして絞り込みを継続。
                // 行自体が見つからない (参加人数に合う構成が無い) ときも絞り込みせず全件返す。
                val organizationSkillCodes = village.setting.organize.fixedOrganization
                    .replace("\r\n", "\n").split("\n")
                    .firstOrNull { it.length == village.participants.count }
                    ?.mapNotNull { Skill.byShortName(it.toString())?.code }
                    ?.distinct()
                    ?: return skills
                skills.filter { organizationSkillCodes.contains(it.code) }
            }
        }
    }
}
