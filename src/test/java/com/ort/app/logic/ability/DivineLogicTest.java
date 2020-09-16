package com.ort.app.logic.ability;

import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import com.ort.TestAssist;
import com.ort.WerewolfMansionTest;
import com.ort.app.datasource.AbilityService;
import com.ort.app.datasource.FootstepService;
import com.ort.app.datasource.VillageService;
import com.ort.app.datasource.VoteService;
import com.ort.app.logic.FootstepLogic;
import com.ort.app.logic.daychange.DayChangeVillage;
import com.ort.dbflute.allcommon.CDef;
import com.ort.dbflute.exbhv.AbilityBhv;
import com.ort.dbflute.exentity.Abilities;
import com.ort.dbflute.exentity.Ability;
import com.ort.dbflute.exentity.Footsteps;
import com.ort.dbflute.exentity.Village;
import com.ort.dbflute.exentity.VillagePlayer;
import com.ort.dbflute.exentity.VillagePlayers;
import com.ort.dbflute.exentity.Votes;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class DivineLogicTest extends WerewolfMansionTest {

    @Autowired
    private TestAssist assist;
    @Autowired
    private DivineLogic logic;
    @Autowired
    private VillageService villageService;
    @Autowired
    private VoteService voteService;
    @Autowired
    private AbilityService abilityService;
    @Autowired
    private FootstepService footstepService;
    @Autowired
    private FootstepLogic footstepLogic;
    @Autowired
    private AbilityBhv abilityBhv;

    @Test
    public void test_divine_占い師() {
        // ## Arrange ##
        Integer villageId = assist.createPrologueFullMemberVillage("村狼狼占賢星村村村");
        assist.toNextDay(villageId); // 1日目へ
        assist.addNextDay(villageId); // 2日目を追加

        // ## Act ##
        Village village = villageService.selectVillage(villageId, false, false);
        Abilities abilities = abilityService.selectAbilities(villageId);
        Votes votes = voteService.selectVotes(villageId);
        DayChangeVillage dayChangeVillage = new DayChangeVillage(villageId, village.getVillageDays().latestDay().getDay(),
                village.getVillagePlayers(), abilities, votes, village.getVillageSettingsAsOne().get());
        logic.divine(dayChangeVillage);

        // ## Assert ##
        assist.selectLatestMessage(villageId, 2).forEach(mes -> {
            System.out.println(mes.getMessageContent());
        });
        assertTrue(assist.selectLatestMessage(villageId, 2).stream().filter(mes -> mes.getMessageContent().contains("ようだ。")).count() == 3L);
    }

    @Test
    public void test_divine_呪殺() {
        // ## Arrange ##
        Integer villageId = assist.createPrologueFullMemberVillage("村狼狼占狐村村村村");
        assist.toNextDay(villageId); // 1日目へ
        assist.addNextDay(villageId); // 2日目を追加

        Abilities abilities = abilityService.selectAbilities(villageId);
        Ability divine = abilities.filterByDay(1).filterByType(CDef.AbilityType.占い).list.get(0);
        Village village = villageService.selectVillage(villageId, false, false);
        VillagePlayer fox = village.getVillagePlayers().filterBySkill(CDef.Skill.妖狐).list.get(0);
        divine.setTargetCharaId(fox.getCharaId());
        abilityBhv.update(divine);
        abilities = abilityService.selectAbilities(villageId);

        // ## Act ##
        Votes votes = voteService.selectVotes(villageId);
        DayChangeVillage dayChangeVillage = new DayChangeVillage(villageId, village.getVillageDays().latestDay().getDay(),
                village.getVillagePlayers(), abilities, votes, village.getVillageSettingsAsOne().get());
        logic.divine(dayChangeVillage);

        // ## Assert ##
        village = villageService.selectVillage(villageId, false, false);
        assertTrue(village.getVillagePlayers().findByCharaId(fox.getCharaId()).isDeadReasonCode呪殺());
        assertTrue(dayChangeVillage.deadPlayers.list.stream()
                .anyMatch(dp -> dp.villagePlayer.getVillagePlayerId().equals(fox.getVillagePlayerId()) && dp.reason == CDef.DeadReason.呪殺));
    }

    @Test
    public void test_divine_逆呪殺() {
        // ## Arrange ##
        Integer villageId = assist.createPrologueFullMemberVillage("村狼狼占呪村村村村");
        assist.toNextDay(villageId); // 1日目へ
        assist.addNextDay(villageId); // 2日目を追加

        Abilities abilities = abilityService.selectAbilities(villageId);
        Ability divine = abilities.filterByDay(1).filterByType(CDef.AbilityType.占い).list.get(0);
        Village village = villageService.selectVillage(villageId, false, false);
        VillagePlayer curseWolf = village.getVillagePlayers().filterBySkill(CDef.Skill.呪狼).list.get(0);
        divine.setTargetCharaId(curseWolf.getCharaId());
        abilityBhv.update(divine);
        abilities = abilityService.selectAbilities(villageId);

        // ## Act ##
        Votes votes = voteService.selectVotes(villageId);
        DayChangeVillage dayChangeVillage = new DayChangeVillage(villageId, village.getVillageDays().latestDay().getDay(),
                village.getVillagePlayers(), abilities, votes, village.getVillageSettingsAsOne().get());
        logic.divine(dayChangeVillage);

        // ## Assert ##
        village = villageService.selectVillage(villageId, false, false);
        VillagePlayer seer = village.getVillagePlayers().findByCharaId(divine.getCharaId());
        assertTrue(village.getVillagePlayers().findByCharaId(seer.getCharaId()).isDeadReasonCode呪殺());
        assertTrue(dayChangeVillage.deadPlayers.list.stream().anyMatch(
                dp -> dp.villagePlayer.getVillagePlayerId().equals(seer.getVillagePlayerId()) && dp.reason == CDef.DeadReason.呪殺));
    }

    @Test
    public void test_insertDefaultDivine() {
        // ## Arrange ##
        Integer villageId = assist.createPrologueFullMemberVillage("村狼狼占賢星村村");
        assist.toNextDay(villageId); // 1日目へ
        assist.addNextDay(villageId); // 2日目を追加

        // ## Act ##
        Village village = villageService.selectVillage(villageId, false, false);
        logic.insertDefaultDivine(village, 2);

        // ## Assert ##
        VillagePlayer seer = village.getVillagePlayers().filterBySkill(CDef.Skill.占い師).list.get(0);
        VillagePlayer wise = village.getVillagePlayers().filterBySkill(CDef.Skill.賢者).list.get(0);
        VillagePlayer astro = village.getVillagePlayers().filterBySkill(CDef.Skill.占星術師).list.get(0);
        Abilities abilities = abilityService.selectAbilities(villageId);
        assertTrue(!abilities.filterByDay(2).filterByChara(seer.getCharaId()).list.isEmpty());
        assertTrue(!abilities.filterByDay(2).filterByChara(wise.getCharaId()).list.isEmpty());
        assertTrue(!abilities.filterByDay(2).filterByChara(astro.getCharaId()).list.isEmpty());
        Footsteps footsteps = footstepService.selectFootsteps(villageId);
        assertTrue(!footsteps.filterByDay(2).filterByChara(seer.getCharaId()).list.isEmpty());
        assertTrue(!footsteps.filterByDay(2).filterByChara(wise.getCharaId()).list.isEmpty());
        assertTrue(!footsteps.filterByDay(2).filterByChara(astro.getCharaId()).list.isEmpty());
    }

    @Test
    public void test_setAbility() {
        // ## Arrange ##
        Integer villageId = assist.createPrologueFullMemberVillage("村狼狼占賢星村村");
        assist.toNextDay(villageId); // 1日目へ
        assist.addNextDay(villageId); // 2日目を追加

        Village village = villageService.selectVillage(villageId, false, false);
        VillagePlayer seer = village.getVillagePlayers().filterBySkill(CDef.Skill.占い師).list.get(0);
        VillagePlayer target = logic.getSelectableTarget(village, seer).list.get(0);
        List<String> footstepList = footstepLogic.getFootstepCandidateList(villageId, seer, 2, seer.getCharaId(), target.getCharaId());

        // ## Act ##
        logic.setAbility(village, seer, 2, target.getCharaId(), footstepList.get(0));

        // ## Assert ##
        Abilities abilities = abilityService.selectAbilities(villageId);
        assertTrue(!abilities.filterByDay(2).filterByChara(seer.getCharaId()).filterByTargetChara(target.getCharaId()).list.isEmpty());
        Footsteps footsteps = footstepService.selectFootsteps(villageId);
        assertTrue(
                footsteps.filterByDay(2).filterByChara(seer.getCharaId()).list.get(0).getFootstepRoomNumbers().equals(footstepList.get(0)));
    }

    @Test
    public void test_setAbility_死亡している() {
        // ## Arrange ##
        Integer villageId = assist.createPrologueFullMemberVillage("村狼狼占賢星村村");
        assist.toNextDay(villageId); // 1日目へ
        Village village = villageService.selectVillage(villageId, false, false);
        VillagePlayer seer = village.getVillagePlayers().filterBySkill(CDef.Skill.占い師).list.get(0);
        assist.kill(seer, 1, CDef.DeadReason.処刑);
        assist.addNextDay(villageId); // 2日目を追加

        VillagePlayer target = logic.getSelectableTarget(village, seer).list.get(0);
        List<String> footstepList = footstepLogic.getFootstepCandidateList(villageId, seer, 2, seer.getCharaId(), target.getCharaId());
        village = villageService.selectVillage(villageId, false, false);

        // ## Act ##
        logic.setAbility(village, seer, 2, target.getCharaId(), footstepList.get(0));

        // ## Assert ##
        Abilities abilities = abilityService.selectAbilities(villageId);
        assertTrue(abilities.filterByDay(2).filterByChara(seer.getCharaId()).filterByTargetChara(target.getCharaId()).list.isEmpty());
        Footsteps footsteps = footstepService.selectFootsteps(villageId);
        assertTrue(footsteps.filterByDay(2).filterByChara(seer.getCharaId()).list.isEmpty());
    }

    @Test
    public void test_setAbility_選べない相手を選んでいる() {
        // ## Arrange ##
        Integer villageId = assist.createPrologueFullMemberVillage("村狼狼占賢星村村");
        assist.toNextDay(villageId); // 1日目へ
        assist.addNextDay(villageId); // 2日目を追加

        Village village = villageService.selectVillage(villageId, false, false);
        VillagePlayer seer = village.getVillagePlayers().filterBySkill(CDef.Skill.占い師).list.get(0);

        // ## Act ##
        logic.setAbility(village, seer, 2, seer.getCharaId(), "なし");

        // ## Assert ##
        Abilities abilities = abilityService.selectAbilities(villageId);
        assertTrue(abilities.filterByDay(2).filterByChara(seer.getCharaId()).filterByTargetChara(seer.getCharaId()).list.isEmpty());
    }

    @Test
    public void test_setAbility_足音がおかしい() {
        // ## Arrange ##
        Integer villageId = assist.createPrologueFullMemberVillage("村狼狼占賢星村村");
        assist.toNextDay(villageId); // 1日目へ
        assist.addNextDay(villageId); // 2日目を追加

        Village village = villageService.selectVillage(villageId, false, false);
        VillagePlayer seer = village.getVillagePlayers().filterBySkill(CDef.Skill.占い師).list.get(0);
        VillagePlayer target = logic.getSelectableTarget(village, seer).list.get(0);
        String footstep = "01,02,03,04,05,06";

        // ## Act ##
        logic.setAbility(village, seer, 2, target.getCharaId(), footstep);

        // ## Assert ##
        Footsteps footsteps = footstepService.selectFootsteps(villageId);
        assertTrue(footsteps.filterByDay(2).filterByChara(seer.getCharaId()).list.isEmpty());
    }

    @Test
    public void test_getSelectableTarget() {
        // ## Arrange ##
        Integer villageId = assist.createPrologueFullMemberVillage("村狼狼占賢星村村");
        assist.toNextDay(villageId); // 1日目へ
        assist.addNextDay(villageId); // 2日目を追加

        Village village = villageService.selectVillage(villageId, false, false);
        VillagePlayer seer = village.getVillagePlayers().filterBySkill(CDef.Skill.占い師).list.get(0);

        // ## Act ##
        VillagePlayers targets = logic.getSelectableTarget(village, seer);

        // ## Assert ##
        assertTrue(targets.list.stream().noneMatch(vp -> vp.getVillagePlayerId().equals(seer.getVillagePlayerId())));
    }
}
