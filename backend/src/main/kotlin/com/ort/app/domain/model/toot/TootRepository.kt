package com.ort.app.domain.model.toot

interface TootRepository {

    fun toot(msg: String, villageId: Int)
    fun toot(msg: String)
}