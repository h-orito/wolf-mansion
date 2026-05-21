package com.ort.app.api

import com.ort.app.api.request.LoginForm
import com.ort.app.api.view.VillageListContent
import com.ort.app.api.view.player.PlayerView
import com.ort.app.application.service.CharaService
import com.ort.app.application.service.PlayerService
import com.ort.app.application.service.VillageService
import com.ort.app.domain.model.skill.Skills
import com.ort.app.domain.model.village.VillageQuery
import com.ort.dbflute.exbhv.PlayerBhv
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Controller
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.ResponseBody

@Controller
@CrossOrigin
class VillageApiController(
    private val villageService: VillageService,
    private val charaService: CharaService,
    private val playerBhv: PlayerBhv,
    private val playerService: PlayerService
) {
    // NOTE: 旧 `GET /api/village/{villageId}` (WholeVillageSituationsContent を返す JSON 端点) は
    // Step 6 で削除した。後継は `/api/v1/villages/{id}` (VillageDetailRestController)。
    // SpringDoc の OpenAPI 出力で旧 `VillageView` / `VillageParticipantView` と
    // 新 `api/response/village/` 配下の同名クラスがスキーマ名衝突を起こすため、
    // 旧端点と関連 view を一緒に整理した。

    // 村一覧初期表示
    @GetMapping("/api/village-list")
    @ResponseBody
    private fun villageList(): VillageListContent {
        val villages = villageService.findVillages(
            query = VillageQuery()
        )
        val charachips = charaService.findCharachips()
        val skills = Skills.all().filterNotSomeone()
        return VillageListContent(villages, charachips, skills)
    }

    @PostMapping("/api/login")
    @ResponseBody
    private fun index(@RequestBody @Validated body: LoginForm): PlayerView? {
        val optPl = playerBhv.selectEntity {
            it.query().setPlayerName_Equal(body.userId)
        }
        if (!optPl.isPresent) return null
        val isMatch = BCryptPasswordEncoder().matches(body.password, optPl.get().playerPassword)
        if (!isMatch) return null
        val player = playerService.findPlayer(body.userId!!)
        return PlayerView(player!!)
    }
}