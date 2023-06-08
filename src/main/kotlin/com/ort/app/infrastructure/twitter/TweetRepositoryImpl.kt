package com.ort.app.infrastructure.twitter

import com.ort.app.domain.model.tweet.TweetRepository
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Repository
import twitter4j.TwitterException
import twitter4j.TwitterFactory
import twitter4j.conf.ConfigurationBuilder

@Repository
class TweetRepositoryImpl : TweetRepository {

    private val logger = LoggerFactory.getLogger(this::class.java)
    private val VILLAGE_URL = "https://wolfort.net/wolf-mansion/village/"

    @Value("\${app.debug}")
    private lateinit var debug: String

    @Value("\${twitter.oauth.consumer-key:}")
    private lateinit var consumerKey: String

    @Value("\${twitter.oauth.consumer-secret:}")
    private lateinit var consumerSecret: String

    @Value("\${twitter.oauth.access-token:}")
    private lateinit var accessToken: String

    @Value("\${twitter.oauth.access-token-secret:}")
    private lateinit var accessTokenSecret: String

    override fun tweet(msg: String, villageId: Int) {
        tweet("[WOLF MANSION] $msg\r\n$VILLAGE_URL$villageId")
    }

    override fun tweet(msg: String) {
        if (debug.toBoolean()) return
        // API有料化に伴い機能停止
        val cb = ConfigurationBuilder()
        cb.setDebugEnabled(true)
            .setOAuthConsumerKey(consumerKey)
            .setOAuthConsumerSecret(consumerSecret)
            .setOAuthAccessToken(accessToken)
            .setOAuthAccessTokenSecret(accessTokenSecret)
        val tf = TwitterFactory(cb.build())
        val twitter = tf.instance
        try {
            twitter.updateStatus("[自動通知]$msg")
        } catch (e: TwitterException) {
            // 失敗してもスルー
            logger.warn("fail tweet", e)
        }
    }
}