package com.ort.app.api.charachip.response

import com.ort.app.domain.model.chara.Chara
import com.ort.app.domain.model.chara.Charachip
import com.ort.app.domain.model.village.room.RoomSize

data class CharachipDetailResponse(
    val id: Int,
    val name: String,
    val designerName: String,
    val descriptionUrl: String?,
    val isAvailableChangeName: Boolean,
    val charas: List<CharaView>,
    val roomAssignment: RoomAssignmentView,
) {
    constructor(charachip: Charachip) : this(
        id = charachip.id,
        name = charachip.name,
        designerName = charachip.designer?.name ?: "",
        descriptionUrl = charachip.descriptionUrl,
        isAvailableChangeName = charachip.isAvailableChangeName,
        charas = charachip.charas.list.map { CharaView(it) },
        roomAssignment = mapRoomAssignment(charachip),
    )

    companion object {
        private fun mapRoomAssignment(charachip: Charachip): RoomAssignmentView {
            val personNum = charachip.charas.list.size
            val roomSize = RoomSize.invoke(personNum)
            val roomNumbers = RoomSize.getRoomNumbers(personNum)
            val roomNumberToChara =
                roomNumbers
                    .mapIndexed { index, number -> number to charachip.charas.list[index] }
                    .toMap()
            val maxWidth = charachip.charas.list.maxOfOrNull { it.size.width } ?: 0
            val maxHeight = charachip.charas.list.maxOfOrNull { it.size.height } ?: 0
            val rows =
                List(roomSize.height) { rowIndex ->
                    RoomAssignmentRowView(
                        cells =
                            List(roomSize.width) { colIndex ->
                                val roomNumber = roomSize.width * rowIndex + colIndex + 1
                                val chara = roomNumberToChara[roomNumber]
                                RoomAssignmentCellView(
                                    roomNumber = roomNumber.toString().padStart(2, '0'),
                                    charaName = chara?.name,
                                    charaShortName = chara?.shortName,
                                    charaImgUrl = chara?.defaultImage()?.url,
                                    charaImgWidth = chara?.size?.width,
                                    charaImgHeight = chara?.size?.height,
                                )
                            },
                    )
                }
            return RoomAssignmentView(
                width = roomSize.width,
                height = roomSize.height,
                maxCharaWidth = maxWidth,
                maxCharaHeight = maxHeight,
                rows = rows,
            )
        }
    }
}

data class CharaView(
    val id: Int,
    val name: String,
    val shortName: String,
    val imageUrls: List<String>,
    val width: Int,
    val height: Int,
) {
    constructor(chara: Chara) : this(
        id = chara.id,
        name = chara.name,
        shortName = chara.shortName,
        imageUrls = chara.images.list.map { it.url },
        width = chara.size.width,
        height = chara.size.height,
    )
}

data class RoomAssignmentView(
    val width: Int,
    val height: Int,
    val maxCharaWidth: Int,
    val maxCharaHeight: Int,
    val rows: List<RoomAssignmentRowView>,
)

data class RoomAssignmentRowView(
    val cells: List<RoomAssignmentCellView>,
)

data class RoomAssignmentCellView(
    val roomNumber: String,
    val charaName: String?,
    val charaShortName: String?,
    val charaImgUrl: String?,
    val charaImgWidth: Int?,
    val charaImgHeight: Int?,
)
