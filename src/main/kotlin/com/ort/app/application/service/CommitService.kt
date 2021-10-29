package com.ort.app.application.service

import com.ort.app.domain.model.commit.Commit
import com.ort.app.domain.model.commit.CommitRepository
import com.ort.app.domain.model.commit.Commits
import com.ort.app.domain.model.village.Village
import org.springframework.stereotype.Service

@Service
class CommitService(
    private val commitRepository: CommitRepository
) {

    fun findCommits(villageId: Int): Commits = commitRepository.findCommits(villageId)

    fun setCommit(village: Village, commit: Commit) = commitRepository.updateCommit(village, commit)
}