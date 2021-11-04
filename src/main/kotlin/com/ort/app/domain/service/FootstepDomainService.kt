package com.ort.app.domain.service

import com.ort.app.domain.model.footstep.Footsteps
import com.ort.app.domain.model.situation.village.VillageDayFootstep
import com.ort.app.domain.model.situation.village.VillageFootstepSituation
import com.ort.app.domain.model.skill.Skill
import com.ort.app.domain.model.village.Village
import com.ort.app.domain.model.village.participant.VillageParticipant
import com.ort.app.fw.exception.WolfMansionBusinessException
import org.springframework.stereotype.Service

@Service
class FootstepDomainService(
    private val spoilerDomainService: SpoilerDomainService
) {

    fun convertToSituation(
        village: Village,
        myself: VillageParticipant?,
        footsteps: Footsteps,
        day: Int
    ): VillageFootstepSituation {
        return VillageFootstepSituation(
            list = (2..village.latestDay()).map {
                val footstep = if (spoilerDomainService.isViewableSpoilerContent(village, myself)) {
                    getDisplayFootstepStringOpenSkill(village, footsteps, it)
                } else getDisplayFootstepStringWithoutHeader(village, footsteps, it)
                VillageDayFootstep(it, footstep)
            }
        )
    }

    // day: 足音を表示する日（=セットした日の翌日）
    fun getDisplayFootstepList(village: Village, footsteps: Footsteps, day: Int): List<String> {
        // 生存者の部屋
        val aliveRoomNumList = village.participants.list
            .filter { it.isAliveWhen(day) }
            .map { it.roomNumberWhen(day - 1) }
        // 足音
        return footsteps
            .filterByDay(day - 1).list
            .filterNot { it.roomNumbers == "なし" }
            .map { footstep ->
                // 生存者のいない部屋の音を除去
                val roomNumbers = footstep.roomNumbers.split(",").map { it.toInt() }
                roomNumbers.filter { aliveRoomNumList.contains(it) }
                    .joinToString(",") { it.toString().padStart(2, '0') }
            }.filterNot { it.isEmpty() }
            .sorted()
    }

    fun assertFootstep(village: Village, charaId: Int, targetCharaId: Int?, footstep: String?) {
        footstep ?: throw WolfMansionBusinessException("足音を選択してください")
        val candidateList = getCandidateList(village, charaId, targetCharaId)
        if (!candidateList.contains(footstep)) {
            throw WolfMansionBusinessException("選択できない足音を選択しています")
        }
    }

    fun getCandidateList(village: Village, charaId: Int, targetCharaId: Int?): List<String> {
        targetCharaId ?: return listOf("なし")
        val clockwise = FootstepCreator(village, charaId, targetCharaId).createClockwiseFootsteps()
        val counterClockwise = FootstepCreator(village, charaId, targetCharaId).createCounterClockwiseFootsteps()
        return listOf(clockwise, counterClockwise).distinct()
    }

    data class FootstepCreator(
        var startRoomNum: Int, // 計算しやすいように-1した部屋番号
        var destRoomNum: Int, // 計算しやすいように-1した部屋番号
        val roomWidth: Int,
        val footstepList: MutableList<Int> = mutableListOf()
    ) {
        constructor(village: Village, charaId: Int, targetCharaId: Int) : this(
            startRoomNum = village.participants.chara(charaId).room!!.number - 1,
            destRoomNum = village.participants.chara(targetCharaId).room!!.number - 1,
            roomWidth = village.roomSize!!.width
        )

        fun createClockwiseFootsteps(): String {
            addUpFootstep()
            addRightFootstep()
            addDownFootstep()
            addLeftFootstep()
            return if (footstepList.isEmpty()) "なし"
            else footstepList.sorted().joinToString(separator = ",")
        }

        fun createCounterClockwiseFootsteps(): String {
            addLeftFootstep()
            addDownFootstep()
            addRightFootstep()
            addUpFootstep()
            return if (footstepList.isEmpty()) "なし"
            else footstepList.sorted().joinToString(separator = ",")
        }

        // 上
        fun addUpFootstep() {
            while (true) {
                val fromy: Int = startRoomNum / roomWidth // 始点のy座標
                val toy: Int = destRoomNum / roomWidth // 終点のy座標
                if (fromy <= toy) break
                startRoomNum -= roomWidth
                // 0始まりにするために1減らしていたので1加算
                if (startRoomNum != destRoomNum) footstepList.add(startRoomNum + 1)
            }
        }

        // 右
        fun addRightFootstep() {
            while (true) {
                val fromx: Int = startRoomNum % roomWidth // 始点のx座標
                val tox: Int = destRoomNum % roomWidth // 終点のx座標
                if (tox <= fromx) break
                startRoomNum += 1
                // 0始まりにするために1減らしていたので1加算
                if (startRoomNum != destRoomNum) footstepList.add(startRoomNum + 1)
            }
        }

        // 下
        fun addDownFootstep() {
            while (true) {
                val fromy: Int = startRoomNum / roomWidth // 始点のy座標
                val toy: Int = destRoomNum / roomWidth // 終点のy座標
                if (toy <= fromy) break
                startRoomNum += roomWidth
                // 0始まりにするために1減らしていたので1加算
                if (startRoomNum != destRoomNum) footstepList.add(startRoomNum + 1)
            }
        }

        // 左
        fun addLeftFootstep() {
            while (true) {
                val fromx: Int = startRoomNum % roomWidth // 始点のx座標
                val tox: Int = destRoomNum % roomWidth // 終点のx座標
                if (fromx <= tox) break
                startRoomNum -= 1
                // 0始まりにするために1減らしていたので1加算
                if (startRoomNum != destRoomNum) footstepList.add(startRoomNum + 1)
            }
        }
    }

    fun assertDisturbFootstep(village: Village, footstep: String?) {
        if (footstep.isNullOrEmpty()) throw WolfMansionBusinessException("足音を選択してください")
        if (!isFootstepStraight(village, footstep)) {
            throw WolfMansionBusinessException("足音が直線になっていません")
        }
    }

    // day: セットした日
    // day+1: 足音が鳴った日
    fun getParticipantByFootstep(village: Village, day: Int, targetFootstep: String, footsteps: Footsteps): VillageParticipant {
        // 足音が鳴った時点で生存している人の部屋番号
        val roomNumberList =
            village.participants.list.filter { it.isAliveWhen(day + 1) }.map { it.roomNumberWhen(day)!! }
        // 調査対象の足音
        val target = footsteps.filterByDay(day).list
            .filterNot { it.roomNumbers == "なし" }
            .filter { footstep ->
                // 実際鳴った足音
                val actualFootstep = footstep.roomNumbers.split(",")
                    .map { it.toInt() }
                    .filter { roomNumberList.contains(it) }
                    .joinToString(separator = ",") { it.toString().padStart(2, '0') }

                targetFootstep == actualFootstep
            }.shuffled().first()

        return village.participants.chara(target.charaId)
    }

    // day: セットした日
    fun findPassedParticipants(
        village: Village,
        footsteps: Footsteps,
        day: Int,
        roomNumber: Int
    ): List<VillageParticipant> {
        return footsteps.filterByDay(day).list
            .filterNot { it.roomNumbers == "なし" }
            .filter { it.roomNumbers.split(",").any { number -> number.toInt() == roomNumber } }
            .map { footstep -> village.participants.chara(footstep.charaId) }
    }

    private fun isFootstepStraight(village: Village, footstep: String): Boolean {
        val footstepRoomNumbers = footstep.split(",")
        val maxRoomNum = village.roomSize!!.width * village.roomSize.height
        if (footstepRoomNumbers.size < 2) {
            return footstepRoomNumbers.first() == "なし"
                    || footstepRoomNumbers.first().toInt() in 1..maxRoomNum
        }
        val width: Int = village.roomSize.width
        var existRightMove = false
        var existDownMove = false
        for (i in 0 until footstepRoomNumbers.size - 1) {
            val nowRoomNum: Int = footstepRoomNumbers[i].toInt() - 1
            val nextRoomNum: Int = footstepRoomNumbers[i + 1].toInt() - 1
            if (nowRoomNum < 0 || maxRoomNum < nowRoomNum || nextRoomNum < 1 || maxRoomNum < nextRoomNum) {
                // 変な数字が紛れてたらNG
                return false
            }
            val nowx = nowRoomNum % width // 始点のx座標
            val nextx = nextRoomNum % width // 終点のx座標
            val nowy = nowRoomNum / width // 始点のy座標
            val nexty = nextRoomNum / width // 終点のy座標
            if (nextx - nowx == 1 && nowy == nexty) { // right
                existRightMove = true
            } else if (nowx == nextx && nexty - nowy == 1) { // down
                existDownMove = true
            } else {
                return false
            }
        }
        // 右もしくは下方向にのみ動いていればok
        return existRightMove xor existDownMove
    }

    // day: 足音を表示する日（=セットした日の翌日）
    private fun getDisplayFootstepStringOpenSkill(village: Village, footsteps: Footsteps, day: Int): String {
        // 生存者の部屋
        val aliveRoomNumList = village.participants.list
            .filter { it.isAliveWhen(day) }
            .map { it.roomNumberWhen(day - 1) }
        // 足音
        return footsteps
            .filterByDay(day - 1).list.joinToString(separator = "\n") { footstep ->
                // 出した人
                val participant = village.participants.chara(footstep.charaId)
                // 出そうとした足音
                val setFootstep = if (footstep.roomNumbers == "なし") "なし"
                else footstep.roomNumbers.split(",").joinToString(separator = ",") { it.padStart(2, '0') }
                // 出た足音
                var actualFootstep = if (footstep.roomNumbers == "なし") "なし"
                else footstep.roomNumbers.split(",").map { it.toInt() }.filter { aliveRoomNumList.contains(it) }
                    .joinToString(",") { it.toString().padStart(2, '0') }
                if (actualFootstep.isEmpty()) actualFootstep = "なし"

                "[${participant.shortNameWhen(day - 1)}][${participant.skill!!.name}] $setFootstep → $actualFootstep"
            }
    }


    // day: 足音を表示する日（=セットした日の翌日）
    private fun getDisplayFootstepStringWithoutHeader(village: Village, footsteps: Footsteps, day: Int): String {
        val list = getDisplayFootstepList(village, footsteps, day)
        if (list.isEmpty()) return "足音を聞いたものはいなかった...。"
        return list.joinToString(separator = "\n") { "部屋${it}で足音が聞こえた...。" }
    }

    // day: 足音を表示する日（=セットした日の翌日）
    fun getDisplayFootstepString(village: Village, footsteps: Footsteps, day: Int): String {
        return "館の大広間に集まった村人達は、昨晩聞こえた足音について確認した。\n\n" +
                getDisplayFootstepStringWithoutHeader(village, footsteps, day)
    }
}