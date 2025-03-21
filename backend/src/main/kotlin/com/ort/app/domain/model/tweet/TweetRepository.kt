package com.ort.app.domain.model.tweet

interface TweetRepository {

    fun tweet(msg: String, villageId: Int)
    fun tweet(msg: String)
}