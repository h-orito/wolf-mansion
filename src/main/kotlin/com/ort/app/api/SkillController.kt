package com.ort.app.api

import com.ort.app.api.view.SkillContent
import com.ort.app.domain.model.skill.SkillTag
import com.ort.app.domain.model.skill.Skills
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.ResponseBody

@Controller
class SkillController {

    @GetMapping("/skill")
    private fun index(model: Model): String {
        model.addAttribute("content", SkillContent())
        return "skill"
    }

    @GetMapping("/skill-list")
    @ResponseBody
    private fun index(form: SkillForm): List<String> {
        val tagSkills =
            if (form.tags.isNullOrBlank()) Skills.all().list
            else SkillTag.of(form.tags!!.split(",")).flatMap { it.getSkillList() }.distinct()
        return if (form.name.isNullOrBlank()) {
            tagSkills.map { it.code.lowercase() }
        } else {
            tagSkills.filter { it.name.contains(form.name!!) }.map { it.code.lowercase() }
        }
    }

    data class SkillForm(
        var tags: String? = null,
        var name: String? = null
    )
}