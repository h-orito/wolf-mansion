package com.ort.app.domain.service

import com.ort.app.domain.model.chara.Chara
import com.ort.app.domain.model.chara.Charachip
import com.ort.app.domain.model.player.Player
import com.ort.app.domain.model.situation.participant.ParticipantParticipateSituation
import com.ort.app.domain.model.village.Village
import com.ort.app.domain.model.village.participant.VillageParticipant
import org.springframework.stereotype.Service

@Service
class ParticipateDomainService {

    fun convertToSituation(
        village: Village,
        myself: VillageParticipant?,
        player: Player?,
        charachip: Charachip
    ): ParticipantParticipateSituation {
        return ParticipantParticipateSituation(
            isParticipating = myself != null,
            isAvailableParticipate = isAvailableParticipate(player, village),
            isAvailableSpectate = isAvailableSpectate(player, village, charachip),
            selectableCharaList = getSelectableCharaList(village, charachip),
            isAvailableLeave = isAvailableLeave(village, myself),
            myself = myself
        )
    }

    private fun isAvailableParticipate(player: Player?, village: Village): Boolean {
        player ?: return false
        return player.isAvailableParticipateVillage(village.id) &&
                village.canParticipate()
    }

    private fun isAvailableSpectate(player: Player?, village: Village, charachip: Charachip): Boolean {
        player ?: return false
        return player.isAvailableParticipateVillage(village.id) &&
                village.canSpectate(charachip.charas.list.size)
    }

    private fun getSelectableCharaList(village: Village, charachip: Charachip): List<Chara> {
        return charachip.charas.list.filterNot { chara ->
            village.allParticipants().list.any { it.charaId == chara.id }
        }
    }

    private fun isAvailableLeave(village: Village, myself: VillageParticipant?): Boolean {
        return myself != null && village.canLeave()
    }

    fun assertParticipate(
        village: Village,
        player: Player,
        charaId: Int,
        joinPassword: String?
    ) {
        player.assertParticipate(village.id)
        village.assertParticipate(charaId, joinPassword)
    }

    fun assertSpectate(
        village: Village,
        player: Player,
        charaId: Int,
        joinPassword: String?,
        charachip: Charachip
    ) {
        player.assertSpectate(village.id)
        village.assertSpectate(charaId, joinPassword, charachip.charas.list.size)
    }

    fun assertLeave(village: Village, myself: VillageParticipant) = village.assertLeave()
}