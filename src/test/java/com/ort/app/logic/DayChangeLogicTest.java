package com.ort.app.logic;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.ort.TestAssist;
import com.ort.WerewolfMansionTest;
import com.ort.dbflute.allcommon.CDef;
import com.ort.dbflute.exbhv.VillageBhv;
import com.ort.dbflute.exbhv.VillageDayBhv;
import com.ort.dbflute.exbhv.VillagePlayerBhv;
import com.ort.dbflute.exentity.Village;
import com.ort.dbflute.exentity.VillageDay;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DayChangeLogicTest extends WerewolfMansionTest {

    @Autowired
    private TestAssist assist;
    @Autowired
    private DayChangeLogic dayChangeLogic;
    @Autowired
    private VillageBhv villageBhv;
    @Autowired
    private VillageDayBhv villageDayBhv;
    @Autowired
    private VillagePlayerBhv villagePlayerBhv;

    @Test
    public void test_更新時間を迎えていない_人数が揃っていない() {
        // 村作成してダミーを参加させる
        Integer villageId = assist.createVillage();
        assist.participate(villageId, 1, 1, CDef.Skill.おまかせ, CDef.Skill.おまかせ);

        // ## Act ##
        dayChangeLogic.dayChangeIfNeeded(villageId);

        // ## Assert ##
        Village village = villageBhv.selectEntityWithDeletedCheck(cb -> cb.query().setVillageId_Equal(villageId));
        assertEquals(village.getVillageStatusCodeAsVillageStatus(), CDef.VillageStatus.募集中);

        VillageDay villageDay = villageDayBhv.selectEntity(cb -> {
            cb.query().setVillageId_Equal(villageId);
            cb.query().addOrderBy_Day_Desc();
            cb.fetchFirst(1);
        }).get();
        assertTrue(villageDay.getDay().equals(0));
    }

    @Test
    public void test_更新時間を迎えている_最低開始人数を満たしていない_参加者なし() {
        // 村作成してダミーを参加させる
        Integer villageId = assist.createVillage();
        assist.participate(villageId, 1, 1);
        // 更新時間を今より前に
        assist.toLatestDayChangeDatetimePast(villageId);

        // ## Act ##
        dayChangeLogic.dayChangeIfNeeded(villageId);

        // ## Assert ##
        Village village = villageBhv.selectByPK(villageId).get();
        assertEquals(village.getVillageStatusCodeAsVillageStatus(), CDef.VillageStatus.廃村);
    }

    @Test
    public void test_更新時間を迎えている_最低開始人数を満たしていない_参加者がいる() {
        // 村作成してダミーを参加させる
        Integer villageId = assist.createVillage();
        assist.participate(villageId, 1, 1);
        // 1名参加
        assist.participate(villageId, 2, 2);
        // 更新時間を今より前に
        assist.toLatestDayChangeDatetimePast(villageId);

        // ## Act ##
        dayChangeLogic.dayChangeIfNeeded(villageId);

        // ## Assert ##
        Village village = villageBhv.selectByPK(villageId).get();
        assertNotEquals(village.getVillageStatusCodeAsVillageStatus(), CDef.VillageStatus.廃村);
    }
}
