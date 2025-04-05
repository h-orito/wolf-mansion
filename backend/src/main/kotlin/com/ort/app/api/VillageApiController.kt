package com.ort.app.api

import com.ort.app.api.view.VillageListContent
import com.ort.app.api.view.village.WholeVillageSituationsContent
import com.ort.app.application.service.AbilityService
import com.ort.app.application.service.CharaService
import com.ort.app.application.service.FootstepApplicationService
import com.ort.app.application.service.PlayerService
import com.ort.app.application.service.VillageService
import com.ort.app.application.service.VoteApplicationService
import com.ort.app.domain.model.skill.Skill
import com.ort.app.domain.model.skill.Skills
import com.ort.app.domain.model.village.VillageQuery
import com.ort.app.domain.model.village.VillageStatus
import com.ort.app.domain.service.FootstepDomainService
import com.ort.app.domain.service.VoteDomainService
import com.ort.app.fw.exception.WolfMansionBusinessException
import com.ort.dbflute.allcommon.CDef
import com.ort.dbflute.exbhv.PlayerBhv
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.ResponseBody

@Controller
@CrossOrigin
class VillageApiController(
    private val villageService: VillageService,
    private val charaService: CharaService,
    private val footstepService: FootstepApplicationService,
    private val voteApplicationService: VoteApplicationService,
    private val abilityService: AbilityService,
    private val footstepDomainService: FootstepDomainService,
    private val voteDomainService: VoteDomainService,
    private val playerBhv: PlayerBhv,
    private val playerService: PlayerService
) {
    @GetMapping("/api/village/{villageId}")
    @ResponseBody
    private fun apiVillage(
        @PathVariable villageId: Int
    ): WholeVillageSituationsContent {
        val village = villageService.findVillage(villageId)
            ?: throw WolfMansionBusinessException("village not found. id: $villageId")
        val rawFootsteps = footstepService.findFootsteps(villageId)
        val footsteps = footstepDomainService.filterDisplayFootsteps(village, rawFootsteps)
        val rawVotes = voteApplicationService.findVotes(villageId)
        val abilities = abilityService.findAbilities(villageId)
        val votes = voteDomainService.filterDisplayVotes(village, rawVotes, abilities)
        val charachips = charaService.findCharachips(
            ids = village.setting.chara.charachipIds,
            isOriginal = village.setting.chara.isOriginalCharachip
        )
        return WholeVillageSituationsContent(
            village = village,
            footsteps = footsteps,
            votes = votes,
            charachips = charachips
        )
    }

    // 村一覧初期表示
    @GetMapping("/api/village/search")
    @ResponseBody
    private fun villageList(form: VillageSearchForm): VillageListContent {
        val villages = villageService.findVillages(
            query = form.toQuery()
        )
        val charachips = charaService.findCharachips()
        val skills = Skills.all().filterNotSomeone()
        return VillageListContent(villages, charachips, skills)
    }

    data class VillageSearchForm(
        val ids: List<Int>? = emptyList(),
        val statuses: List<String>? = emptyList(),
        val charachipIds: List<Int>? = emptyList(),
        val skills: List<String>? = emptyList(),
        val isRandomOrg: Boolean? = null
    ) {
        fun toQuery(): VillageQuery {
            return VillageQuery(
                ids = ids ?: emptyList(),
                statuses = statuses?.map { VillageStatus(CDef.VillageStatus.codeOf(it)) } ?: emptyList(),
                charachipIds = charachipIds ?: emptyList(),
                skills = skills?.map { Skill(CDef.Skill.codeOf(it)) } ?: emptyList(),
                isRandomOrg = isRandomOrg
            )
        }
    }
}