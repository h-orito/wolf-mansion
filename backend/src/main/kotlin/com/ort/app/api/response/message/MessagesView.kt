package com.ort.app.api.response.message

import com.ort.app.domain.model.message.Messages
import io.swagger.v3.oas.annotations.media.Schema

@Schema(description = "発言一覧")
data class MessagesView(
    @field:Schema(description = "発言リスト (時系列昇順)")
    val list: List<MessageView>,
    @field:Schema(description = "総ページ数 (paging 無効なら 0)")
    val allPageCount: Int,
    @field:Schema(description = "現在のページ番号 (paging 無効なら null)")
    val currentPageNum: Int?,
    @field:Schema(description = "前ページがあるか")
    val isExistPrePage: Boolean,
    @field:Schema(description = "次ページがあるか")
    val isExistNextPage: Boolean,
    @field:Schema(description = "最新表示か")
    val isLatest: Boolean,
) {
    constructor(messages: Messages) : this(
        list = messages.list.map { MessageView(it) },
        allPageCount = messages.allPageCount,
        currentPageNum = messages.currentPageNum,
        isExistPrePage = messages.isExistPrePage,
        isExistNextPage = messages.isExistNextPage,
        isLatest = messages.isLatest,
    )
}
