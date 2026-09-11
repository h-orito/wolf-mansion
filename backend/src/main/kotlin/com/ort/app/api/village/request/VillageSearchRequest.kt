package com.ort.app.api.village.request

import com.ort.app.domain.model.skill.Skill
import com.ort.app.domain.model.village.VillageQuery
import com.ort.app.domain.model.village.toModel
import com.ort.app.fw.exception.WolfMansionBusinessException
import com.ort.dbflute.allcommon.CDef

/**
 * 村一覧 (`GET /api/v1/villages`) の検索条件。クエリパラメータをまとめて受け取り、
 * ドメインの [VillageQuery] への変換を担う。不正な code は [WolfMansionBusinessException] (= 400)。
 */
data class VillageSearchRequest(
    /** village_status の code 配列。未指定なら全状態。 */
    val status: List<String>? = null,
    /** キャラセット (CharaGroup) の id 配列。指定したキャラセットを含む村に絞る。 */
    val charachip: List<Int>? = null,
    /**
     * 役職 (CDef.Skill) の code 配列。指定した役職を含む村に絞る。
     * status とは排他で、skill 指定時はエピローグ以降 (募集中・進行中を除く) のみが対象になる。
     */
    val skill: List<String>? = null,
    /** 編成。true=闇鍋 / false=固定 / 未指定=両方。 */
    val random: Boolean? = null,
    /** 並び順。`asc`=村ID 昇順 / それ以外 (未指定含む)=降順 (新しい村が先)。 */
    val order: String? = null,
) {
    fun toQuery(): VillageQuery =
        VillageQuery(
            statuses = (status ?: emptyList()).map { toVillageStatus(it) },
            charachipIds = charachip ?: emptyList(),
            skills = (skill ?: emptyList()).map { toSkill(it) },
            isRandomOrg = random,
            isDescending = !"asc".equals(order, ignoreCase = true),
        )

    private fun toVillageStatus(code: String) =
        (CDef.VillageStatus.codeOf(code) ?: throw WolfMansionBusinessException("不正な status code です: $code")).toModel()

    private fun toSkill(code: String): Skill =
        Skill(CDef.Skill.codeOf(code) ?: throw WolfMansionBusinessException("不正な skill code です: $code"))
}
