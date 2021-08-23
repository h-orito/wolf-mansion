package com.ort.app.domain.model.player

interface PlayerRepository {

    fun findPlayers(villageIdList: List<Int>): Players

    fun findPlayer(userName: String): Player?

    fun registerPlayer(userName: String, password: String): Player

    fun updatePassword(userName: String, password: String)
}