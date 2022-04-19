package com.ort.app.api.view

import com.ort.app.domain.model.chara.Chara
import com.ort.app.domain.model.chara.Charas
import com.ort.app.domain.model.player.CampRecord
import com.ort.app.domain.model.player.ParticipateVillage
import com.ort.app.domain.model.player.PlayerRecords
import com.ort.app.domain.model.player.Record
import com.ort.app.domain.model.player.SkillRecord
import com.ort.app.domain.model.village.participant.VillageParticipant

data class PlayerRecordsContent(
    /** 総合戦績  */
    val wholeStats: PlayerRecord,
    /** 陣営戦績  */
    val campStatsList: List<PlayerCampRecord>,
    /** 役職戦績  */
    val skillStatsList: List<PlayerSkillRecord>,
    /** 参加した村  */
    val participateVillageList: List<PlayerParticipateVillage>,
    /** 見学した村  */
    val spectateVillageList: List<PlayerParticipateVillage>
) {
    constructor(
        playerRecords: PlayerRecords,
        charas: Charas,
        originalCharas: Charas
    ) : this(
        wholeStats = PlayerRecord(playerRecords.wholeRecord),
        campStatsList = playerRecords.campRecordList.map { PlayerCampRecord(it) },
        skillStatsList = playerRecords.skillRecordList.map { PlayerSkillRecord(it) },
        participateVillageList = playerRecords.participateVillageList.filterNot {
            it.participant.isSpectator
        }.map { PlayerParticipateVillage(it, charas, originalCharas) },
        spectateVillageList = playerRecords.participateVillageList.filter {
            it.participant.isSpectator
        }.map { PlayerParticipateVillage(it, charas, originalCharas) }
    )

    data class PlayerRecord(
        /** 参加数  */
        val participateNum: Int,
        /** 勝利数  */
        val winNum: Int,
        /** 勝率  */
        val winRate: Float
    ) {
        constructor(
            record: Record
        ) : this(
            participateNum = record.participateCount,
            winNum = record.winCount,
            winRate = record.winRate
        )
    }

    data class PlayerCampRecord(
        /** 陣営名  */
        val campName: String,
        /** 戦績 */
        val stats: PlayerRecord
    ) {
        constructor(
            campRecord: CampRecord
        ) : this(
            campName = campRecord.camp.name,
            stats = PlayerRecord(campRecord.record)
        )
    }

    data class PlayerSkillRecord(
        /** 役職名  */
        val skillName: String,
        /** 戦績 */
        val stats: PlayerRecord
    ) {
        constructor(
            skillRecord: SkillRecord
        ) : this(
            skillName = skillRecord.skill.name,
            stats = PlayerRecord(skillRecord.record)
        )
    }

    data class PlayerParticipateVillage(
        /** 村ID  */
        val villageId: Int,
        /** 村名  */
        val villageName: String,
        /** キャラクター  */
        val characterName: String,
        /** キャラクター画像URL  */
        val characterImgUrl: String,
        /** キャラクター画像横幅  */
        val characterImgWidth: Int,
        /** キャラクター画像縦幅  */
        val characterImgHeight: Int,
        /** 役職名  */
        val skillName: String,
        /** 生死  */
        val liveStatus: String,
        /** 陣営名  */
        val campName: String,
        /** 勝敗  */
        val winStatus: String
    ) {
        constructor(
            participateVillage: ParticipateVillage,
            charas: Charas,
            originalCharas: Charas
        ) : this(
            villageId = participateVillage.village.id,
            villageName = participateVillage.village.name,
            characterName = participateVillage.participant.charaName.name,
            characterImgUrl = getChara(participateVillage, charas, originalCharas).defaultImage().url,
            characterImgWidth = getChara(participateVillage, charas, originalCharas).size.width,
            characterImgHeight = getChara(participateVillage, charas, originalCharas).size.height,
            skillName = participateVillage.participant.skill?.name.orEmpty(),
            liveStatus = convertToLiveStatus(participateVillage.participant).orEmpty(),
            campName = participateVillage.participant.camp?.name.orEmpty(),
            winStatus = participateVillage.participant.isWin?.let { if (it) "勝利" else "敗北" }.orEmpty()
        )

        companion object {
            private fun convertToLiveStatus(participant: VillageParticipant): String? {
                if (participant.isSpectator) return null
                if (!participant.dead.isDead) return "生存"
                val deadDay = participant.dead.deadDay!!
                val reason = participant.dead.reason!!.name
                return if (reason.endsWith("死")) "${deadDay}d $reason"
                else "${deadDay}d ${reason}死"
            }

            private fun getChara(
                participateVillage: ParticipateVillage,
                charas: Charas,
                originalCharas: Charas
            ): Chara {
                val charaId = participateVillage.participant.charaId
                return if (participateVillage.village.setting.chara.isOriginalCharachip) {
                    originalCharas.chara(charaId)
                } else charas.chara(charaId)
            }
        }
    }
}