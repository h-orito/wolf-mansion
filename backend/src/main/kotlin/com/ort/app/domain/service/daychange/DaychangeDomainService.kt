package com.ort.app.domain.service.daychange

import com.ort.app.domain.model.chara.Chara
import com.ort.app.domain.model.chara.Charas
import com.ort.app.domain.model.commit.Commits
import com.ort.app.domain.model.daychange.Daychange
import org.springframework.stereotype.Service

@Service
class DaychangeDomainService(
    private val prologueDomainService: PrologueDomainService,
    private val progressDomainService: ProgressDomainService,
    private val epilogueDomainService: EpilogueDomainService
) {

    fun leaveParticipantIfNeeded(daychange: Daychange): Daychange {
        return if (!daychange.village.status.isPrologue()) daychange
        else prologueDomainService.leaveParticipantIfNeeded(daychange)
    }

    fun cancelOrExtendPrologueIfNeeded(daychange: Daychange): Daychange {
        return if (!daychange.village.status.isPrologue()) daychange
        else prologueDomainService.cancelOrExtendPrologueIfNeeded(daychange)
    }

    fun changeDayIfNeeded(daychange: Daychange, commits: Commits, charas: Charas): Daychange {
        val status = daychange.village.status
        return when {
            status.isPrologue() -> prologueDomainService.changeDayIfNeeded(daychange, charas)
            status.isProgress() -> progressDomainService.changeDayIfNeeded(daychange, commits, charas)
            status.isEpilogue() -> epilogueDomainService.changeDayIfNeeded(daychange)
            else -> daychange
        }
    }
}