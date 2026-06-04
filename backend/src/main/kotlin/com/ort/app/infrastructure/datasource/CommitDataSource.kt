package com.ort.app.infrastructure.datasource

import com.ort.app.domain.model.commit.Commit
import com.ort.app.domain.model.commit.CommitRepository
import com.ort.app.domain.model.commit.Commits
import com.ort.app.domain.model.village.Village
import com.ort.dbflute.exbhv.CommitBhv
import org.springframework.stereotype.Repository
import com.ort.dbflute.exentity.Commit as DbCommit

@Repository
class CommitDataSource(
    private val commitBhv: CommitBhv
) : CommitRepository {

    override fun findCommits(villageId: Int): Commits {
        val list = commitBhv.selectList { it.query().setVillageId_Equal(villageId) }
        return mapCommits(list)
    }

    override fun updateCommit(village: Village, commit: Commit) {
        val exists = findCommit(village.id, commit.day, commit.myselfId)
        if (exists == null) {
            insertCommit(village, commit)
        } else {
            deleteCommit(village, commit)
        }
    }

    private fun insertCommit(village: Village, commit: Commit) {
        val c = DbCommit()
        c.villageId = village.id
        c.day = commit.day
        c.villagePlayerId = commit.myselfId
        commitBhv.insert(c)
    }

    private fun deleteCommit(village: Village, commit: Commit) {
        commitBhv.queryDelete {
            it.query().setVillageId_Equal(village.id)
            it.query().setDay_Equal(commit.day)
            it.query().setVillagePlayerId_Equal(commit.myselfId)
        }
    }

    private fun findCommit(villageId: Int, day: Int, myselfId: Int): Commit? {
        return commitBhv.selectEntity {
            it.query().setVillageId_Equal(villageId)
            it.query().setDay_Equal(day)
            it.query().setVillagePlayerId_Equal(myselfId)
        }.map { mapCommit(it) }.orElse(null)
    }

    private fun mapCommits(list: List<DbCommit>): Commits = Commits(list = list.map { mapCommit(it) })

    private fun mapCommit(commit: DbCommit): Commit = Commit(
        day = commit.day,
        myselfId = commit.villagePlayerId
    )
}