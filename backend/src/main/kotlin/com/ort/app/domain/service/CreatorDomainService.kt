package com.ort.app.domain.service

import com.ort.app.domain.model.player.Player
import com.ort.app.domain.model.situation.participant.ParticipantCreatorSituation
import com.ort.app.domain.model.village.Village
import org.springframework.stereotype.Service

@Service
class CreatorDomainService {

    fun convertToSituation(
        village: Village,
        player: Player?
    ): ParticipantCreatorSituation {
        return ParticipantCreatorSituation(
            isCreator = isCreator(village, player),
            isAvailableCreatorSay = isAvailableCreatorSay(village, player),
            isAvailableCancelVillage = isAvailableCancelVillage(village, player),
            isAvailableKick = isAvailableKick(village, player),
            isAvailableModifySetting = isAvailableModifySetting(village, player),
            isAvailableExtendEpilogue = isAvailableExtendEpilogue(village, player)
        )
    }

    private fun isCreator(
        village: Village,
        player: Player?
    ): Boolean = player?.id == 1 || village.createPlayerName == player?.name

    private fun isAvailableCreatorSay(village: Village, player: Player?): Boolean =
        isCreator(village, player) && village.isSayableCreatorSay()

    private fun isAvailableCancelVillage(village: Village, player: Player?): Boolean =
        isCreator(village, player) && village.canCancel()

    private fun isAvailableKick(village: Village, player: Player?): Boolean =
        isCreator(village, player) && village.canKick()

    private fun isAvailableModifySetting(village: Village, player: Player?): Boolean =
        isCreator(village, player) && village.canModifySetting()

    private fun isAvailableExtendEpilogue(village: Village, player: Player?): Boolean =
        isCreator(village, player) && village.canExtendEpilogue()
}