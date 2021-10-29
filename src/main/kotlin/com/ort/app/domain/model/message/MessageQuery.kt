package com.ort.app.domain.model.message

import com.ort.app.domain.model.village.Village

data class MessageQuery(
    val village: Village,
    val day: Int,
    val pageSize: Int?,
    val pageNum: Int?,
    val onlyToMe: Boolean,
    // 後からセットする
    var isAllViewable: Boolean = false,
    var messageTypeList: List<MessageType> = emptyList(),
    var personalMessageTypeList: List<MessageType> = emptyList()
)