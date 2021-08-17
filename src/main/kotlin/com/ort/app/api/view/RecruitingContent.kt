package com.ort.app.api.view

import com.ort.app.domain.model.chara.Charachips
import com.ort.app.domain.model.village.Village
import com.ort.app.domain.model.village.Villages
import java.time.format.DateTimeFormatter

data class RecruitingContent(
    /** 村リスト  */
    val villageList: List<RecruitingVillage>
) {

    constructor(villages: Villages, charachips: Charachips) : this(
        villageList = villages.list.map { RecruitingVillage(it, charachips) }
    )

    data class RecruitingVillage(
        /** 村ID  */
        private var villageId: Int,
        /** 村表示用番号  */
        val villageNumber: String,
        /** 村名  */
        val villageName: String,
        /** 人数  */
        val participateNum: String,
        /** 見学人数  */
        val spectateNum: String,
        /** 更新時間  */
        val daychangeDatetime: String,
        /** 更新間隔  */
        val daychangeInterval: String,
        /** キャラチップ  */
        val charaset: String,
        /** 発言制限  */
        val restrict: String,
        /** 状態  */
        val status: String,
        /** URL  */
        val url: String
    ) {
        constructor(village: Village, charachips: Charachips) : this(
            villageId = village.id,
            villageNumber = village.id.toString().padStart(4, '0'),
            villageName = village.name,
            participateNum = convertToParticipateNum(village),
            spectateNum = village.spectators.count.toString(),
            daychangeDatetime = village.days.latestDay().dayChangeDatetime.format(DateTimeFormatter.ofPattern("hh:mm")),
            daychangeInterval = convertToDaychangeInterval(village),
            charaset = charachips.list.first { it.id == village.setting.charachipId }.name,
            restrict = "",
            status = village.status.name,
            url = "https://wolfort.net/wolf-mansion/village/${village.id}"
        )

        companion object {
            private fun convertToParticipateNum(village: Village): String {
                val current = village.participants.count
                return if (village.status.isRecruiting()) {
                    val max = village.setting.personMax
                    "$current/$max"
                } else {
                    current.toString()
                }
            }

            private fun convertToDaychangeInterval(village: Village): String {
                val seconds = village.setting.dayChangeIntervalSeconds
                return if (seconds >= 3600) {
                    (seconds / 3600).toString() + "h"
                } else (seconds / 60).toString() + "m"
            }
        }
    }
}