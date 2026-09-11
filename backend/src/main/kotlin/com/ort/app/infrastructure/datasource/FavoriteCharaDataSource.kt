package com.ort.app.infrastructure.datasource

import com.ort.app.domain.model.favorite.FavoriteCharaRepository
import com.ort.dbflute.exbhv.PlayerFavoriteCharaBhv
import com.ort.dbflute.exentity.PlayerFavoriteChara
import org.dbflute.exception.EntityAlreadyExistsException
import org.springframework.stereotype.Repository

@Repository
class FavoriteCharaDataSource(
    private val playerFavoriteCharaBhv: PlayerFavoriteCharaBhv,
) : FavoriteCharaRepository {
    override fun findCharaIdsGroupedByCharachipId(playerId: Int): Map<Int, List<Int>> {
        val entityList =
            playerFavoriteCharaBhv.selectList {
                it.setupSelect_Chara()
                it.query().setPlayerId_Equal(playerId)
                it.query().addOrderBy_CharaId_Asc()
            }
        return entityList.groupBy({ it.chara.get().charaGroupId }, { it.charaId })
    }

    override fun add(
        playerId: Int,
        charaId: Int,
    ) {
        val entity = PlayerFavoriteChara()
        entity.playerId = playerId
        entity.charaId = charaId
        try {
            playerFavoriteCharaBhv.insert(entity)
        } catch (_: EntityAlreadyExistsException) {
            // 登録済み (二重リクエスト等)。冪等に成功扱いとする
        }
    }

    override fun delete(
        playerId: Int,
        charaId: Int,
    ) {
        playerFavoriteCharaBhv.queryDelete {
            it.query().setPlayerId_Equal(playerId)
            it.query().setCharaId_Equal(charaId)
        }
    }
}
