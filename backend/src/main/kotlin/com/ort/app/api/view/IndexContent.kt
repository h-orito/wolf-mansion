package com.ort.app.api.view

import com.ort.app.domain.model.village.Village
import com.ort.app.domain.model.village.Villages
import com.ort.app.domain.model.village.setting.VillageTags
import com.ort.dbflute.allcommon.CDef
import java.time.LocalDateTime
import java.time.temporal.ChronoUnit

data class IndexContent(
    /** 村リスト  */
    val villageList: List<IndexVillage>,
    /** どこかの村に参加中か  */
    val isParticipate: Boolean
) {

    constructor(
        villages: Villages,
        canCreateVillage: Boolean
    ) : this(
        villageList = villages.list.map { IndexVillage(it) },
        isParticipate = !canCreateVillage
    )

    data class IndexVillage(
        /** 村ID  */
        val villageId: Int,
        /** 村表示用番号  */
        val villageNumber: String,
        /** 村名  */
        val villageName: String,
        /** 人数  */
        val participateNum: String,
        /** 状態  */
        val status: String,
        /** タグ */
        val tags: List<Tag>,
        // 以下、エイプリルフールネタ
        /** 築年数 */
        val chiku: String,
        /** 徒歩 */
        val toho: String,
        /** エリア */
        val area: String,
        /** 間取り */
        val madori: String,
        /** 家賃 */
        val yachin: String

    ) {
        constructor(
            village: Village
        ) : this(
            villageId = village.id,
            villageNumber = village.id.toString().padStart(4, '0'),
            villageName = village.name,
            participateNum = convertToParticipateNumStr(village),
            status = village.status.name,
            tags = mapTags(village.setting.tags),
            chiku = "築${ChronoUnit.DAYS.between(village.createDatetime, LocalDateTime.now())}日",
            toho = "駅徒歩${(1..59).random()}分",
            area = listOf(
                "北海道札幌市",
                "青森県青森市",
                "岩手県盛岡市",
                "宮城県仙台市",
                "秋田県秋田市",
                "山形県山形市",
                "福島県福島市",
                "茨城県水戸市",
                "栃木県宇都宮市",
                "群馬県前橋市",
                "埼玉県さいたま市",
                "千葉県千葉市",
                "東京都特別行政区",
                "神奈川県横浜市",
                "新潟県新潟市",
                "富山県富山市",
                "石川県金沢市",
                "福井県福井市",
                "山梨県甲府市",
                "長野県長野市",
                "岐阜県岐阜市",
                "静岡県静岡市",
                "愛知県名古屋市",
                "三重県津市",
                "滋賀県大津市",
                "京都府京都市",
                "大阪府大阪市",
                "兵庫県神戸市",
                "奈良県奈良市",
                "和歌山県和歌山市",
                "鳥取県鳥取市",
                "島根県松江市",
                "岡山県岡山市",
                "広島県広島市",
                "山口県山口市",
                "徳島県徳島市",
                "香川県高松市",
                "愛媛県松山市",
                "高知県高知市",
                "福岡県福岡市",
                "佐賀県佐賀市",
                "長崎県長崎市",
                "熊本県熊本市",
                "大分県大分市",
                "宮崎県宮崎市",
                "鹿児島県鹿児島市",
                "沖縄県那覇市",
            ).random(),
            madori = if (village.status.isPrologue()) "${village.setting.personMin}~${village.setting.personMax}LDK"
            else if (village.spectators.count > 0) "${village.participants.count}+${village.spectators.count}LDK"
            else "${village.participants.count}LDK",
            yachin = "${(2..100).random()}万円"
        )

        data class Tag(
            val level: String,
            val name: String
        )

        companion object {
            private fun convertToParticipateNumStr(village: Village): String {
                val participateNum = village.participants.count
                val spectatorNum = village.spectators.count
                val spectatorStr = if (spectatorNum <= 0) "" else " ($spectatorNum)"
                return if (village.status.isPrologue()) {
                    val maxNum = village.setting.personMax
                    "$participateNum/$maxNum${spectatorStr}人"
                } else {
                    "$participateNum${spectatorStr}人"
                }
            }

            private fun mapTags(tags: VillageTags): List<Tag> {
                val age =
                    tags.list.find { it.toCdef() == CDef.VillageTagItem.R15 || it.toCdef() == CDef.VillageTagItem.R18 }
                        ?.let {
                            listOf(Tag(level = "danger", name = it.name))
                        } ?: emptyList()
                val welcome =
                    tags.list.find { it.toCdef() == CDef.VillageTagItem.誰歓 || it.toCdef() == CDef.VillageTagItem.身内 }
                        ?.let {
                            listOf(Tag(level = "success", name = it.name))
                        } ?: emptyList()
                return age + welcome
            }
        }
    }
}