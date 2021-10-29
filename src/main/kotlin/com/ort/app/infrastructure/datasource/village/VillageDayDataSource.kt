package com.ort.app.infrastructure.datasource.village

import com.ort.app.domain.model.village.Village
import com.ort.app.domain.model.village.VillageDay
import com.ort.app.domain.model.village.VillageDays
import com.ort.dbflute.exbhv.VillageDayBhv
import org.springframework.stereotype.Repository
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import com.ort.dbflute.exentity.VillageDay as DbVillageDay

@Repository
class VillageDayDataSource(
    private val villageDayBhv: VillageDayBhv
) {
    private val formatter = DateTimeFormatter.ofPattern("uuuuMMddhhmm")

    fun extendDay(id: Int, day: Int, datetime: LocalDateTime) {
        val vd = DbVillageDay()
        vd.daychangeDatetime = datetime
        villageDayBhv.queryUpdate(vd) {
            it.query().setVillageId_Equal(id)
            it.query().setDay_Equal(day)
        }
    }

    fun insertVillageDays(id: Int, paramVillage: Village) {
        paramVillage.days.list.forEach { insertVillageDay(id, it) }
    }

    fun updateVillageStartDateTime(id: Int, days: VillageDays) = updateVillageDay(id, days.list.first())

    fun updateDaychangeDifference(villageId: Int, current: VillageDays, changed: VillageDays) {
        changed.list.filterNot { changedDay ->
            current.list.any { it.day == changedDay.day }
        }.forEach { insertVillageDay(villageId, it) }

        changed.list.filter { changedDay ->
            current.list.any {
                it.day == changedDay.day &&
                        it.dayChangeDatetime.format(formatter) != changedDay.dayChangeDatetime.format(formatter)
            }
        }.forEach { updateVillageDay(villageId, it) }
    }

    private fun updateVillageDay(villageId: Int, villageDay: VillageDay) {
        val d = DbVillageDay()
        d.villageId = villageId
        d.day = villageDay.day
        d.daychangeDatetime = villageDay.dayChangeDatetime
        villageDayBhv.update(d)
    }

    private fun insertVillageDay(villageId: Int, villageDay: VillageDay) {
        val vd = DbVillageDay()
        vd.villageId = villageId
        vd.day = villageDay.day
        vd.daychangeDatetime = villageDay.dayChangeDatetime
        villageDayBhv.insert(vd)
    }
}