package com.ort.app.api.village

import com.ort.app.api.village.response.VillageListResponse
import com.ort.app.application.service.VillageService
import com.ort.app.domain.model.skill.Skill
import com.ort.app.domain.model.village.VillageQuery
import com.ort.app.domain.model.village.toModel
import com.ort.app.fw.exception.WolfMansionBusinessException
import com.ort.dbflute.allcommon.CDef
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

/**
 * 村一覧の REST (公開)。複数画面で共有する (トップページ = 未終了村、村一覧画面 = 全村 + 絞り込み)。
 * 状態・キャラセット・役職・編成で絞り込める。村作成可否は player の情報なので本 API では返さない (me の `canCreateVillage`)。
 *
 * 注: 役職 (skill) 絞り込みは既存ドメイン挙動 (`VillageRepository.findVillages`) を踏襲する。
 * status と skill は**排他**で、両方指定すると status が優先され skill は無視される。また skill 単独指定時は
 * 役職ネタバレ防止のため**エピローグ以降の村** (募集中・進行中を除く = エピローグ/終了/廃村) のみが対象になる
 * (村一覧画面は status を送らず charachip/skill/random を送る)。
 */
@RestController
@RequestMapping("/api/v1/villages")
class VillageRestController(
    private val villageService: VillageService,
) {
    /**
     * 村一覧を返す。
     * @param status village_status の code 配列 (`?status=IN_PREPARATION&status=IN_PROGRESS` のように指定)。
     *   省略時は全件。トップは未終了 (IN_PREPARATION/IN_PROGRESS/EPILOGUE) を指定して取得する。
     * @param charachip キャラセット (CharaGroup) の id 配列。指定したキャラセットを含む村に絞る。
     * @param skill 役職 (CDef.Skill) の code 配列。指定した役職を含む村に絞る (status とは排他・エピローグ以降のみ)。
     * @param random 編成。`true`=闇鍋 / `false`=固定 / 省略=両方。
     */
    @GetMapping
    fun list(
        @RequestParam(name = "status", required = false) status: List<String>?,
        @RequestParam(name = "charachip", required = false) charachip: List<Int>?,
        @RequestParam(name = "skill", required = false) skill: List<String>?,
        @RequestParam(name = "random", required = false) random: Boolean?,
    ): VillageListResponse {
        val villages =
            villageService.findVillages(
                query =
                    VillageQuery(
                        statuses = (status ?: emptyList()).map { toVillageStatus(it) },
                        charachipIds = charachip ?: emptyList(),
                        skills = (skill ?: emptyList()).map { toSkill(it) },
                        isRandomOrg = random,
                    ),
            )
        return VillageListResponse(villages)
    }

    private fun toVillageStatus(code: String) =
        (CDef.VillageStatus.codeOf(code) ?: throw WolfMansionBusinessException("不正な status code です: $code")).toModel()

    private fun toSkill(code: String): Skill =
        Skill(CDef.Skill.codeOf(code) ?: throw WolfMansionBusinessException("不正な skill code です: $code"))
}
