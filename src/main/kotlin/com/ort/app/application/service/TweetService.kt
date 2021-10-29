package com.ort.app.application.service

import com.ort.app.domain.model.tweet.TweetRepository
import com.ort.app.domain.model.village.Village
import org.springframework.stereotype.Service
import java.time.format.DateTimeFormatter

@Service
class TweetService(
    private val tweetRepository: TweetRepository
) {

    private val format = DateTimeFormatter.ofPattern("uuuu/MM/dd HH:mm")

    fun tweetParticipantEnoughIfNeeded(village: Village) {
        if (village.participants.count != village.setting.personMin) return
        // 身内村は通知しない
        if (!village.setting.joinPassword.isNullOrBlank()) return

        val startDateTime = village.setting.startDatetime.format(format)
        tweetRepository.tweet(
            "人数が揃いました。次回更新時に村が開始されます。\r\n村名：${village.name}\r\n開始予定：${startDateTime}",
            village.id
        )
    }

    fun tweetCreateVillageIfNeeded(village: Village) {
        if (village.setting.joinPassword.isNullOrEmpty()) return
        val startDatetime = village.setting.startDatetime.format(format)
        tweetRepository.tweet(
            msg = """
                新しい村が作成されました。
                村名：${village.name}
                開始予定：$startDatetime
            """.trimIndent(),
            villageId = village.id
        )
    }

    fun tweetDaychange(villageId: Int, list: List<String>) = list.forEach { tweetRepository.tweet(it, villageId) }
}