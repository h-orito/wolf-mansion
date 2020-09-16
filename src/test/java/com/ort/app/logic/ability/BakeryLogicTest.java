package com.ort.app.logic.ability;

import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import com.ort.TestAssist;
import com.ort.WerewolfMansionTest;
import com.ort.app.datasource.AbilityService;
import com.ort.app.datasource.VillageService;
import com.ort.app.datasource.VoteService;
import com.ort.app.logic.daychange.DayChangeVillage;
import com.ort.dbflute.allcommon.CDef;
import com.ort.dbflute.exentity.Abilities;
import com.ort.dbflute.exentity.Village;
import com.ort.dbflute.exentity.VillagePlayers;
import com.ort.dbflute.exentity.Votes;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class BakeryLogicTest extends WerewolfMansionTest {

    @Autowired
    private BakeryLogic logic;
    @Autowired
    private TestAssist assist;
    @Autowired
    private VillageService villageService;
    @Autowired
    private AbilityService abilityService;
    @Autowired
    private VoteService voteService;

    @Test
    public void test_insertBakeryMessageIfNeeded_1人生存() {
        // ## Arrange ##
        Integer villageId = assist.createPrologueFullMemberVillage("村狼狼パパ村村村");
        assist.toNextDay(villageId); // 1日目へ
        assist.addNextDay(villageId); // 2日目を追加

        Village village = villageService.selectVillage(villageId, false, false);
        VillagePlayers bakeryPlayers = village.getVillagePlayers().filterBySkill(CDef.Skill.パン屋);
        assist.kill(bakeryPlayers.list.get(0), 2, CDef.DeadReason.突然);

        // ## Act ##
        Abilities abilities = abilityService.selectAbilities(villageId);
        Votes votes = voteService.selectVotes(villageId);
        DayChangeVillage dayChangeVillage = new DayChangeVillage(villageId, village.getVillageDays().latestDay().getDay(),
                village.getVillagePlayers(), abilities, votes, village.getVillageSettingsAsOne().get());
        logic.insertBakeryMessageIfNeeded(dayChangeVillage);

        // ## Assert ##
        assertTrue(assist.selectLatestMessage(villageId, 2).stream().anyMatch(mes -> mes.getMessageContent().startsWith("パン屋がおいしい")));
    }

    @Test
    public void test_insertBakeryMessageIfNeeded_全員死亡_当日() {
        // ## Arrange ##
        Integer villageId = assist.createPrologueFullMemberVillage("村狼狼パパ村村村");
        assist.toNextDay(villageId); // 1日目へ
        assist.addNextDay(villageId); // 2日目を追加

        Village village = villageService.selectVillage(villageId, false, false);
        VillagePlayers bakeryPlayers = village.getVillagePlayers().filterBySkill(CDef.Skill.パン屋);
        assist.kill(bakeryPlayers.list.get(0), 1, CDef.DeadReason.突然);
        assist.kill(bakeryPlayers.list.get(1), 2, CDef.DeadReason.突然);

        // ## Act ##
        village = villageService.selectVillage(villageId, false, false);
        Abilities abilities = abilityService.selectAbilities(villageId);
        Votes votes = voteService.selectVotes(villageId);
        DayChangeVillage dayChangeVillage = new DayChangeVillage(villageId, village.getVillageDays().latestDay().getDay(),
                village.getVillagePlayers(), abilities, votes, village.getVillageSettingsAsOne().get());
        dayChangeVillage.deadPlayers.add(bakeryPlayers.list.get(1), CDef.DeadReason.突然);
        logic.insertBakeryMessageIfNeeded(dayChangeVillage);

        // ## Assert ##
        assertTrue(assist.selectLatestMessage(villageId, 2).stream().anyMatch(mes -> mes.getMessageContent().startsWith("今日からは")));
    }

    @Test
    public void test_insertBakeryMessageIfNeeded_全員死亡_翌日() {
        // ## Arrange ##
        Integer villageId = assist.createPrologueFullMemberVillage("村狼狼パパ村村村");
        assist.toNextDay(villageId); // 1日目へ
        assist.addNextDay(villageId); // 2日目を追加

        Village village = villageService.selectVillage(villageId, false, false);
        VillagePlayers bakeryPlayers = village.getVillagePlayers().filterBySkill(CDef.Skill.パン屋);
        assist.kill(bakeryPlayers.list.get(0), 1, CDef.DeadReason.突然);
        assist.kill(bakeryPlayers.list.get(1), 1, CDef.DeadReason.突然);

        // ## Act ##
        Abilities abilities = abilityService.selectAbilities(villageId);
        Votes votes = voteService.selectVotes(villageId);
        DayChangeVillage dayChangeVillage = new DayChangeVillage(villageId, village.getVillageDays().latestDay().getDay(),
                village.getVillagePlayers(), abilities, votes, village.getVillageSettingsAsOne().get());
        logic.insertBakeryMessageIfNeeded(dayChangeVillage);

        // ## Assert ##
        assertTrue(assist.selectLatestMessage(villageId, 2).stream().noneMatch(mes -> mes.getMessageContent().startsWith("今日からは")));
    }

}
