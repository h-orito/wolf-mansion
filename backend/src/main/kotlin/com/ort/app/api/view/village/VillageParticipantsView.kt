package com.ort.app.api.view.village

import com.ort.app.domain.model.chara.Chara
import com.ort.app.domain.model.chara.Charachips
import com.ort.app.domain.model.player.Players
import com.ort.app.domain.model.village.participant.VillageParticipants

data class VillageParticipantsView(
    val count: Int,
    val list: List<VillageParticipantView>,
) {
    /** SSR 互換 */
    constructor(
        org: VillageParticipants,
        participantIdToChara: Map<Int, Chara>,
    ) : this(
        count = org.count,
        list = org.list.map { VillageParticipantView(it, participantIdToChara) },
    )

    /** REST API 向け。エピローグ以降は役職・陣営・プレイヤー・勝敗を公開する */
    constructor(
        org: VillageParticipants,
        charachips: Charachips,
        shouldHidePrivate: Boolean,
        players: Players? = null,
    ) : this(
        count = org.count,
        list =
            org.list.map {
                VillageParticipantView(
                    it,
                    charachips.chara(it.charaId),
                    shouldHidePrivate,
                    player = players?.list?.firstOrNull { p -> p.id == it.playerId },
                )
            },
    )
}
