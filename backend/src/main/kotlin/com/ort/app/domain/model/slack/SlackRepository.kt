package com.ort.app.domain.model.slack

interface SlackRepository {

    fun post(villageId: Int, day: Int, message: String)
}