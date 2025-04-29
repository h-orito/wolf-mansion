package com.ort.app.domain.model.player

interface PlayerRepository {

    fun findPlayers(pageSize: Int, pageNum: Int, playerName: String?): Players

    fun findPlayers(villageIdList: List<Int>): Players

    fun findPlayers(villageId: Int): Players

    fun findPlayer(userName: String): Player?

    fun findPlayer(id: Int): Player

    fun registerPlayer(userName: String, password: String): Player

    fun updatePassword(userName: String, password: String)

    fun updateDetail(userName: String, twitterUserName: String?, introduction: String?)

    fun updateDifference(current: Players, changed: Players)
}