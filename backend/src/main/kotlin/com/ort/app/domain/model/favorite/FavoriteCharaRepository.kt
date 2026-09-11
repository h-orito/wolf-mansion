package com.ort.app.domain.model.favorite

interface FavoriteCharaRepository {
    /** お気に入りキャラの charaId をキャラチップID単位でまとめて返す。 */
    fun findCharaIdsGroupedByCharachipId(playerId: Int): Map<Int, List<Int>>

    /** 登録済みでも成功扱い (冪等)。 */
    fun add(
        playerId: Int,
        charaId: Int,
    )

    /** 未登録でも成功扱い (冪等)。 */
    fun delete(
        playerId: Int,
        charaId: Int,
    )
}
