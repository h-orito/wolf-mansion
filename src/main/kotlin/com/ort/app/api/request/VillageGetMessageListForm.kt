package com.ort.app.api.request

import com.ort.app.domain.model.message.MessageQuery
import com.ort.app.domain.model.village.Village
import javax.validation.constraints.NotNull

data class VillageGetMessageListForm(
    /** 村ID */
    @NotNull
    val villageId: Int? = null,

    /** 何日目か */
    val day: Int? = null,

    /** 1ページあたりの表示発言数 */
    val pageSize: Int? = null,

    /** 何ページ目か */
    val pageNum: Int? = null,

    /** 自分宛に絞るか */
    val onlyToMe: Boolean? = null
) {
    fun toMessageQuery(village: Village) = MessageQuery(
        village = village,
        day = day ?: village.latestDay(),
        pageSize = pageSize,
        pageNum = pageNum,
        onlyToMe = onlyToMe ?: false
    )
}