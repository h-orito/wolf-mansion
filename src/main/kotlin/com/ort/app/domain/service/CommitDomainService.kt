package com.ort.app.domain.service

import com.ort.app.domain.model.commit.Commits
import com.ort.app.domain.model.message.Message
import com.ort.app.domain.model.message.toModel
import com.ort.app.domain.model.situation.participant.ParticipantCommitSituation
import com.ort.app.domain.model.village.Village
import com.ort.app.domain.model.village.participant.VillageParticipant
import com.ort.app.fw.exception.WolfMansionBusinessException
import com.ort.dbflute.allcommon.CDef
import org.springframework.stereotype.Service

@Service
class CommitDomainService {

    fun convertToSituation(
        village: Village,
        myself: VillageParticipant?,
        commits: Commits
    ): ParticipantCommitSituation = ParticipantCommitSituation(
        isAvailableCommit = isAvailableCommit(village, myself),
        isCommitting = commits.list.any { it.day == village.latestDay() && it.myselfId == myself?.id }
    )

    private fun isAvailableCommit(village: Village, myself: VillageParticipant?): Boolean {
        myself ?: return false
        return village.canCommit() && myself.isAvailableCommit(village.dummyParticipant().id)
    }

    fun assertCommit(village: Village, myself: VillageParticipant) {
        if (!isAvailableCommit(village, myself)) {
            throw WolfMansionBusinessException("コミットできません")
        }
    }

    fun createSetMessage(village: Village, myself: VillageParticipant, commit: Boolean): Message {
        return Message.ofSystemMessage(
            day = village.latestDay(),
            message = "${myself.name()}がコミット${if (commit) "" else "を取り消"}しました。",
            messageType = CDef.MessageType.非公開システムメッセージ.toModel(),
        )
    }
}