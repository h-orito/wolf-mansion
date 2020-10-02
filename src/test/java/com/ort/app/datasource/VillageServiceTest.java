package com.ort.app.datasource;

import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import com.ort.TestAssist;
import com.ort.WerewolfMansionTest;
import com.ort.dbflute.allcommon.CDef;
import com.ort.dbflute.exentity.Village;
import com.ort.dbflute.exentity.VillagePlayer;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class VillageServiceTest extends WerewolfMansionTest {

    @Autowired
    private TestAssist assist;
    @Autowired
    private VillageService service;

    // VillagePlayerのテストだが便宜上ここにおく
    @Test
    public void test_isDead() {
        // ## Arrange ##
        Integer villageId = assist.createPrologueFullMemberVillage();
        assist.toNextDay(villageId); // 1日目へ
        assist.toNextDay(villageId); // 2日目へ
        assist.toNextDay(villageId); // 3日目へ
        assist.toNextDay(villageId); // 4日目へ

        Village village = service.selectVillage(villageId, false, false);
        // 生存している人
        VillagePlayer villagePlayer = village.getVillagePlayers().filterAlive().list.get(0);
        Integer villagePlayerId = villagePlayer.getVillagePlayerId();
        assertTrue(villagePlayer.isAliveWhen(4));

        // 2日目に死亡
        service.dead(villagePlayer, 2, CDef.DeadReason.襲撃);
        village = service.selectVillage(villageId, false, false);
        villagePlayer = village.getVillagePlayers().filterBy(vp -> vp.getVillagePlayerId().equals(villagePlayerId)).list.get(0);
        assertTrue(villagePlayer.isDeadWhen(2));
        assertTrue(villagePlayer.isDeadWhen(3));
        assertTrue(villagePlayer.isDeadWhen(4));
        // 2日目に死亡後復活
        service.revive(villagePlayer, 2);
        village = service.selectVillage(villageId, false, false);
        villagePlayer = village.getVillagePlayers().filterBy(vp -> vp.getVillagePlayerId().equals(villagePlayerId)).list.get(0);
        assertTrue(villagePlayer.isAliveWhen(2));
        assertTrue(villagePlayer.isAliveWhen(3));
        assertTrue(villagePlayer.isAliveWhen(4));
        // 3日目に後追い死
        service.dead(villagePlayer, 3, CDef.DeadReason.後追);
        village = service.selectVillage(villageId, false, false);
        villagePlayer = village.getVillagePlayers().filterBy(vp -> vp.getVillagePlayerId().equals(villagePlayerId)).list.get(0);
        assertTrue(villagePlayer.isDeadWhen(3));
        assertTrue(villagePlayer.isDeadWhen(4));
        // 4日目に復活直後に後追い死
        service.revive(villagePlayer, 4);
        service.dead(villagePlayer, 4, CDef.DeadReason.後追);
        village = service.selectVillage(villageId, false, false);
        villagePlayer = village.getVillagePlayers().filterBy(vp -> vp.getVillagePlayerId().equals(villagePlayerId)).list.get(0);
        assertTrue(villagePlayer.isDeadWhen(4));
    }

    // VillagePlayerのテストだが便宜上ここにおく
    @Test
    public void test_roomnumber() {
        // ## Arrange ##
        Integer villageId = assist.createPrologueFullMemberVillage();
        assist.toNextDay(villageId); // 1日目へ
        assist.toNextDay(villageId); // 2日目へ
        assist.toNextDay(villageId); // 3日目へ
        assist.toNextDay(villageId); // 4日目へ

        Village village = service.selectVillage(villageId, false, false);
        // 生存している人
        VillagePlayer villagePlayer = village.getVillagePlayers().filterAlive().list.get(0);
        Integer villagePlayerId = villagePlayer.getVillagePlayerId();
        Integer roomNumber = villagePlayer.getRoomNumber();
        service.assignRoom(villagePlayer, 90, 2);
        service.assignRoom(villagePlayer, 91, 3);
        service.assignRoom(villagePlayer, 92, 4);
        village = service.selectVillage(villageId, false, false);
        villagePlayer = village.getVillagePlayers().filterBy(vp -> vp.getVillagePlayerId().equals(villagePlayerId)).list.get(0);
        assertTrue(villagePlayer.getRoomNumber().equals(92));
        assertTrue(villagePlayer.getRoomNumberWhen(1).equals(roomNumber));
        assertTrue(villagePlayer.getRoomNumberWhen(2).equals(90));
        assertTrue(villagePlayer.getRoomNumberWhen(3).equals(91));
        assertTrue(villagePlayer.getRoomNumberWhen(4).equals(92));
        assertTrue(village.getVillagePlayers().findByRoomNumber(roomNumber, 1).isPresent());
        assertTrue(!village.getVillagePlayers().findByRoomNumber(92, 1).isPresent());
        assertTrue(village.getVillagePlayers().findByRoomNumber(90, 2).isPresent());
        assertTrue(village.getVillagePlayers().findByRoomNumber(91, 3).isPresent());
        assertTrue(village.getVillagePlayers().findByRoomNumber(92, 4).isPresent());
    }
}
