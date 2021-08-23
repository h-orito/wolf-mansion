package com.ort.app.application.service

import com.ort.app.domain.model.player.Player
import com.ort.app.domain.model.player.PlayerRepository
import com.ort.app.domain.model.player.Players
import com.ort.app.web.exception.WolfMansionBusinessException
import org.springframework.stereotype.Service

@Service
class PlayerService(
    private val playerRepository: PlayerRepository
) {

    fun findPlayers(villageIdList: List<Int>): Players = playerRepository.findPlayers(villageIdList)

    fun findPlayer(userName: String): Player? = playerRepository.findPlayer(userName)

    @Throws(WolfMansionBusinessException::class)
    fun registerPlayer(userName: String, password: String): Player {
        findPlayer(userName)?.let { throw WolfMansionBusinessException("既に登録されているIDです。") }
        return playerRepository.registerPlayer(userName, password)
    }

    fun updatePassword(userName: String, password: String) =
        playerRepository.updatePassword(userName, password)
}