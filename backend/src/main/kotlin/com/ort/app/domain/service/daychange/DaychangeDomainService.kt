package com.ort.app.domain.service.daychange

import com.ort.app.domain.model.chara.Charas
import com.ort.app.domain.model.commit.Commits
import com.ort.app.domain.model.daychange.Daychange
import org.springframework.stereotype.Service

@Service
class DaychangeDomainService(
    private val prologueDomainService: PrologueDomainService,
    private val progressDomainService: ProgressDomainService,
    private val epilogueDomainService: EpilogueDomainService,
) {
    fun leaveParticipantIfNeeded(daychange: Daychange): Daychange =
        if (!daychange.village.status.isPrologue()) {
            daychange
        } else {
            prologueDomainService.leaveParticipantIfNeeded(daychange)
        }

    fun cancelOrExtendPrologueIfNeeded(daychange: Daychange): Daychange =
        if (!daychange.village.status.isPrologue()) {
            daychange
        } else {
            prologueDomainService.cancelOrExtendPrologueIfNeeded(daychange)
        }

    fun changeDayIfNeeded(
        daychange: Daychange,
        commits: Commits,
        charasProvider: () -> Charas,
    ): Daychange {
        val status = daychange.village.status
        return when {
            status.isPrologue() -> prologueDomainService.changeDayIfNeeded(daychange, charasProvider)
            status.isProgress() -> progressDomainService.changeDayIfNeeded(daychange, commits, charasProvider)
            status.isEpilogue() -> epilogueDomainService.changeDayIfNeeded(daychange)
            else -> daychange
        }
    }
}
