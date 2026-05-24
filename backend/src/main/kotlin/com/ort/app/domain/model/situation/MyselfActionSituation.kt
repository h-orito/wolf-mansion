package com.ort.app.domain.model.situation

import com.ort.app.domain.model.situation.participant.ParticipantAbilitySituation
import com.ort.app.domain.model.situation.participant.ParticipantCommitSituation
import com.ort.app.domain.model.situation.participant.ParticipantRpSituation
import com.ort.app.domain.model.situation.participant.ParticipantSaySituation
import com.ort.app.domain.model.situation.participant.ParticipantVoteSituation

data class MyselfActionSituation(
    val commit: ParticipantCommitSituation,
    val vote: ParticipantVoteSituation,
    val ability: ParticipantAbilitySituation,
    val rp: ParticipantRpSituation,
    val say: ParticipantSaySituation,
)
