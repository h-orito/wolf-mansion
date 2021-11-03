package com.ort.app.api.view.village

import com.ort.app.domain.model.chara.Charachip
import com.ort.app.domain.model.village.Village
import com.ort.app.domain.model.village.participant.VillageParticipant

data class VillageRoomAssignedRow(
    val roomAssignedList: List<VillageRoomAssigned>
) {
    constructor(
        village: Village,
        day: Int,
        columnIndex: Int,
        charachip: Charachip,
        myself: VillageParticipant?
    ) : this(
        roomAssignedList = List(village.roomSize!!.width) { rowIndex ->
            val roomNumber = columnIndex * village.roomSize.width + rowIndex + 1
            VillageRoomAssigned(village, day, roomNumber, charachip, myself)
        }
    )

    data class VillageRoomAssigned(
        /** 部屋番号 0埋めの2桁 */
        var roomNumber: String,
        /** 参加者ID */
        val participantId: Int?,
        /** キャラ名 */
        val charaName: String?,
        /** キャラ名略称 */
        val charaShortName: String?,
        /** キャラ画像 */
        val charaImgUrl: String?,
        /** キャラ画像横幅 */
        val charaImgWidth: Int?,
        /** キャラ画像縦幅 */
        val charaImgHeight: Int?,
        /** ダミーか */
        val isDummy: Boolean?,
        /** 死亡しているか */
        val isDead: Boolean?,
        /** 死亡日時 */
        var deadDay: Int?,
        /** 死亡理由 */
        var deadReason: String?,
        /** 役職名 */
        val skillName: String?
    ) {
        constructor(
            village: Village,
            day: Int,
            roomNumber: Int,
            charachip: Charachip,
            myself: VillageParticipant?
        ) : this(
            roomNumber = roomNumber.toString().padStart(2, '0'),
            participantId = participant(village, roomNumber, day)?.id,
            charaName = participant(village, roomNumber, day)?.charaName?.name,
            charaShortName = participant(village, roomNumber, day)?.charaName?.shortName,
            charaImgUrl = participant(village, roomNumber, day)?.let {
                charachip.charas.chara(it.charaId).defaultImage().url
            },
            charaImgWidth = participant(
                village,
                roomNumber,
                day
            )?.let { charachip.charas.chara(it.charaId).size.width },
            charaImgHeight = participant(
                village,
                roomNumber,
                day
            )?.let { charachip.charas.chara(it.charaId).size.height },
            isDummy = village.dummyParticipant().id == participant(village, roomNumber, day)?.id,
            isDead = participant(village, roomNumber, day)?.isDeadWhen(day),
            deadDay = null,
            deadReason = null,
            skillName = mapSkillName(village, myself, participant(village, roomNumber, day))
        ) {
            participant(village, roomNumber, day)?.let {
                if (it.isDeadWhen(day)) {
                    deadDay = it.dead.deadDayWhen(day)
                    deadReason = it.dead.deadReasonWhen(day)!!.code
                }
            }
        }

        companion object {
            private fun participant(village: Village, roomNumber: Int, day: Int): VillageParticipant? =
                village.participants.findByRoomNumber(roomNumber, day)

            private fun mapSkillName(
                village: Village,
                myself: VillageParticipant?,
                participant: VillageParticipant?
            ): String? {
                participant ?: return null
                if (!isViewableMemberSkill(village, myself)) return null
                return participant.skill?.name
            }

            private fun isViewableMemberSkill(village: Village, myself: VillageParticipant?): Boolean {
                if (myself != null && myself.isAdmin()) return true
                if (village.status.isSettled()) return true
                return village.setting.rule.isOpenSkillInGrave &&
                        myself != null &&
                        (myself.dead.isDead || myself.isSpectator)
            }
        }

    }
}