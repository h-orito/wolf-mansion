package com.ort.app.api

import com.ort.app.api.view.SkillContent
import com.ort.app.application.service.VillageService
import com.ort.app.domain.model.skill.Skill
import com.ort.app.domain.model.skill.SkillTag
import com.ort.app.domain.model.skill.Skills
import com.ort.app.domain.model.village.VillageQuery
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.ResponseBody

@Controller
class SkillController(
    val villageService: VillageService
) {

    @GetMapping("/skill")
    private fun index(model: Model): String {
        model.addAttribute("content", SkillContent())
        model.addAttribute(
            "villageList",
            villageService.findVillages(VillageQuery(isRandomOrg = false)).list.reversed()
        )
        return "skill"
    }

    @GetMapping("/skill-list")
    @ResponseBody
    private fun index(form: SkillForm): List<String> {
        var tagSkills =
            if (form.tags.isNullOrBlank()) Skills.all().list
            else SkillTag.of(form.tags!!.split(",")).flatMap { it.getSkillList() }.distinct()
        // 村IDが指定されている場合はその村の役職で絞る
        if (form.villageId != null) {
            villageService.findVillage(form.villageId!!)?.let { village ->
                tagSkills = tagSkills.filterByVillageSkill(village.id)
            }
        }
        return if (form.name.isNullOrBlank()) {
            tagSkills.map { it.code.lowercase() }
        } else {
            tagSkills.filter { it.name.contains(form.name!!) }.map { it.code.lowercase() }
        }
    }

    @GetMapping("/api/skill/search")
    @ResponseBody
    private fun apiSkillList(form: ApiSkillForm): List<Skill> {
        var tagSkills =
            if (form.tags.isNullOrEmpty()) Skills.all().list
            else SkillTag.of(form.tags!!).flatMap { it.getSkillList() }.distinct()
        // 村IDが指定されている場合はその村の役職で絞る
        if (form.villageId != null) {
            villageService.findVillage(form.villageId!!)?.let { village ->
                tagSkills = tagSkills.filterByVillageSkill(village.id)
            }
        }
        return if (form.name.isNullOrBlank()) tagSkills
        else tagSkills.filter { it.name.contains(form.name!!) }
    }

    @GetMapping("/api/skill-tag-list")
    @ResponseBody
    private fun apiSkillTagList(): List<String> {
        return SkillTag.entries.map { it.name }
    }

    private fun List<Skill>.filterByVillageSkill(villageId: Int): List<Skill> {
        val village = villageService.findVillage(villageId) ?: return this
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

    data class SkillForm(
        var tags: String? = null,
        var name: String? = null,
        var villageId: Int? = null,
    )

    data class ApiSkillForm(
        var tags: List<String>? = null,
        var name: String? = null,
        var villageId: Int? = null,
    )
}