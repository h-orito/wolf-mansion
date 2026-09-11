package com.ort.app.application.service

import com.ort.app.domain.model.chara.CharaRepository
import com.ort.app.domain.model.chara.Charachips
import com.ort.app.domain.model.chara.Charas
import com.ort.app.domain.model.favorite.FavoriteCharaRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class FavoriteCharaService(
    private val favoriteCharaRepository: FavoriteCharaRepository,
    private val charaRepository: CharaRepository,
) {
    /** お気に入りキャラをキャラチップ単位で返す (各キャラチップの charas はお気に入りのみに絞る)。 */
    fun findFavoriteCharachips(playerId: Int): Charachips {
        val charaIdsByCharachipId = favoriteCharaRepository.findCharaIdsGroupedByCharachipId(playerId)
        if (charaIdsByCharachipId.isEmpty()) return Charachips(emptyList())
        val charachips = charaRepository.findCharachips(charaIdsByCharachipId.keys.toList(), false)
        return Charachips(
            charachips.list.map { charachip ->
                val favoriteCharaIds = charaIdsByCharachipId[charachip.id].orEmpty().toSet()
                charachip.copy(charas = Charas(charachip.charas.list.filter { favoriteCharaIds.contains(it.id) }))
            },
        )
    }

    @Transactional
    fun addFavoriteChara(
        playerId: Int,
        charaId: Int,
    ) = favoriteCharaRepository.add(playerId, charaId)

    @Transactional
    fun deleteFavoriteChara(
        playerId: Int,
        charaId: Int,
    ) = favoriteCharaRepository.delete(playerId, charaId)
}
