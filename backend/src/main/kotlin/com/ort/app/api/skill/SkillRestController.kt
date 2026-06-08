package com.ort.app.api.skill

import com.ort.app.api.skill.response.SkillListResponse
import com.ort.app.domain.model.skill.Skills
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

/**
 * 役職一覧の REST (公開)。村一覧の絞り込み候補や役職一覧画面で共有する。
 */
@RestController
@RequestMapping("/api/v1/skills")
class SkillRestController {
    @GetMapping
    fun list(): SkillListResponse = SkillListResponse(Skills.all().filterNotSomeone())
}
