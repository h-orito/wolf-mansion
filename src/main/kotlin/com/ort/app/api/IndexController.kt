package com.ort.app.api

import com.ort.app.api.request.LoginForm
import com.ort.app.api.request.VillageRecordListForm
import com.ort.app.api.view.IndexContent
import com.ort.app.api.view.RecruitingContent
import com.ort.app.api.view.SkillListContent
import com.ort.app.api.view.VillageRecordLatestVidContent
import com.ort.app.api.view.VillageRecordListContent
import com.ort.app.application.service.CampService
import com.ort.app.application.service.CharaService
import com.ort.app.application.service.PlayerService
import com.ort.app.application.service.VillageService
import com.ort.app.domain.model.player.canCreateVillage
import com.ort.app.domain.model.translate.TranslateRepository
import com.ort.app.domain.model.village.VillageQuery
import com.ort.app.domain.model.village.VillageStatus
import com.ort.app.fw.util.WolfMansionUserInfoUtil
import com.ort.dbflute.allcommon.CDef
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.ResponseBody
import java.time.LocalDateTime

@Controller
class IndexController(
    private val villageService: VillageService,
    private val playerService: PlayerService,
    private val charaService: CharaService,
    private val campService: CampService,
    private val translateRepository: TranslateRepository
) {

    @GetMapping("/")
    private fun index(form: LoginForm, model: Model): String {
        model.addAttribute("form", form)
        val villages = villageService.findVillages(
            query = VillageQuery(statuses = VillageStatus.notFinishedStatusList.map { VillageStatus(it) })
        )
        val userInfo = WolfMansionUserInfoUtil.getUserInfo()
        val player = userInfo?.let {
            playerService.findPlayer(it.username)
        }
        val canCreateVillage = player.canCreateVillage()
        val content = IndexContent(villages, canCreateVillage)
        model.addAttribute("content", content)
        return "index"
    }

    @GetMapping("/recruiting")
    @ResponseBody
    private fun recruiting(): RecruitingContent {
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
    @ResponseBody
    private fun villageRecordList( //
        form: VillageRecordListForm //
    ): VillageRecordListContent {
        var villages = villageService.findVillages(
            query = VillageQuery(
                statuses = listOf(
                    VillageStatus(CDef.VillageStatus.エピローグ),
                    VillageStatus(CDef.VillageStatus.終了),
                    VillageStatus(CDef.VillageStatus.廃村)
                ),
                ids = form.vid ?: emptyList()
            )
        )
        villages = villages.copy(list = villages.list.reversed())
        val players = playerService.findPlayers(
            villageIdList = villages.list.map { it.id }
        )
        return VillageRecordListContent(villages, players)
    }

    @GetMapping("/village-record/latest-vid")
    @ResponseBody
    private fun latestVillageRecordVid(): VillageRecordLatestVidContent {
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
    @ResponseBody
    private fun skillList(): SkillListContent {
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
}