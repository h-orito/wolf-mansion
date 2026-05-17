package com.ort.app.domain.model.commit

import com.ort.app.domain.model.village.Village

interface CommitRepository {

    fun findCommits(villageId: Int): Commits

    fun updateCommit(village: Village, commit: Commit)
}