package com.ort.app.domain.service

import com.ort.app.domain.model.situation.participant.ParticipantAdminSituation
import com.ort.app.domain.model.village.Village
import com.ort.app.domain.model.village.participant.VillageParticipant
import org.springframework.stereotype.Service

@Service
class AdminDomainService {

    fun convertToSituation(
        village: Village,
        myself: VillageParticipant?
    ): ParticipantAdminSituation = ParticipantAdminSituation(
        isAdmin = myself?.isAdmin() ?: false
    )
}