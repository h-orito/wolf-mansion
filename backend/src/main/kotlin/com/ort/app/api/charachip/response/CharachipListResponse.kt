package com.ort.app.api.charachip.response

import com.ort.app.domain.model.chara.Charachip
import com.ort.app.domain.model.chara.Charachips

data class CharachipListResponse(
    val charachips: List<SimpleCharachipView>,
) {
    constructor(charachips: Charachips) : this(charachips = charachips.list.map { SimpleCharachipView(it) })
}

data class SimpleCharachipView(
    val id: Int,
    val name: String,
    val designerName: String,
    val charaNum: Int,
    val dummyImgUrl: String,
    val dummyImgWidth: Int,
    val dummyImgHeight: Int,
) {
    constructor(charachip: Charachip) : this(
        id = charachip.id,
        name = charachip.name,
        designerName = charachip.designer?.name ?: "",
        charaNum = charachip.charas.list.size,
        dummyImgUrl = charachip.dummyChara().defaultImage().url,
        dummyImgWidth = charachip.dummyChara().size.width,
        dummyImgHeight = charachip.dummyChara().size.height,
    )
}
