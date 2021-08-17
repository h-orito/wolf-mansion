package com.ort.app.application.service

import com.ort.app.domain.model.player.Player
import com.ort.app.domain.model.player.PlayerRepository
import com.ort.app.domain.model.player.Players
import org.springframework.stereotype.Service

@Service
class PlayerService(
    private val playerRepository: PlayerRepository
) {

    fun findPlayers(villageIdList: List<Int>): Players = playerRepository.findPlayers(villageIdList)
    fun findPlayer(userName: String): Player? = playerRepository.findPlayer(userName)
}