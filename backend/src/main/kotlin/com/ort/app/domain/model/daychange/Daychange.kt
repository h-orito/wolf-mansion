package com.ort.app.domain.model.daychange

import com.ort.app.domain.model.ability.Abilities
import com.ort.app.domain.model.footstep.Footsteps
import com.ort.app.domain.model.message.Messages
import com.ort.app.domain.model.player.Players
import com.ort.app.domain.model.village.Village
import com.ort.app.domain.model.village.participant.VillageParticipant
import com.ort.app.domain.model.vote.Votes

data class Daychange(
    val village: Village,
    val abilities: Abilities,
    val votes: Votes,
    val footsteps: Footsteps,
    val messages: Messages,
    val players: Players,
    val tweets: List<String>,
    val guarded: List<VillageParticipant>
) {
    constructor(
        village: Village,
        abilities: Abilities,
        votes: Votes,
        footsteps: Footsteps,
        players: Players
    ) : this(
        village = village.copy(
            participants = village.participants.filterNotGone(),
            spectators = village.spectators.filterNotGone()
        ),
        abilities = abilities,
        votes = votes,
        footsteps = footsteps,
        messages = Messages(emptyList()),
        players = players,
        tweets = emptyList(),
        guarded = emptyList()
    )

    fun isChanged(other: Daychange): Boolean = !isSame(other)

    fun isSame(other: Daychange): Boolean =
        village.isSame(other.village)
                && abilities.isSame(other.abilities)
                && votes.isSame(other.votes)
                && footsteps.isSame(other.footsteps)
                && messages.isSame(other.messages)
                && players.isSame(other.players)
}