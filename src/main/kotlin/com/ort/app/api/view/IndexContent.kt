package com.ort.app.api.view

import com.ort.app.domain.model.village.Village
import com.ort.app.domain.model.village.Villages

data class IndexContent(
    /** 村リスト  */
    val villageList: List<IndexVillage>,
    /** どこかの村に参加中か  */
    val isParticipate: Boolean
) {

    constructor(
        villages: Villages,
        canCreateVillage: Boolean
    ) : this(
        villageList = villages.list.map { IndexVillage(it) },
        isParticipate = !canCreateVillage
    )

    data class IndexVillage(
        /** 村ID  */
        val villageId: Int,
        /** 村表示用番号  */
        val villageNumber: String,
        /** 村名  */
        val villageName: String,
        /** 人数  */
        val participateNum: String,
        /** 状態  */
        val status: String
    ) {
        constructor(
            village: Village
        ) : this(
            villageId = village.id,
            villageNumber = village.id.toString().padStart(4, '0'),
            villageName = village.name,
            participateNum = convertToParticipateNumStr(village),
            status = village.status.name
        )

        companion object {
            private fun convertToParticipateNumStr(village: Village): String {
                val participateNum = village.participants.count
                val spectatorNum = village.spectators.count
                val spectatorStr = if (spectatorNum <= 0) "" else " ($spectatorNum)"
                return if (village.status.isPrologue()) {
                    val maxNum = village.setting.personMax
                    "$participateNum/$maxNum${spectatorStr}人"
                } else {
                    "$participateNum${spectatorStr}人"
                }
            }
        }
    }
}