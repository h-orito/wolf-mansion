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
import com.ort.app.domain.model.village.VillageQuery
import com.ort.app.domain.model.village.VillageStatus
import com.ort.app.fw.util.WolfMansionUserInfoUtil
import com.ort.dbflute.allcommon.CDef
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.ResponseBody

@Controller
class IndexController(
    private val villageService: VillageService,
    private val playerService: PlayerService,
    private val charaService: CharaService,
    private val campService: CampService
) {

    companion object {
        private val aprilFoolDescriptions = listOf(
            "あなたは<strong>大根</strong>です。水と昆布と一緒に火にかけられることができます。",
            "あなたは<strong>Wi-Fiルーター</strong>です。毎晩一回再起動することで、周囲の通信環境を改善することができます。",
            "あなたは<strong>炊飯器</strong>です。毎朝、村人全員分のご飯を炊くことができます。ただし、おかずは出ません。",
            "あなたは<strong>扇風機</strong>です。あなた程度では、この厳しい夏を乗り切ることはできません。",
            "あなたは<strong>段ボール</strong>です。中に隠れることで、人狼の襲撃を1度だけ回避できますが、雨の日は使えません。",
            "あなたは<strong>カーナビ</strong>です。目的地までのルートと到着予定時刻を教えてくれますが、推理には役立ちません。",
            "あなたは<strong>鮭</strong>です。遡ることができます。",
            "あなたは<strong>デスチワワ</strong>です。徘徊すると、あなたと、通った部屋の人が全員死亡します。",
            "あなたは<strong>毒チワワ</strong>です。あなたを襲撃した人は死亡します。",
            "あなたは<strong>ネットリテラシー</strong>です。ねっとり照らすことができます。",
            "あなたは<strong>サイコ</strong>です。発言の語尾に「あ、薬は足りてます」が付きます。",
            "あなたは<strong>食欲</strong>です。パン屋や冷やし中華を自動的に襲撃します。",
            "あなたは<strong>睡眠欲</strong>です。寝ているので投票できません。",
            "あなたは<strong>性欲</strong>です。サイトが閉鎖に追い込まれるので下手なことはしないでください。",
            "あなたは<strong>花粉</strong>です。1日目に死亡します。",
            "あなたは<strong>墾田永年私財法</strong>です。一度だけ、墾田永年私財法ビームを発射することができます。",
            "あなたは<strong>風邪</strong>です。ベッドで寝ることができます。",
            "あなたは<strong>スマホ</strong>です。時々顔に落ちてきます。",
            "あなたは<strong>寺生まれのTさん</strong>です。占い能力はありませんが、指定した人が呪殺対象だった場合、呪殺させることができます。呪殺すると、「破ァ！！」と発言します。",
            "あなたは<strong>弊社</strong>です。7日目に爆発します。",
            "あなたは<strong>最終回</strong>です。全ての伏線を回収すると、OP曲を流しながら終わることができます。",
            "あなたは<strong>歯槽膿狼</strong>です。襲撃したら死亡します。",
            "あなたは<strong>液狼</strong>です。常温で液体の狼です。",
            "あなたのサークル「<strong>初日犠牲者</strong>」は部屋番号26に配置されました。",
            "あなたは<strong>収監</strong>されました。今日からは臭い飯しか食べられません。",
            "（ファミチキください）",
            "あなたは<strong>おじゃ魔女</strong>です。不思議な力が湧いて、毎日を日曜日に、学校の中を遊園地に、嫌な宿題をゴミ箱に捨ててしまうことができます。",
            "あなたは<strong>マジックカット</strong>です。こちら側のどこからでも切ることができます。",
            "あなたは<strong>ロマンスの神様</strong>です。対象を選んで、年齢、住所、趣味に職業をさりげなくチェックすることができます。"
        )
    }

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
        // エイプリルフール: 4/1にランダムなとんちんかん役職説明を表示
        val today = java.time.LocalDate.now()
        if (today.monthValue == 4 && today.dayOfMonth == 1) {
            model.addAttribute("aprilFoolDescription", aprilFoolDescriptions.random())
        }
        return "index"
    }

    @GetMapping("/archives/april-20250401")
    fun april20250401(form: LoginForm, model: Model): String {
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

        return "april20250401"
    }

    @GetMapping("/archives/april-20250402")
    fun april20250402(form: LoginForm, model: Model): String {
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
        return "april20250402"
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