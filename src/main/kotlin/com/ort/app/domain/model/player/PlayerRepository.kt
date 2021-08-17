package com.ort.app.domain.model.player

interface PlayerRepository {

    fun findPlayers(villageIdList: List<Int>): Players

    fun findPlayer(userName: String): Player?
}