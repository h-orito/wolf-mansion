package com.ort.app.domain.model.situation

import com.ort.app.domain.model.situation.participant.ParticipantAbilitySituation
import com.ort.app.domain.model.situation.participant.ParticipantAdminSituation
import com.ort.app.domain.model.situation.participant.ParticipantCommitSituation
import com.ort.app.domain.model.situation.participant.ParticipantCreatorSituation
import com.ort.app.domain.model.situation.participant.ParticipantParticipateSituation
import com.ort.app.domain.model.situation.participant.ParticipantRpSituation
import com.ort.app.domain.model.situation.participant.ParticipantSaySituation
import com.ort.app.domain.model.situation.participant.ParticipantSkillRequestSituation
import com.ort.app.domain.model.situation.participant.ParticipantVoteSituation

data class ParticipantSituation(
    val participate: ParticipantParticipateSituation,
    val skillRequest: ParticipantSkillRequestSituation,
    val commit: ParticipantCommitSituation,
    val say: ParticipantSaySituation,
    val rp: ParticipantRpSituation,
    val ability: ParticipantAbilitySituation,
    val vote: ParticipantVoteSituation,
    val admin: ParticipantAdminSituation,
    val creator: ParticipantCreatorSituation
)