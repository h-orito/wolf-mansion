package com.ort.app.logic.daychange;

import static org.junit.Assert.assertTrue;

import java.time.LocalDateTime;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.ort.TestAssist;
import com.ort.WerewolfMansionTest;
import com.ort.app.datasource.VillageService;
import com.ort.app.web.dto.VillageInfo;
import com.ort.dbflute.allcommon.CDef;
import com.ort.dbflute.exbhv.VillagePlayerBhv;
import com.ort.dbflute.exentity.VillagePlayer;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PrologueLogicTest extends WerewolfMansionTest {

    @Autowired
    private TestAssist assist;
    @Autowired
    private PrologueLogic logic;
    @Autowired
    private DayChangeLogicHelper helper;
    @Autowired
    private VillagePlayerBhv villagePlayerBhv;
    @Autowired
    private VillageService villageService;

    @Test
    public void test_leaveVillageIfNeeded_退村あり() {
        // ## Arrange ##
        // 村作成してダミーを参加させる
        Integer villageId = assist.createVillage();
        assist.participate(villageId, 1, 1, CDef.Skill.おまかせ, CDef.Skill.おまかせ);
        // ダミー以外を1人参加させる
        Integer villagePlayerId = assist.participate(villageId, 2, 2, CDef.Skill.おまかせ, CDef.Skill.おまかせ);
        // 最終アクセス日時を1日以上前に
        VillagePlayer vp = new VillagePlayer();
        vp.setVillagePlayerId(villagePlayerId);
        vp.setLastAccessDatetime(LocalDateTime.now().minusHours(25L));
        villagePlayerBhv.update(vp);
        // もう1人参加させる
        Integer villagePlayerId2 = assist.participate(villageId, 3, 3, CDef.Skill.おまかせ, CDef.Skill.おまかせ);

        VillageInfo villageInfo = helper.convertToVillageInfo(villageService.selectVillage(villageId, false, false));
        // ## Act ##
        logic.leaveVillageIfNeeded(villageInfo);

        // ## Assert ##
        // 1日以上前にアクセスした人は追い出される
        VillagePlayer villagePlayer = villagePlayerBhv.selectByPK(villagePlayerId).get();
        assertTrue(villagePlayer.isIsGoneTrue());
        // 直近アクセスした人は追い出されない
        VillagePlayer villagePlayer2 = villagePlayerBhv.selectByPK(villagePlayerId2).get();
        assertTrue(villagePlayer2.isIsGoneFalse());
    }
}
