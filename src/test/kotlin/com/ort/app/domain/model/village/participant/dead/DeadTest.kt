package com.ort.app.domain.model.village.participant.dead

import com.ort.dbflute.allcommon.CDef
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

internal class DeadTest {

    @Test
    fun test_isDeadWhen() {
        val dead = Dead(
            isDead = true,
            deadDay = 6,
            reason = CDef.DeadReason.後追.toModel(),
            histories = DeadHistories(
                listOf(
                    DeadHistory(
                        day = 2,
                        isDead = true,
                        reason = CDef.DeadReason.処刑.toModel()
                    ),
                    DeadHistory(
                        day = 2,
                        isDead = false,
                        reason = null
                    ),
                    DeadHistory(
                        day = 2,
                        isDead = true,
                        reason = CDef.DeadReason.後追.toModel()
                    ),
                    DeadHistory(
                        day = 4,
                        isDead = false,
                        reason = null
                    ),
                    DeadHistory(
                        day = 5,
                        isDead = true,
                        reason = CDef.DeadReason.処刑.toModel()
                    )
                )
            )
        )

        assertFalse(dead.isDeadWhen(1))
        assertTrue(dead.isDeadWhen(2))
        assertTrue(dead.isDeadWhen(3))
        assertFalse(dead.isDeadWhen(4))
        assertTrue(dead.isDeadWhen(5))
    }
}