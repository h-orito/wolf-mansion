package com.ort.app.api.view.village

import com.ort.app.domain.model.village.VillageSetting
import com.ort.app.domain.model.village.setting.VillageCharaSetting
import com.ort.app.domain.model.village.setting.VillageOrganize
import com.ort.app.domain.model.village.setting.VillageRule

data class VillageSettingView(
    val chara: VillageCharaSetting,
    val rule: VillageRule,
    val organize: VillageOrganize,
) {
    constructor(
        org: VillageSetting
    ) : this(
        chara = org.chara,
        rule = org.rule,
        organize = org.organize
    )
}