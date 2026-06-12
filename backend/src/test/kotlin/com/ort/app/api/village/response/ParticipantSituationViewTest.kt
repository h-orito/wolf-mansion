package com.ort.app.api.village.response

import com.ort.app.domain.model.message.MessageType
import com.ort.app.domain.model.situation.ParticipantSituation
import com.ort.app.domain.model.situation.participant.ParticipantAbilitySituation
import com.ort.app.domain.model.situation.participant.ParticipantAdminSituation
import com.ort.app.domain.model.situation.participant.ParticipantCommitSituation
import com.ort.app.domain.model.situation.participant.ParticipantCreatorSituation
import com.ort.app.domain.model.situation.participant.ParticipantParticipateSituation
import com.ort.app.domain.model.situation.participant.ParticipantRpSituation
import com.ort.app.domain.model.situation.participant.ParticipantSaySituation
import com.ort.app.domain.model.situation.participant.ParticipantSkillRequestSituation
import com.ort.app.domain.model.situation.participant.ParticipantVoteSituation
import com.ort.app.domain.model.skill.Skill
import com.ort.app.domain.model.village.createDay1Village
import com.ort.app.domain.model.village.createVillageParticipant
import com.ort.dbflute.allcommon.CDef
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

internal class ParticipantSituationViewTest {
    @Test
    fun `未参加でもフラグがそのまま写像される`() {
        val view = ParticipantSituationView(createSituation(myselfParticipant = null), createDay1Village())

        assertNull(view.myself)
        assertFalse(view.participate.isParticipating)
        assertTrue(view.participate.isAvailableParticipate)
        assertFalse(view.commit.isAvailableCommit)
        assertFalse(view.say.isAvailableSay)
        assertFalse(view.vote.canVote)
        assertFalse(view.admin.isAdmin)
        assertFalse(view.creator.isCreator)
    }

    @Test
    fun `参加中は本人情報が返る`() {
        val participant = createVillageParticipant(skill = Skill(CDef.Skill.村人), id = 2)
        val view =
            ParticipantSituationView(
                createSituation(myselfParticipant = participant, isParticipating = true),
                createDay1Village(),
            )

        assertEquals(participant.id, view.myself!!.id)
        assertEquals(participant.charaId, view.myself!!.charaId)
        assertEquals(participant.name(), view.myself!!.name)
        assertEquals(participant.shortName(), view.myself!!.shortName)
        assertFalse(view.myself!!.isDead)
        assertFalse(view.myself!!.isSpectator)
        assertTrue(view.participate.isParticipating)
    }

    private fun createSituation(
        myselfParticipant: com.ort.app.domain.model.village.participant.VillageParticipant?,
        isParticipating: Boolean = false,
    ): ParticipantSituation =
        ParticipantSituation(
            participate =
                ParticipantParticipateSituation(
                    isParticipating = isParticipating,
                    isAvailableParticipate = true,
                    isAvailableSpectate = false,
                    isAvailableSwitchParticipate = false,
                    selectableCharachipList = emptyList(),
                    selectableCharaList = emptyList(),
                    isAvailableLeave = false,
                    myself = myselfParticipant,
                ),
            skillRequest =
                ParticipantSkillRequestSituation(
                    isAvailableSkillRequest = false,
                    selectableSkillList = emptyList(),
                    skillRequest = null,
                ),
            commit = ParticipantCommitSituation(isAvailableCommit = false, isCommitting = false),
            say =
                ParticipantSaySituation(
                    isAvailableSay = false,
                    selectableMessageTypeList = emptyList(),
                    selectableCharaImageList = emptyList(),
                    defaultMessageType = MessageType(CDef.MessageType.通常発言),
                ),
            rp =
                ParticipantRpSituation(
                    isAvailableChangeName = false,
                    isAvailableMemo = false,
                    canAddImage = false,
                ),
            ability =
                ParticipantAbilitySituation(
                    canUseAbility = false,
                    type = null,
                    targetList = emptyList(),
                    targetFootstepList = emptyList(),
                    attacker = null,
                    target = null,
                    targetFootstep = null,
                    targetingMessage = null,
                    footstep = null,
                    isAvailableNoTarget = false,
                    attackerList = emptyList(),
                    skillHistoryList = emptyList(),
                    wolfList = emptyList(),
                    cMadmanList = emptyList(),
                    foxList = emptyList(),
                    loversList = emptyList(),
                    masonsList = emptyList(),
                    listenMasonsList = emptyList(),
                    targetPrefix = null,
                    targetSuffix = null,
                    isTargetingAndFootstep = false,
                ),
            vote = ParticipantVoteSituation(canVote = false, targetList = emptyList(), target = null),
            admin = ParticipantAdminSituation(isAdmin = false),
            creator =
                ParticipantCreatorSituation(
                    isCreator = false,
                    isAvailableCreatorSay = false,
                    isAvailableCancelVillage = false,
                    isAvailableKick = false,
                    isAvailableModifySetting = false,
                    isAvailableExtendEpilogue = false,
                ),
        )
}
