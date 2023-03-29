package com.ort.app.api

import com.ort.app.api.request.LoginForm
import com.ort.app.api.request.VillageRecordListForm
import com.ort.app.api.view.*
import com.ort.app.application.service.CampService
import com.ort.app.application.service.CharaService
import com.ort.app.application.service.PlayerService
import com.ort.app.application.service.VillageService
import com.ort.app.domain.model.player.canCreateVillage
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
    private val campService: CampService
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

        // エイプリルフールネタ
        val now = LocalDateTime.now()
        if (LocalDateTime.of(2023, 4, 1, 0, 0, 0).isBefore(now)
            && LocalDateTime.of(2023, 4, 2, 0, 0, 0).isAfter(now)
        ) {
            val charachip = charaService.findCharachip(1, false)
            model.addAttribute("stuffs", charachip!!.charas.list.take(20).map {
                IndexChara(
                    it.name.substringAfter(" "),
                    it.defaultImage().url,
                    it.size.width,
                    it.size.height,
                    comments[it.id - 1].padEnd(40, '　')
                )
            })
            return "april-fool"
        }

        return "index"
    }

    data class IndexChara(
        /** キャラ名 */
        val name: String,
        /** ダミーキャラ画像URL  */
        val imgUrl: String,
        /** ダミーキャラ画像横幅  */
        val imgWidth: Int,
        /** ダミーキャラ画像縦幅  */
        val imgHeight: Int,
        val comment: String
    )

    private val comments = listOf(
        "快適に睡眠できるマンションを紹介します",
        "おう兄ちゃん、いいブツ（物件）入ってるぜ",
        "老後も安心のマンションを紹介するぞい",
        "近所に美女がいる部屋を紹介しますよ",
        "何？いいマンションがない？作ればいいじゃねえか",
        "諸経費が安いマンションを紹介するよ",
        "うるさくしてもいいマンション？あるぜ",
        "ちかくに公園があるおへやがいいな",
        "かわいいおへや？いっぱいあるの！",
        "ヒヒ...お客さんいいの（物件）ありますぜ",
        "ペット可の物件を紹介するねー！",
        "おいしいパン屋が近くにある物件を紹介しましょう",
        "んあ？静かに寝られる物件なら詳しいよ",
        "オシャな街の物件はまかせてちょうだい",
        "農業がしたい？田舎はワシにまかせるべ",
        "ここは近くに安いスーパーがないからやめときな！！",
        "近所に神父さんのような変態がいない部屋を紹介します",
        "おしゃれな服屋さんが近くにあると楽しいですよ",
        "ヲタ活しやすい地域に自信ネキです",
        "VRを楽しむには広い部屋が必要だよ"
    )

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