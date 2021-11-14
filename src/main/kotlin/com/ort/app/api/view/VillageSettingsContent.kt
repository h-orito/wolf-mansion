package com.ort.app.api.view

import com.ort.app.domain.model.chara.Charachips
import com.ort.app.domain.model.village.Village

data class VillageSettingsContent(
    val villageId: Int,
    val villageName: String,
    val villageSettings: SettingsContent
) {

    constructor(village: Village, charachips: Charachips) : this(
        villageId = village.id,
        villageName = village.name,
        villageSettings = SettingsContent(village, charachips)
    )

    data class SettingsContent(
        /** キャラセット名 */
        val charaGroupName: String,
        /** ダミーキャラ名 */
        val dummyCharaName: String,
        /** 役職希望 */
        val skillRequestType: String
    ) {
        constructor(village: Village, charachips: Charachips) : this(
            charaGroupName = charachips.list.joinToString(separator = "、") { it.name },
            dummyCharaName = charachips.chara(village.setting.dummyCharaId).name,
            skillRequestType = if (village.setting.rule.isPossibleSkillRequest) "有効" else "無効"
        )
    }
}