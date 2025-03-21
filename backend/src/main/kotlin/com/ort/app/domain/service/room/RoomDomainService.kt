package com.ort.app.domain.service.room

import com.ort.app.domain.model.daychange.Daychange
import com.ort.app.domain.model.situation.village.VillageRoomAssignedColumn
import com.ort.app.domain.model.situation.village.VillageRoomAssignedSituation
import com.ort.app.domain.model.village.Village
import com.ort.app.domain.model.village.participant.VillageParticipant
import com.ort.app.domain.model.village.participant.VillageParticipants
import com.ort.app.domain.model.village.room.Room
import com.ort.app.domain.model.village.room.RoomSize
import com.ort.app.domain.service.FootstepDomainService
import com.ort.app.domain.service.MessageDomainService
import org.springframework.stereotype.Service

@Service
class RoomDomainService(
    private val messageDomainService: MessageDomainService,
    private val footstepDomainService: FootstepDomainService
) {

    fun convertToSituation(village: Village, day: Int): VillageRoomAssignedSituation {
        village.roomSize ?: return VillageRoomAssignedSituation(emptyList())
        return VillageRoomAssignedSituation(
            columns = List(village.roomSize.height) { columnIndex ->
                VillageRoomAssignedColumn(
                    rows = List(village.roomSize.width) { rowIndex ->
                        val roomNumber = columnIndex * village.roomSize.width + rowIndex + 1
                        village.participants.findByRoomNumber(roomNumber, day)
                    }
                )
            }
        )
    }

    fun assign(daychange: Daychange): Daychange {
        val roomSize = RoomSize.invoke(daychange.village.participants.list.size)
        var village = daychange.village.copy(roomSize = roomSize)
        // 部屋割り当て
        village = assignRoom(village)
        // 割り当てメッセージ
        val messages = daychange.messages.add(messageDomainService.createRoomAssignedMessage(village))
        return daychange.copy(
            village = village,
            messages = messages
        )
    }

    fun reAssign(daychange: Daychange, participant: VillageParticipant): Daychange {
        var village = daychange.village.copy()
        // 部屋割り当て
        village = assignRoom(village)
        // 割り当てメッセージ
        val messages = daychange.messages.add(messageDomainService.createRoomReAssignedMessage(village, participant))
        return daychange.copy(
            village = village,
            messages = messages
        )
    }

    // 対象＋周辺8部屋の部屋番号（存在しない部屋番号を含んでいても良い）
    fun detectAroundRoomNumbers(room: Room, size: RoomSize): List<Int> {
        val targetRoomNumber = room.number
        val width = size.width

        return when {
            isLeftSide(targetRoomNumber, width) -> {
                listOf(
                    targetRoomNumber, // 対象の部屋
                    targetRoomNumber - width, // 上
                    targetRoomNumber - width + 1, // 右上
                    targetRoomNumber + 1, // 右
                    targetRoomNumber + width, // 下
                    targetRoomNumber + width + 1 // 右下
                )
            }
            isRightSide(targetRoomNumber, width) -> {
                listOf(
                    targetRoomNumber, // 対象の部屋
                    targetRoomNumber - width - 1, // 左上
                    targetRoomNumber - width, // 上
                    targetRoomNumber - 1, // 左
                    targetRoomNumber + width - 1, // 左下
                    targetRoomNumber + width // 下
                )
            }
            else -> {
                listOf(
                    targetRoomNumber, // 対象の部屋
                    targetRoomNumber - width - 1, // 左上
                    targetRoomNumber - width, // 上
                    targetRoomNumber - width + 1, // 右上
                    targetRoomNumber - 1, // 左
                    targetRoomNumber + 1, // 右
                    targetRoomNumber + width - 1, // 左下
                    targetRoomNumber + width, // 下
                    targetRoomNumber + width + 1 // 右下
                )
            }
        }
    }

    // 周辺8部屋の部屋番号（存在しない部屋番号を含んでいても良い）
    fun detectKingRoomNumbers(room: Room, size: RoomSize): List<Int> {
        val targetRoomNumber = room.number
        val width = size.width

        return when {
            isLeftSide(targetRoomNumber, width) -> {
                listOf(
                    targetRoomNumber - width, // 上
                    targetRoomNumber - width + 1, // 右上
                    targetRoomNumber + 1, // 右
                    targetRoomNumber + width, // 下
                    targetRoomNumber + width + 1 // 右下
                )
            }
            isRightSide(targetRoomNumber, width) -> {
                listOf(
                    targetRoomNumber - width - 1, // 左上
                    targetRoomNumber - width, // 上
                    targetRoomNumber - 1, // 左
                    targetRoomNumber + width - 1, // 左下
                    targetRoomNumber + width // 下
                )
            }
            else -> {
                listOf(
                    targetRoomNumber - width - 1, // 左上
                    targetRoomNumber - width, // 上
                    targetRoomNumber - width + 1, // 右上
                    targetRoomNumber - 1, // 左
                    targetRoomNumber + 1, // 右
                    targetRoomNumber + width - 1, // 左下
                    targetRoomNumber + width, // 下
                    targetRoomNumber + width + 1 // 右下
                )
            }
        }
    }

    // 対象の周辺8部屋のうち、銀が移動できる部屋番号（存在しない部屋番号を含んでいても良い）
    fun detectGinRoomNumbers(room: Room, size: RoomSize): List<Int> {
        val targetRoomNumber = room.number
        val width = size.width
        return when {
            isLeftSide(targetRoomNumber, width) -> {
                listOf(
                    targetRoomNumber - width, // 上
                    targetRoomNumber - width + 1, // 右上
                    targetRoomNumber + width + 1 // 右下
                )
            }
            isRightSide(targetRoomNumber, width) -> {
                listOf(
                    targetRoomNumber - width - 1, // 左上
                    targetRoomNumber - width, // 上
                    targetRoomNumber + width - 1 // 左下
                )
            }
            else -> {
                listOf(
                    targetRoomNumber - width - 1, // 左上
                    targetRoomNumber - width, // 上
                    targetRoomNumber - width + 1, // 右上
                    targetRoomNumber + width - 1, // 左下
                    targetRoomNumber + width + 1 // 右下
                )
            }
        }
    }

    // 周辺8部屋のうち金が移動できる部屋番号（存在しない部屋番号を含んでいても良い）
    fun detectKinRoomNumbers(room: Room, size: RoomSize): List<Int> {
        val targetRoomNumber = room.number
        val width = size.width

        return when {
            isLeftSide(targetRoomNumber, width) -> {
                listOf(
                    targetRoomNumber - width, // 上
                    targetRoomNumber - width + 1, // 右上
                    targetRoomNumber + 1, // 右
                    targetRoomNumber + width, // 下
                )
            }
            isRightSide(targetRoomNumber, width) -> {
                listOf(
                    targetRoomNumber - width - 1, // 左上
                    targetRoomNumber - width, // 上
                    targetRoomNumber - 1, // 左
                    targetRoomNumber + width // 下
                )
            }
            else -> {
                listOf(
                    targetRoomNumber - width - 1, // 左上
                    targetRoomNumber - width, // 上
                    targetRoomNumber - width + 1, // 右上
                    targetRoomNumber - 1, // 左
                    targetRoomNumber + 1, // 右
                    targetRoomNumber + width // 下
                )
            }
        }
    }

    // 対象を除く周辺4部屋の部屋番号（存在しない部屋番号を含んでいても良い）
    fun detectWasdRoomNumbers(room: Room, size: RoomSize): List<Int> {
        val targetRoomNumber = room.number
        val width = size.width

        return when {
            isLeftSide(targetRoomNumber, width) -> {
                listOf(
                    targetRoomNumber - width,  // 上
                    targetRoomNumber + 1,  // 右
                    targetRoomNumber + width // 下
                )
            }
            isRightSide(targetRoomNumber, width) -> {
                listOf(
                    targetRoomNumber - width,  // 上
                    targetRoomNumber - 1,  // 左
                    targetRoomNumber + width // 下
                )
            }
            else -> {
                listOf(
                    targetRoomNumber - width,  // 上
                    targetRoomNumber - 1,  // 左
                    targetRoomNumber + 1,  // 右
                    targetRoomNumber + width // 下
                )
            }
        }
    }

    // 対象を除く直線4方向の部屋番号（存在しない部屋番号を含んでいても良い）
    fun detectHishaRoomNumbers(room: Room, size: RoomSize): List<Int> {
        val roomNumberList: MutableList<Int> = mutableListOf()
        val targetRoomNumber = room.number
        val width = size.width


        // 上
        var tempRoomNumber: Int = targetRoomNumber - width
        while (tempRoomNumber > 0) {
            roomNumberList.add(tempRoomNumber)
            tempRoomNumber -= width
        }

        // 右
        tempRoomNumber = targetRoomNumber
        while (!isRightSide(tempRoomNumber, width)) {
            tempRoomNumber += 1
            roomNumberList.add(tempRoomNumber)
        }

        // 下
        tempRoomNumber = targetRoomNumber + width
        while (tempRoomNumber <= width * size.height) {
            roomNumberList.add(tempRoomNumber)
            tempRoomNumber += width
        }

        // 左
        tempRoomNumber = targetRoomNumber
        while (!isLeftSide(tempRoomNumber, width)) {
            tempRoomNumber -= 1
            roomNumberList.add(tempRoomNumber)
        }

        return roomNumberList
    }

    fun detectKakuRoomNumbers(room: Room, size: RoomSize): List<Int> {
        val roomNumberList: MutableList<Int> = mutableListOf()
        val targetRoomNumber = room.number
        val width = size.width
        val height = size.height

        // 右上
        var tempRoomNumber = targetRoomNumber - width + 1
        while (tempRoomNumber > 0 && !isRightSide(tempRoomNumber + width - 1, width)) {
            roomNumberList.add(tempRoomNumber)
            tempRoomNumber = tempRoomNumber - width + 1
        }

        // 右下
        tempRoomNumber = targetRoomNumber + width + 1
        while (!isRightSide(tempRoomNumber - width - 1, width) && targetRoomNumber <= width * height) {
            roomNumberList.add(tempRoomNumber)
            tempRoomNumber = tempRoomNumber + width + 1
        }

        // 左下
        tempRoomNumber = targetRoomNumber + width - 1
        while (!isLeftSide(tempRoomNumber - width + 1, width) && tempRoomNumber <= width * height) {
            roomNumberList.add(tempRoomNumber)
            tempRoomNumber = tempRoomNumber + width - 1
        }

        // 左上
        tempRoomNumber = targetRoomNumber - width - 1
        while (!isLeftSide(tempRoomNumber + width + 1, width) && tempRoomNumber > 0) {
            roomNumberList.add(tempRoomNumber)
            tempRoomNumber = tempRoomNumber - width - 1
        }

        return roomNumberList
    }

    fun detectSilentRoomNumbers(myself: VillageParticipant, village: Village): List<Int> {
        // 生存者の部屋（現在の部屋、防音者は考慮しない）
        val aliveRoomNumList = village.participants.filterAlive().list.map { it.room!!.number }

        return village.participants.filterNotParticipant(myself).filterAlive().list.filter { participant ->
            val candidates = footstepDomainService.getCandidateList(village, myself.charaId, participant.charaId)
            // 足音なしで襲撃できるか、
            if (candidates.size == 1 && candidates.first() == "なし") true
            // 生存者がいないため無音になる
            else {
                candidates.all { roomNumbersStr ->
                    val roomNumbers = roomNumbersStr.split(",").map { it.toInt() }
                    roomNumbers.none { aliveRoomNumList.contains(it) }
                }
            }
        }.map { it.room!!.number }
    }

    private fun isLeftSide(targetRoomNumber: Int, width: Int): Boolean = targetRoomNumber % width == 1
    private fun isRightSide(targetRoomNumber: Int, width: Int): Boolean = targetRoomNumber % width == 0

    private fun assignRoom(village: Village): Village {
        var participants = village.participants.copy()
        val roomNumbers = RoomSize.getRoomNumbers(participants.list.size)
        participants.list.shuffled().forEachIndexed { index, participant ->
            participants = participants.assignRoom(
                id = participant.id,
                roomNumber = roomNumbers[index],
                day = village.latestDay()
            )
        }
        return village.copy(participants = participants)
    }
}