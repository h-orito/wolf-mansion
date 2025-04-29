package com.ort.app.api.view

import com.ort.app.api.view.village.VillageRoomAssignedRow
import com.ort.app.domain.model.chara.Chara
import com.ort.app.domain.model.chara.Charachip
import com.ort.app.domain.model.village.room.RoomSize

data class CharaGroupContent(
    /** キャラグループID  */
    val charaGroupId: Int,
    /** キャラグループ名  */
    val charaGroupName: String,
    /** 作者名  */
    val designerName: String,
    /** キャラチップURL  */
    val descriptionUrl: String,
    /** キャラリスト  */
    val charaList: List<CharaGroupChara>,
    /** 名称変更可能か  */
    val isAvailableChangeName: Boolean,
    /** 部屋割り表示用 */
    val roomAssignedRowList: List<RoomAssignedRow>,
) {
    constructor(
        charachip: Charachip
    ) : this(
        charaGroupId = charachip.id,
        charaGroupName = charachip.name,
        designerName = charachip.designer!!.name,
        descriptionUrl = charachip.descriptionUrl!!,
        charaList = charachip.charas.list.map { CharaGroupChara(it) },
        isAvailableChangeName = charachip.isAvailableChangeName,
        roomAssignedRowList = mapRoomAssign(charachip)
    )

    data class CharaGroupChara(
        /** キャラID  */
        val charaId: Int,
        /** キャラ名  */
        val charaName: String,
        /** キャラ名略称  */
        val charaShortName: String,
        /** キャラ画像URLリスト  */
        val charaImgUrlList: List<String>,
        /** キャラ画像横幅  */
        val charaImgWidth: Int,
        /** キャラ画像縦幅  */
        val charaImgHeight: Int
    ) {
        constructor(
            chara: Chara
        ) : this(
            charaId = chara.id,
            charaName = chara.name,
            charaShortName = chara.shortName,
            charaImgUrlList = chara.images.list.map { it.url },
            charaImgWidth = chara.size.width,
            charaImgHeight = chara.size.height
        )
    }

    companion object {
        private fun mapRoomAssign(charachip: Charachip): List<RoomAssignedRow> {
            val roomSize = RoomSize.invoke(charachip.charas.list.size)
            val roomNumbers = RoomSize.getRoomNumbers(charachip.charas.list.size)
            val roomNumberToChara = roomNumbers.mapIndexed { index, number ->
                number to charachip.charas.list[index]
            }.toMap()
            return List(roomSize.height) { columnIndex ->
                RoomAssignedRow.of(charachip, roomSize, roomNumberToChara, columnIndex)
            }
        }
    }

    data class RoomAssignedRow(
        var roomAssignedList: List<RoomAssigned>
    ) {

        companion object {
            fun of(
                charachip: Charachip,
                roomSize: RoomSize,
                roomNumberToChara: Map<Int, Chara>,
                columnIndex: Int
            ): RoomAssignedRow {
                val maxWidth = charachip.charas.list.maxByOrNull { it.size.width }!!.size.width
                val maxHeight = charachip.charas.list.maxByOrNull { it.size.height }!!.size.height
                return RoomAssignedRow(
                    roomAssignedList = List(roomSize.width) { rowIndex ->
                        val roomNumber = roomSize.width * columnIndex + rowIndex + 1
                        RoomAssigned(roomNumber, roomNumberToChara[roomNumber], maxWidth, maxHeight)
                    }
                )
            }
        }

        data class RoomAssigned(
            /** 部屋番号 */
            val roomNumber: String,
            /** キャラ名 */
            val charaName: String?,
            /** キャラ名略称 */
            val charaShortName: String?,
            /** キャラ画像 */
            val charaImgUrl: String?,
            /** キャラ画像横幅 */
            val charaImgWidth: Int?,
            /** キャラ画像縦幅 */
            val charaImgHeight: Int?,
            /** 最大横幅 */
            val maxWidth: Int?,
            /** 最大縦幅 */
            val maxHeight: Int?,
        ) {
            constructor(
                roomNumber: Int,
                chara: Chara?,
                maxWidth: Int?,
                maxHeight: Int?
            ): this(
                roomNumber = roomNumber.toString().padStart(2, '0'),
                charaName = chara?.name,
                charaShortName = chara?.shortName,
                charaImgUrl = chara?.defaultImage()?.url,
                charaImgWidth = chara?.size?.width,
                charaImgHeight = chara?.size?.height,
                maxWidth = maxWidth,
                maxHeight = maxHeight
            )
        }
    }
}