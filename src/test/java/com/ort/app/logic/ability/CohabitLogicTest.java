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
import com.ort.dbflute.exentity.Ability;
import com.ort.dbflute.exentity.Village;
import com.ort.dbflute.exentity.VillagePlayer;
import com.ort.dbflute.exentity.VillagePlayers;
import com.ort.dbflute.exentity.Votes;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class CohabitLogicTest extends WerewolfMansionTest {

    @Autowired
    private TestAssist assist;
    @Autowired
    private CohabitLogic logic;
    @Autowired
    private VillageService villageService;
    @Autowired
    private VoteService voteService;
    @Autowired
    private AbilityService abilityService;

    @Test
    public void test_cohabit() {
        // ## Arrange ##
        Integer villageId = assist.createPrologueFullMemberVillage("村狼狼狼棲棲村村");
        assist.toNextDay(villageId); // 1日目へ
        assist.addNextDay(villageId); // 2日目を追加

        Village village = villageService.selectVillage(villageId, false, false);
        Abilities abilities = abilityService.selectAbilities(villageId);
        Votes votes = voteService.selectVotes(villageId);

        // ## Act ##
        DayChangeVillage dayChangeVillage = new DayChangeVillage(villageId, village.getVillageDays().latestDay().getDay(),
                village.getVillagePlayers(), abilities, votes, village.getVillageSettingsAsOne().get());
        logic.cohabit(dayChangeVillage);

        // ## Assert ##
        assertTrue(assist.selectLatestMessage(villageId, 2).stream().anyMatch(mes -> {
            return mes.getMessageContent().contains("部屋で過ごすことにした");
        }));
    }

    @Test
    public void test_cohabit_移動元が死亡() {
        // ## Arrange ##
        Integer villageId = assist.createPrologueFullMemberVillage("村狼狼狼棲棲村村");
        assist.toNextDay(villageId); // 1日目へ
        assist.addNextDay(villageId); // 2日目を追加

        Village village = villageService.selectVillage(villageId, false, false);
        Abilities abilities = abilityService.selectAbilities(villageId);
        Votes votes = voteService.selectVotes(villageId);

        Ability cohabit = abilities.filterByDay(1).filterByType(CDef.AbilityType.同棲).list.get(0);
        VillagePlayer cohabiter = village.getVillagePlayers().findByCharaId(cohabit.getCharaId());
        assist.kill(cohabiter, 2, CDef.DeadReason.処刑);
        village = villageService.selectVillage(villageId, false, false);

        // ## Act ##
        DayChangeVillage dayChangeVillage = new DayChangeVillage(villageId, village.getVillageDays().latestDay().getDay(),
                village.getVillagePlayers(), abilities, votes, village.getVillageSettingsAsOne().get());
        dayChangeVillage.deadPlayers.add(cohabiter, CDef.DeadReason.処刑);
        logic.cohabit(dayChangeVillage);

        // ## Assert ##
        assertTrue(assist.selectLatestMessage(villageId, 2).stream().noneMatch(mes -> {
            return mes.getMessageContent().contains("部屋で過ごすことにした");
        }));
    }

    @Test
    public void test_cohabit_移動先が死亡() {
        // ## Arrange ##
        Integer villageId = assist.createPrologueFullMemberVillage("村狼狼狼棲棲村村");
        assist.toNextDay(villageId); // 1日目へ
        assist.addNextDay(villageId); // 2日目を追加

        Village village = villageService.selectVillage(villageId, false, false);
        Abilities abilities = abilityService.selectAbilities(villageId);
        Votes votes = voteService.selectVotes(villageId);

        Ability cohabit = abilities.filterByDay(1).filterByType(CDef.AbilityType.同棲).list.get(0);
        VillagePlayer targetCohabiter = village.getVillagePlayers().findByCharaId(cohabit.getTargetCharaId());
        assist.kill(targetCohabiter, 2, CDef.DeadReason.処刑);
        village = villageService.selectVillage(villageId, false, false);

        // ## Act ##
        DayChangeVillage dayChangeVillage = new DayChangeVillage(villageId, village.getVillageDays().latestDay().getDay(),
                village.getVillagePlayers(), abilities, votes, village.getVillageSettingsAsOne().get());
        dayChangeVillage.deadPlayers.add(targetCohabiter, CDef.DeadReason.処刑);
        logic.cohabit(dayChangeVillage);

        // ## Assert ##
        assertTrue(assist.selectLatestMessage(villageId, 2).stream().noneMatch(mes -> {
            return mes.getMessageContent().contains("部屋で過ごすことにした");
        }));
    }

    @Test
    public void test_insertDefaultCohabit() {
        // ## Arrange ##
        Integer villageId = assist.createPrologueFullMemberVillage("村狼狼狼棲棲棲棲");
        assist.toNextDay(villageId); // 1日目へ
        assist.addNextDay(villageId); // 2日目を追加

        Village village = villageService.selectVillage(villageId, false, false);

        // ## Act ##
        logic.insertDefaultCohabit(village, 2);

        // ## Assert ##
        Abilities abilities = abilityService.selectAbilities(villageId).filterByType(CDef.AbilityType.同棲).filterByDay(2);
        assertTrue(abilities.list.size() == 2);
        Ability cohabit1 = abilities.list.get(0);
        Ability cohabit2 = abilities.list.get(1);
        assertTrue(!cohabit1.getCharaId().equals(cohabit2.getCharaId()));
        assertTrue(!cohabit1.getCharaId().equals(cohabit2.getTargetCharaId()));
        assertTrue(!cohabit2.getCharaId().equals(cohabit1.getCharaId()));
        assertTrue(!cohabit2.getCharaId().equals(cohabit1.getTargetCharaId()));
        assertTrue(village.getVillagePlayers().findByCharaId(cohabit1.getCharaId()).getTargetCohabitor().getCharaId().equals(
                cohabit1.getTargetCharaId()));
        assertTrue(village.getVillagePlayers().findByCharaId(cohabit2.getCharaId()).getTargetCohabitor().getCharaId().equals(
                cohabit2.getTargetCharaId()));
    }

    @Test
    public void test_setAbility_同棲者でない() {
        // ## Arrange ##
        Integer villageId = assist.createPrologueFullMemberVillage("村狼狼狼村村棲棲");
        assist.toNextDay(villageId); // 1日目へ

        Village village = villageService.selectVillage(villageId, false, false);
        VillagePlayer wolf = village.getVillagePlayers().filterBySkill(CDef.Skill.人狼).list.get(0);
        VillagePlayer cohabiter = village.getVillagePlayers().filterBySkill(CDef.Skill.同棲者).list.get(0);

        // ## Act ##
        logic.setAbility(village, wolf, 1, cohabiter.getCharaId());

        // ## Assert ##
        Abilities abilities = abilityService.selectAbilities(villageId).filterByType(CDef.AbilityType.同棲).filterByDay(1);
        assertTrue(!abilities.list.get(0).getCharaId().equals(wolf.getCharaId()));
    }

    @Test
    public void test_setAbility_死亡している() {
        // ## Arrange ##
        Integer villageId = assist.createPrologueFullMemberVillage("村狼狼狼村村棲棲");
        assist.toNextDay(villageId); // 1日目へ

        Village village = villageService.selectVillage(villageId, false, false);
        VillagePlayer cohabiter = village.getVillagePlayers().filterBySkill(CDef.Skill.同棲者).list.get(0);
        assist.kill(cohabiter, 1, CDef.DeadReason.突然);

        // ## Act ##
        logic.setAbility(village, cohabiter, 1, cohabiter.getCharaId());

        // ## Assert ##
        // エラーが出ない
    }

    @Test
    public void test_setAbility_対象がおかしい() {
        // ## Arrange ##
        Integer villageId = assist.createPrologueFullMemberVillage("村狼狼狼村村棲棲");
        assist.toNextDay(villageId); // 1日目へ

        Village village = villageService.selectVillage(villageId, false, false);
        VillagePlayer wolf = village.getVillagePlayers().filterBySkill(CDef.Skill.人狼).list.get(0);
        VillagePlayer cohabiter = village.getVillagePlayers().filterBySkill(CDef.Skill.同棲者).list.get(0);

        // ## Act ##
        logic.setAbility(village, cohabiter, 1, wolf.getCharaId());

        // ## Assert ##
        Abilities abilities = abilityService.selectAbilities(villageId).filterByType(CDef.AbilityType.同棲).filterByDay(1);
        assertTrue(!abilities.list.get(0).getTargetCharaId().equals(wolf.getCharaId()));
    }

    @Test
    public void test_setAbility() {
        // ## Arrange ##
        Integer villageId = assist.createPrologueFullMemberVillage("村狼狼狼村村棲棲");
        assist.toNextDay(villageId); // 1日目へ

        Village village = villageService.selectVillage(villageId, false, false);
        VillagePlayer cohabiter = village.getVillagePlayers().filterBySkill(CDef.Skill.同棲者).list.get(0);
        VillagePlayer targetCohabiter = village.getVillagePlayers().filterBySkill(CDef.Skill.同棲者).list.get(1);

        // ## Act ##
        logic.setAbility(village, cohabiter, 1, targetCohabiter.getCharaId());

        // ## Assert ##
        Abilities abilities = abilityService.selectAbilities(villageId).filterByType(CDef.AbilityType.同棲).filterByDay(1);
        assertTrue(abilities.list.get(0).getCharaId().equals(cohabiter.getCharaId()));
        assertTrue(abilities.list.get(0).getTargetCharaId().equals(targetCohabiter.getCharaId()));
    }

    @Test
    public void test_getSelectableTarget() {
        // ## Arrange ##
        Integer villageId = assist.createPrologueFullMemberVillage("村狼狼狼村村棲棲");
        assist.toNextDay(villageId); // 1日目へ

        Village village = villageService.selectVillage(villageId, false, false);
        VillagePlayer cohabiter = village.getVillagePlayers().filterBySkill(CDef.Skill.同棲者).list.get(0);

        // ## Act ##
        VillagePlayers targets = logic.getSelectableTarget(cohabiter);

        // ## Assert ##
        assertTrue(targets.list.size() == 2);
        assertTrue(targets.list.stream().anyMatch(vp -> vp.getVillagePlayerId().equals(cohabiter.getVillagePlayerId())));
        assertTrue(
                targets.list.stream().anyMatch(vp -> vp.getVillagePlayerId().equals(cohabiter.getTargetCohabitor().getVillagePlayerId())));
    }
}
