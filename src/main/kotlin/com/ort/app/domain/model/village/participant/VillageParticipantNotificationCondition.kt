package com.ort.app.domain.model.village.participant

data class VillageParticipantNotificationCondition(
    val discordWebhookUrl: String,
    val village: VillageCondition,
    val message: MessageCondition
) {
    data class VillageCondition(
        /** 村が開始した */
        val start: Boolean,
        /** 村がエピローグを迎えた */
        val epilogue: Boolean
    )

    data class MessageCondition(
        /** 新着秘話 */
        val secretSay: Boolean,
        /** 新着役職窓発言 */
        val abilitySay: Boolean,
        /** アンカー */
        val anchor: Boolean,
        /** キーワード */
        val keywords: List<String>
    )
}