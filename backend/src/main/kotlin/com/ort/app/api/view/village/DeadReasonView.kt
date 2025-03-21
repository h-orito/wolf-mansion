package com.ort.app.api.view.village

import com.ort.app.domain.model.village.participant.dead.DeadReason

data class DeadReasonView(
    val code: String,
    val name: String
) {
    companion object {
        fun of(org: DeadReason): DeadReasonView {
            return if (org.isMiserable()) ofMiserable()
            else DeadReasonView(
                code = org.code,
                name = org.name
            )
        }

        private fun ofMiserable() = DeadReasonView(
            code = "MISERABLE",
            name = "無惨"
        )
    }
}