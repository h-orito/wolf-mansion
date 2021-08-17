package com.ort.app.api.view

import com.ort.app.domain.model.chara.Chara
import com.ort.app.domain.model.chara.Charachip

data class CharaGroupContent(
    /** キャラグループID  */
    val charaGroupId: Int,
    /** キャラグループ名  */
    val charaGroupName: String,
    /** 作者名  */
    val designerName: String,
    /** キャラチップURL  */
    val descriptionUrl: String,
    /** キャラリスト  */
    val charaList: List<CharaGroupChara>,
    /** 名称変更可能か  */
    val isAvailableChangeName: Boolean
) {
    constructor(
        charachip: Charachip
    ) : this(
        charaGroupId = charachip.id,
        charaGroupName = charachip.name,
        designerName = charachip.designer.name,
        descriptionUrl = charachip.descriptionUrl,
        charaList = charachip.charas.list.map { CharaGroupChara(it) },
        isAvailableChangeName = charachip.isAvailableChangeName
    )

    data class CharaGroupChara(
        /** キャラID  */
        val charaId: Int,
        /** キャラ名  */
        val charaName: String,
        /** キャラ名略称  */
        val charaShortName: String,
        /** キャラ画像URLリスト  */
        val charaImgUrlList: List<String>,
        /** キャラ画像横幅  */
        val charaImgWidth: Int,
        /** キャラ画像縦幅  */
        val charaImgHeight: Int
    ) {
        constructor(
            chara: Chara
        ) : this(
            charaId = chara.id,
            charaName = chara.name,
            charaShortName = chara.shortName,
            charaImgUrlList = chara.images.list.map { it.url },
            charaImgWidth = chara.size.width,
            charaImgHeight = chara.size.height
        )
    }
}