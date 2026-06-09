package com.ort.app.api.skill

import com.ort.app.api.skill.request.SkillSearchRequest
import com.ort.app.api.skill.response.SkillListResponse
import com.ort.app.api.skill.response.SkillSearchResponse
import com.ort.app.application.service.VillageService
import com.ort.app.domain.model.skill.Skill
import com.ort.app.domain.model.skill.Skills
import org.springdoc.core.annotations.ParameterObject
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

/**
 * 役職の REST (公開)。村一覧の絞り込み候補や役職一覧画面で共有する。
 */
@RestController
@RequestMapping("/api/v1/skills")
class SkillRestController(
    private val villageService: VillageService,
) {
    @GetMapping
    fun list(): SkillListResponse = SkillListResponse(Skills.all().filterNotSomeone())

    @GetMapping("/search")
    fun search(
        @ParameterObject request: SkillSearchRequest,
    ): SkillSearchResponse {
        var skills =
            if (request.parseTags().isEmpty()) {
                Skills.all().filterNotSomeone().list
            } else {
                request.parseTags().flatMap { it.getSkillList() }.distinct()
            }
        if (request.villageId != null) {
            skills = filterByVillageSkill(skills, request.villageId)
        }
        if (!request.name.isNullOrBlank()) {
            skills = skills.filter { it.name.contains(request.name) }
        }
        return SkillSearchResponse(skillCodes = skills.map { it.code })
    }

    private fun filterByVillageSkill(
        skills: List<Skill>,
        villageId: Int,
    ): List<Skill> {
        val village = villageService.findVillage(villageId) ?: return skills
        if (village.setting.rule.isRandomOrganization) return skills
        return when {
            village.status.isPrologue() || village.status.isCanceled() -> {
                skills.filter { skill ->
                    village.setting.organize
                        .allRequestableSkillList()
                        .any { it.code == skill.code }
                }
            }

            else -> {
                val organizationSkillCodes =
                    village.setting.organize.fixedOrganization
                        .replace("\r\n", "\n")
                        .split("\n")
                        .first { it.length == village.participants.count }
                        .map { Skill.byShortName(it.toString())!!.code }
                        .distinct()
                skills.filter { organizationSkillCodes.contains(it.code) }
            }
        }
    }
}
