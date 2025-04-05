package com.ort.app.domain.service.room

import com.ort.app.domain.model.ability.Abilities
import com.ort.app.domain.model.daychange.Daychange
import com.ort.app.domain.model.footstep.Footsteps
import com.ort.app.domain.model.message.toModel
import com.ort.app.domain.model.player.Players
import com.ort.app.domain.model.village.createDay1Village
import com.ort.app.domain.model.village.createPrologueVillage
import com.ort.app.domain.model.village.room.Room
import com.ort.app.domain.model.village.room.RoomHistories
import com.ort.app.domain.model.village.room.RoomSize
import com.ort.app.domain.model.vote.Votes
import com.ort.dbflute.allcommon.CDef
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import kotlin.test.assertNotNull

@SpringBootTest
internal class RoomDomainServiceTest {

    @Autowired
    lateinit var service: RoomDomainService


    @Test
    fun test_convertToSituation() {
        // when
        val village = createDay1Village()

        // act
        val situation = service.convertToSituation(village, 1)

        // then
        val participantsCount = situation.columns.flatMap { it.rows }.count { it != null }
        assertEquals(participantsCount, village.participants.list.size)
    }

    @Test
    fun test_assign() {
        // when
        val org = Daychange(
            village = createPrologueVillage(),
            abilities = Abilities(emptyList()),
            votes = Votes(emptyList()),
            footsteps = Footsteps(emptyList()),
            players = Players(emptyList())
        )

        // act
        val daychange = service.assign(org)

        // then
        daychange.village.participants.list.forEach { assertNotNull(it.room) }
        assertEquals(
            daychange.messages.list.first().content.type.code,
            CDef.MessageType.公開システムメッセージ.toModel().code
        )
    }

    @Test
    fun test_reAssign() {
        // when
        val org = Daychange(
            village = createDay1Village(),
            abilities = Abilities(emptyList()),
            votes = Votes(emptyList()),
            footsteps = Footsteps(emptyList()),
            players = Players(emptyList())
        )

        // act
        val daychange = service.reAssign(org, org.village.participants.list.first())

        // then
        daychange.village.participants.list.forEach {
            assertNotNull(it.room)
            assertEquals(it.room!!.histories.list.size, 2)
        }

        assertEquals(
            daychange.messages.list.first().content.type.code,
            CDef.MessageType.公開システムメッセージ.toModel().code
        )
    }

    @Test
    fun test_detectAroundRoomNumbers() {
        // when
        var room = Room(number = 6, histories = RoomHistories(emptyList()))
        val size = RoomSize(width = 4, height = 4)

        // act
        var numbers = service.detectAroundRoomNumbers(room, size)

        // then
        var expected = listOf(1, 2, 3, 5, 7, 9, 10, 11)
        expected.forEach { assertTrue(numbers.contains(it)) }

        // when
        room = room.copy(number = 5)
        numbers = service.detectAroundRoomNumbers(room, size)

        // then
        expected = listOf(1, 2, 6, 9, 10)
        expected.forEach { assertTrue(numbers.contains(it)) }

        // when
        room = room.copy(number = 8)
        numbers = service.detectAroundRoomNumbers(room, size)

        // then
        expected = listOf(3, 4, 7, 11, 12)
        expected.forEach { assertTrue(numbers.contains(it)) }
    }

    @Test
    fun test_detectWasdRoomNumbers() {
        // when
        var room = Room(number = 6, histories = RoomHistories(emptyList()))
        val size = RoomSize(width = 4, height = 4)

        // act
        var numbers = service.detectWasdRoomNumbers(room, size)

        // then
        var expected = listOf(2, 5, 7, 10)
        expected.forEach { assertTrue(numbers.contains(it)) }

        // when
        room = room.copy(number = 5)
        numbers = service.detectWasdRoomNumbers(room, size)

        // then
        expected = listOf(1, 6, 9)
        expected.forEach { assertTrue(numbers.contains(it)) }

        // when
        room = room.copy(number = 8)
        numbers = service.detectWasdRoomNumbers(room, size)

        // then
        expected = listOf(4, 7, 12)
        expected.forEach { assertTrue(numbers.contains(it)) }
    }

    @Test
    fun test_detectHishaRoomNumbers() {
        // when
        val room = Room(number = 6, histories = RoomHistories(emptyList()))
        val size = RoomSize(width = 4, height = 4)

        // act
        val numbers = service.detectHishaRoomNumbers(room, size)

        // then
        val expected = listOf(2, 5, 7, 8, 10, 14)
        expected.forEach { assertTrue(numbers.contains(it)) }
    }

    @Test
    fun test_detectKakuRoomNumbers() {
        // when
        val room = Room(number = 6, histories = RoomHistories(emptyList()))
        val size = RoomSize(width = 4, height = 4)

        // act
        val numbers = service.detectKakuRoomNumbers(room, size)

        // then
        val expected = listOf(1, 3, 9, 11, 16)
        expected.forEach { assertTrue(numbers.contains(it)) }
    }
}