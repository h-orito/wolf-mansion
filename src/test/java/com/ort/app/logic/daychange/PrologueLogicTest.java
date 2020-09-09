package com.ort.app.logic.daychange;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.time.LocalDateTime;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import com.ort.TestAssist;
import com.ort.WerewolfMansionTest;
import com.ort.app.datasource.AbilityService;
import com.ort.app.datasource.VillageService;
import com.ort.app.logic.TwitterLogic;
import com.ort.app.web.dto.VillageInfo;
import com.ort.dbflute.allcommon.CDef;
import com.ort.dbflute.exbhv.VillagePlayerBhv;
import com.ort.dbflute.exentity.Abilities;
import com.ort.dbflute.exentity.Village;
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
    @Autowired
    private AbilityService abilityService;
    @MockBean
    private TwitterLogic twitterLogic;

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

    @Test
    public void test_extendOrCancelVillage_廃村() {
        // ## Arrange ##
        // 村作成してダミーを参加させる
        Integer villageId = assist.createVillage();
        assist.participate(villageId, 1, 1, CDef.Skill.おまかせ, CDef.Skill.おまかせ);

        VillageInfo villageInfo = helper.convertToVillageInfo(villageService.selectVillage(villageId, false, false));

        // ## Act ##
        logic.extendOrCancelVillage(villageInfo, villageInfo.days.latestDay().getDaychangeDatetime().minusSeconds(1L));

        // ## Assert ##
        // 廃村になっている
        Village village = villageService.selectVillage(villageId, false, false);
        assertTrue(village.isVillageStatusCode廃村());
    }

    @Test
    public void test_extendOrCancelVillage_延長() {
        // ## Arrange ##
        // 村作成してダミーを参加させる
        Integer villageId = assist.createVillage();
        assist.participate(villageId, 1, 1, CDef.Skill.おまかせ, CDef.Skill.おまかせ);
        // ダミー以外を1人参加させる
        assist.participate(villageId, 2, 2, CDef.Skill.おまかせ, CDef.Skill.おまかせ);

        // ## Act ##
        VillageInfo villageInfo = helper.convertToVillageInfo(villageService.selectVillage(villageId, false, false));
        LocalDateTime daychangeDatetime = villageInfo.days.latestDay().getDaychangeDatetime().minusSeconds(1L);
        logic.extendOrCancelVillage(villageInfo, daychangeDatetime);

        // ## Assert ##
        // 廃村になっていない
        Village village = villageService.selectVillage(villageId, false, false);
        assertFalse(village.isVillageStatusCode廃村());
        // 1日延長されている
        assertEquals(daychangeDatetime.plusDays(1L), village.getVillageDays().latestDay().getDaychangeDatetime());
    }

    public void test_startVillage() {
        // ## Arrange ##
        // 村を作成してフル参加させる
        Integer villageId = assist.createPrologueFullMemberVillage();

        // mock
        Mockito.doNothing().when(twitterLogic);

        // ## Act ##
        VillageInfo villageInfo = helper.convertToVillageInfo(villageService.selectVillage(villageId, false, false));
        logic.startVillage(villageInfo, 1);

        // ## Assert ##
        Village village = villageService.selectVillage(villageId, true, true);
        // ステータス
        assertTrue(village.isVillageStatusCode進行中());
        // 役職
        assertTrue(village.getVillagePlayers().filterNotSpecatate().filterNotGone().list.stream()
                .allMatch(vp -> vp.getSkillCodeAsSkill() != null));
        assertTrue(village.getVillagePlayers().filterSpectate().list.stream().allMatch(vp -> vp.getSkillCodeAsSkill() == null));
        assertTrue(village.getVillagePlayers().filterBy(vp -> vp.isIsGoneTrue()).list.stream()
                .allMatch(vp -> vp.getSkillCodeAsSkill() == null));
        // 部屋
        assertTrue(
                village.getVillagePlayers().filterNotSpecatate().filterNotGone().list.stream().allMatch(vp -> vp.getRoomNumber() != null));
        assertTrue(village.getVillagePlayers().filterSpectate().list.stream().allMatch(vp -> vp.getRoomNumber() == null));
        assertTrue(village.getVillagePlayers().filterBy(vp -> vp.isIsGoneTrue()).list.stream().allMatch(vp -> vp.getRoomNumber() == null));
        // 能力
        Abilities abilities = abilityService.selectAbilities(villageId);
        assertTrue(!abilities.filterByDay(1).list.isEmpty());
    }
}
