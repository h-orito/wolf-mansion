package com.ort.app.api.view

import com.ort.app.domain.model.chara.Charachip
import com.ort.app.domain.model.chara.Charachips

data class CharaGroupListContent(
    val charaGroupList: List<CharaGroupListCharaGroup>
) {
    constructor(
        charachips: Charachips
    ) : this(
        charaGroupList = charachips.list.map { CharaGroupListCharaGroup(it) }
    )

    data class CharaGroupListCharaGroup(
        /** キャラグループID  */
        val charaGroupId: Int,
        /** キャラグループ名  */
        val charaGroupName: String,
        /** 作者名  */
        val designerName: String,
        /** キャラチップ数  */
        val charaNum: Int,
        /** ダミーキャラ画像URL  */
        val dummyImgUrl: String,
        /** ダミーキャラ画像横幅  */
        val dummyImgWidth: Int,
        /** ダミーキャラ画像縦幅  */
        val dummyImgHeight: Int
    ) {
        constructor(
            charachip: Charachip
        ) : this(
            charaGroupId = charachip.id,
            charaGroupName = charachip.name,
            designerName = charachip.designer!!.name,
            charaNum = charachip.charas.list.size,
            dummyImgUrl = charachip.dummyChara().images.list.first().url,
            dummyImgWidth = charachip.dummyChara().size.width,
            dummyImgHeight = charachip.dummyChara().size.height
        )
    }
}