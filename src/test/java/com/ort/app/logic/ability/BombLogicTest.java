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
import com.ort.app.datasource.FootstepService;
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
public class BombLogicTest extends WerewolfMansionTest {

    @Autowired
    private BombLogic logic;
    @Autowired
    private TestAssist assist;
    @Autowired
    private VillageService villageService;
    @Autowired
    private AbilityService abilityService;
    @Autowired
    private VoteService voteService;
    @Autowired
    private FootstepService footstepService;

    @Test
    public void test_insertBombMessages_設置していない() {
        // ## Arrange ##
        Integer villageId = assist.createPrologueFullMemberVillage("村狼狼狼村村村爆");
        assist.toNextDay(villageId); // 1日目へ
        assist.addNextDay(villageId); // 2日目を追加

        // ## Act ##
        Village village = villageService.selectVillage(villageId, false, false);
        Abilities abilities = abilityService.selectAbilities(villageId);
        Votes votes = voteService.selectVotes(villageId);
        DayChangeVillage dayChangeVillage = new DayChangeVillage(villageId, village.getVillageDays().latestDay().getDay(),
                village.getVillagePlayers(), abilities, votes, village.getVillageSettingsAsOne().get());
        logic.insertBombMessages(dayChangeVillage);

        // ## Assert ##
        assertTrue(assist.selectLatestMessage(villageId, 2).stream().noneMatch(mes -> {
            return mes.getMessageContent().contains("爆弾を設置した");
        }));
    }

    @Test
    public void test_insertBombMessages_設置した() {
        // ## Arrange ##
        Integer villageId = assist.createPrologueFullMemberVillage("村狼狼狼村村村爆");
        assist.toNextDay(villageId); // 1日目へ
        assist.addNextDay(villageId); // 2日目を追加
        Village village = villageService.selectVillage(villageId, false, false);
        VillagePlayer bomber = village.getVillagePlayers().filterBySkill(CDef.Skill.爆弾魔).list.stream().findFirst().get();
        VillagePlayers targets = logic.getSelectableTarget(village, bomber, 2);
        abilityService.insertAbility(villageId, 1, bomber.getCharaId(), targets.list.get(0).getCharaId(), null, CDef.AbilityType.爆弾設置);

        // ## Act ##
        Abilities abilities = abilityService.selectAbilities(villageId);
        Votes votes = voteService.selectVotes(villageId);
        DayChangeVillage dayChangeVillage = new DayChangeVillage(villageId, village.getVillageDays().latestDay().getDay(),
                village.getVillagePlayers(), abilities, votes, village.getVillageSettingsAsOne().get());
        logic.insertBombMessages(dayChangeVillage);

        // ## Assert ##
        assertTrue(assist.selectLatestMessage(villageId, 2).stream().anyMatch(mes -> {
            return mes.getMessageContent().contains("爆弾を設置した");
        }));
    }

    @Test
    public void test_insertBombMessages_突然死() {
        // ## Arrange ##
        Integer villageId = assist.createPrologueFullMemberVillage("村狼狼狼村村村爆");
        assist.toNextDay(villageId); // 1日目へ
        assist.addNextDay(villageId); // 2日目を追加
        Village village = villageService.selectVillage(villageId, false, false);
        VillagePlayer bomber = village.getVillagePlayers().filterBySkill(CDef.Skill.爆弾魔).list.stream().findFirst().get();
        VillagePlayers targets = logic.getSelectableTarget(village, bomber, 2);
        abilityService.insertAbility(villageId, 1, bomber.getCharaId(), targets.list.get(0).getCharaId(), null, CDef.AbilityType.爆弾設置);

        // ## Act ##
        Abilities abilities = abilityService.selectAbilities(villageId);
        Votes votes = voteService.selectVotes(villageId);
        DayChangeVillage dayChangeVillage = new DayChangeVillage(villageId, village.getVillageDays().latestDay().getDay(),
                village.getVillagePlayers(), abilities, votes, village.getVillageSettingsAsOne().get());
        assist.kill(bomber, 1, CDef.DeadReason.突然);
        village = villageService.selectVillage(villageId, false, false);
        dayChangeVillage.deadPlayers.add(bomber, CDef.DeadReason.突然);
        logic.insertBombMessages(dayChangeVillage);

        // ## Assert ##
        assertTrue(assist.selectLatestMessage(villageId, 2).stream().noneMatch(mes -> {
            return mes.getMessageContent().contains("爆弾を設置した");
        }));
    }

    @Test
    public void test_bomb_設置なし() {
        // ## Arrange ##
        Integer villageId = assist.createPrologueFullMemberVillage("村狼狼狼村村村爆");
        assist.toNextDay(villageId); // 1日目へ
        assist.addNextDay(villageId); // 2日目を追加

        // ## Act ##
        Village village = villageService.selectVillage(villageId, false, false);
        Abilities abilities = abilityService.selectAbilities(villageId);
        Votes votes = voteService.selectVotes(villageId);
        DayChangeVillage dayChangeVillage = new DayChangeVillage(villageId, village.getVillageDays().latestDay().getDay(),
                village.getVillagePlayers(), abilities, votes, village.getVillageSettingsAsOne().get());
        logic.bomb(dayChangeVillage);

        // ## Assert ##
        assertTrue(dayChangeVillage.deadPlayers.list.isEmpty());
    }

    @Test
    public void test_bomb_設置_突然死() {
        // ## Arrange ##
        Integer villageId = assist.createPrologueFullMemberVillage("村狼狼狼村村狂爆");
        assist.toNextDay(villageId); // 1日目へ
        assist.addNextDay(villageId); // 2日目を追加
        Village village = villageService.selectVillage(villageId, false, false);
        // 設置
        VillagePlayer bomber = village.getVillagePlayers().filterBySkill(CDef.Skill.爆弾魔).list.stream().findFirst().get();
        VillagePlayers targets = logic.getSelectableTarget(village, bomber, 2);
        abilityService.insertAbility(villageId, 1, bomber.getCharaId(), targets.list.get(0).getCharaId(), null, CDef.AbilityType.爆弾設置);
        // 狂人が足音を残す
        VillagePlayer madman = village.getVillagePlayers().filterBySkill(CDef.Skill.狂人).list.get(0);
        footstepService.deleteFootstep(villageId, 1, madman.getCharaId());
        footstepService.insertFootstep(villageId, 1, madman.getCharaId(), String.format("02d", targets.list.get(0).getRoomNumber()));

        // ## Act ##
        Abilities abilities = abilityService.selectAbilities(villageId);
        Votes votes = voteService.selectVotes(villageId);
        DayChangeVillage dayChangeVillage = new DayChangeVillage(villageId, village.getVillageDays().latestDay().getDay(),
                village.getVillagePlayers(), abilities, votes, village.getVillageSettingsAsOne().get());
        assist.kill(bomber, 1, CDef.DeadReason.突然);
        village = villageService.selectVillage(villageId, false, false);
        // 爆弾魔が突然死
        dayChangeVillage.deadPlayers.add(bomber, CDef.DeadReason.突然);
        logic.insertBombMessages(dayChangeVillage);

        // ## Assert ##
        assertTrue(dayChangeVillage.deadPlayers.list.stream()
                .noneMatch(dp -> !dp.villagePlayer.getVillagePlayerId().equals(bomber.getVillagePlayerId())));
    }

    @Test
    public void test_bomb_設置_通過者なし() {
        // ## Arrange ##
        Integer villageId = assist.createPrologueFullMemberVillage("村狼狼狼村村狂爆");
        assist.toNextDay(villageId); // 1日目へ
        assist.addNextDay(villageId); // 2日目を追加
        Village village = villageService.selectVillage(villageId, false, false);
        // 設置
        VillagePlayer bomber = village.getVillagePlayers().filterBySkill(CDef.Skill.爆弾魔).list.stream().findFirst().get();
        VillagePlayers targets = logic.getSelectableTarget(village, bomber, 2);
        abilityService.insertAbility(villageId, 1, bomber.getCharaId(), targets.list.get(0).getCharaId(), null, CDef.AbilityType.爆弾設置);

        // ## Act ##
        Abilities abilities = abilityService.selectAbilities(villageId);
        Votes votes = voteService.selectVotes(villageId);
        DayChangeVillage dayChangeVillage = new DayChangeVillage(villageId, village.getVillageDays().latestDay().getDay(),
                village.getVillagePlayers(), abilities, votes, village.getVillageSettingsAsOne().get());
        logic.bomb(dayChangeVillage);

        // ## Assert ##
        village = villageService.selectVillage(villageId, false, false);
        assertTrue(dayChangeVillage.deadPlayers.list.stream().anyMatch(
                dp -> dp.villagePlayer.getVillagePlayerId().equals(bomber.getVillagePlayerId()) && dp.reason == CDef.DeadReason.爆死));
        assertTrue(village.getVillagePlayers().findByCharaId(bomber.getCharaId()).isDeadReasonCode爆死());
    }

    @Test
    public void test_bomb_設置_通過者あり() {
        // ## Arrange ##
        Integer villageId = assist.createPrologueFullMemberVillage("村狼狼狼村村狂爆");
        assist.toNextDay(villageId); // 1日目へ
        assist.addNextDay(villageId); // 2日目を追加
        Village village = villageService.selectVillage(villageId, false, false);
        // 設置
        VillagePlayer bomber = village.getVillagePlayers().filterBySkill(CDef.Skill.爆弾魔).list.stream().findFirst().get();
        VillagePlayer target = logic.getSelectableTarget(village, bomber, 2).list.get(0);
        abilityService.insertAbility(villageId, 1, bomber.getCharaId(), target.getCharaId(), null, CDef.AbilityType.爆弾設置);
        // 狂人が足音を残す
        VillagePlayer madman = village.getVillagePlayers().filterBySkill(CDef.Skill.狂人).list.get(0);
        footstepService.deleteFootstep(villageId, 1, madman.getCharaId());
        footstepService.insertFootstep(villageId, 1, madman.getCharaId(), target.getRoomNumber().toString());

        // ## Act ##
        Abilities abilities = abilityService.selectAbilities(villageId);
        Votes votes = voteService.selectVotes(villageId);
        DayChangeVillage dayChangeVillage = new DayChangeVillage(villageId, village.getVillageDays().latestDay().getDay(),
                village.getVillagePlayers(), abilities, votes, village.getVillageSettingsAsOne().get());
        logic.bomb(dayChangeVillage);

        // ## Assert ##
        village = villageService.selectVillage(villageId, false, false);
        assertTrue(dayChangeVillage.deadPlayers.list.stream()
                .noneMatch(dp -> dp.villagePlayer.getVillagePlayerId().equals(bomber.getVillagePlayerId())));
        assertTrue(dayChangeVillage.deadPlayers.list.stream().anyMatch(
                dp -> dp.villagePlayer.getVillagePlayerId().equals(target.getVillagePlayerId()) && dp.reason == CDef.DeadReason.爆死));
        assertTrue(dayChangeVillage.deadPlayers.list.stream().anyMatch(
                dp -> dp.villagePlayer.getVillagePlayerId().equals(madman.getVillagePlayerId()) && dp.reason == CDef.DeadReason.爆死));
        assertTrue(village.getVillagePlayers().findByCharaId(bomber.getCharaId()).isIsDeadFalse());
        assertTrue(village.getVillagePlayers().findByCharaId(target.getCharaId()).isDeadReasonCode爆死());
        assertTrue(village.getVillagePlayers().findByCharaId(madman.getCharaId()).isDeadReasonCode爆死());
    }

    @Test
    public void test_bomb_設置_通過者あり_設置された部屋が同棲者の部屋で移動元() {
        // ## Arrange ##
        Integer villageId = assist.createPrologueFullMemberVillage("村狼狼狼棲棲狂爆");
        assist.toNextDay(villageId); // 1日目へ
        assist.addNextDay(villageId); // 2日目を追加
        Village village = villageService.selectVillage(villageId, false, false);
        Abilities abilities = abilityService.selectAbilities(villageId);

        // 移動元の同棲者の部屋に設置
        Ability cohabit = abilities.filterByType(CDef.AbilityType.同棲).filterByDay(1).list.get(0);
        VillagePlayer bomber = village.getVillagePlayers().filterBySkill(CDef.Skill.爆弾魔).list.stream().findFirst().get();
        VillagePlayer cohabiter = village.getVillagePlayers().findByCharaId(cohabit.getCharaId());
        abilityService.insertAbility(villageId, 1, bomber.getCharaId(), cohabiter.getCharaId(), null, CDef.AbilityType.爆弾設置);
        // 狂人が足音を残す
        VillagePlayer madman = village.getVillagePlayers().filterBySkill(CDef.Skill.狂人).list.get(0);
        footstepService.deleteFootstep(villageId, 1, madman.getCharaId());
        footstepService.insertFootstep(villageId, 1, madman.getCharaId(), cohabiter.getRoomNumber().toString());

        // ## Act ##
        abilities = abilityService.selectAbilities(villageId);
        Votes votes = voteService.selectVotes(villageId);
        DayChangeVillage dayChangeVillage = new DayChangeVillage(villageId, village.getVillageDays().latestDay().getDay(),
                village.getVillagePlayers(), abilities, votes, village.getVillageSettingsAsOne().get());
        logic.bomb(dayChangeVillage);

        // ## Assert ##
        village = villageService.selectVillage(villageId, false, false);
        assertTrue(dayChangeVillage.deadPlayers.list.stream()
                .noneMatch(dp -> dp.villagePlayer.getVillagePlayerId().equals(bomber.getVillagePlayerId())));
        assertTrue(dayChangeVillage.deadPlayers.list.stream()
                .noneMatch(dp -> dp.villagePlayer.getVillagePlayerId().equals(cohabiter.getVillagePlayerId())));
        assertTrue(dayChangeVillage.deadPlayers.list.stream().anyMatch(
                dp -> dp.villagePlayer.getVillagePlayerId().equals(madman.getVillagePlayerId()) && dp.reason == CDef.DeadReason.爆死));
        assertTrue(village.getVillagePlayers().findByCharaId(bomber.getCharaId()).isIsDeadFalse());
        assertTrue(village.getVillagePlayers().findByCharaId(cohabiter.getCharaId()).isIsDeadFalse());
        assertTrue(village.getVillagePlayers().findByCharaId(madman.getCharaId()).isDeadReasonCode爆死());
    }

    @Test
    public void test_bomb_設置_通過者あり_設置された部屋が同棲者の部屋で移動先() {
        // ## Arrange ##
        Integer villageId = assist.createPrologueFullMemberVillage("村狼狼狼棲棲狂爆");
        assist.toNextDay(villageId); // 1日目へ
        assist.addNextDay(villageId); // 2日目を追加
        Village village = villageService.selectVillage(villageId, false, false);
        Abilities abilities = abilityService.selectAbilities(villageId);

        // 移動元の同棲者の部屋に設置
        Ability cohabit = abilities.filterByType(CDef.AbilityType.同棲).filterByDay(1).list.get(0);
        VillagePlayer bomber = village.getVillagePlayers().filterBySkill(CDef.Skill.爆弾魔).list.stream().findFirst().get();
        VillagePlayer cohabiter = village.getVillagePlayers().findByCharaId(cohabit.getCharaId());
        VillagePlayer targetCohabiter = village.getVillagePlayers().findByCharaId(cohabit.getTargetCharaId());
        abilityService.insertAbility(villageId, 1, bomber.getCharaId(), targetCohabiter.getCharaId(), null, CDef.AbilityType.爆弾設置);
        // 狂人が足音を残す
        VillagePlayer madman = village.getVillagePlayers().filterBySkill(CDef.Skill.狂人).list.get(0);
        footstepService.deleteFootstep(villageId, 1, madman.getCharaId());
        footstepService.insertFootstep(villageId, 1, madman.getCharaId(), targetCohabiter.getRoomNumber().toString());

        // ## Act ##
        abilities = abilityService.selectAbilities(villageId);
        Votes votes = voteService.selectVotes(villageId);
        DayChangeVillage dayChangeVillage = new DayChangeVillage(villageId, village.getVillageDays().latestDay().getDay(),
                village.getVillagePlayers(), abilities, votes, village.getVillageSettingsAsOne().get());
        logic.bomb(dayChangeVillage);

        // ## Assert ##
        village = villageService.selectVillage(villageId, false, false);
        assertTrue(dayChangeVillage.deadPlayers.list.stream()
                .noneMatch(dp -> dp.villagePlayer.getVillagePlayerId().equals(bomber.getVillagePlayerId())));
        assertTrue(dayChangeVillage.deadPlayers.list.stream()
                .anyMatch(dp -> dp.villagePlayer.getVillagePlayerId().equals(cohabiter.getVillagePlayerId())));
        assertTrue(dayChangeVillage.deadPlayers.list.stream()
                .anyMatch(dp -> dp.villagePlayer.getVillagePlayerId().equals(targetCohabiter.getVillagePlayerId())));
        assertTrue(dayChangeVillage.deadPlayers.list.stream().anyMatch(
                dp -> dp.villagePlayer.getVillagePlayerId().equals(madman.getVillagePlayerId()) && dp.reason == CDef.DeadReason.爆死));
        assertTrue(village.getVillagePlayers().findByCharaId(bomber.getCharaId()).isIsDeadFalse());
        assertTrue(village.getVillagePlayers().findByCharaId(cohabiter.getCharaId()).isDeadReasonCode爆死());
        assertTrue(village.getVillagePlayers().findByCharaId(targetCohabiter.getCharaId()).isDeadReasonCode爆死());
        assertTrue(village.getVillagePlayers().findByCharaId(madman.getCharaId()).isDeadReasonCode爆死());
    }

    @Test
    public void test_setAbility_設置() {
        // ## Arrange ##
        Integer villageId = assist.createPrologueFullMemberVillage("村狼狼狼棲棲狂爆");
        assist.toNextDay(villageId); // 1日目へ
        assist.addNextDay(villageId); // 2日目を追加
        Village village = villageService.selectVillage(villageId, false, false);

        VillagePlayer bomber = village.getVillagePlayers().filterBySkill(CDef.Skill.爆弾魔).list.stream().findFirst().get();
        VillagePlayers targets = logic.getSelectableTarget(village, bomber, 2);

        // ## Act ##
        logic.setAbility(village, bomber, 2, targets.list.get(0).getCharaId());

        // ## Assert ##
        Abilities abilities = abilityService.selectAbilities(villageId);
        assertTrue(!abilities.filterBy(
                a -> a.getCharaId().equals(bomber.getCharaId()) && a.getTargetCharaId().equals(targets.list.get(0).getCharaId())).list
                        .isEmpty());
    }

    @Test
    public void test_setAbility_解除() {
        // ## Arrange ##
        Integer villageId = assist.createPrologueFullMemberVillage("村狼狼狼棲棲狂爆");
        assist.toNextDay(villageId); // 1日目へ
        assist.addNextDay(villageId); // 2日目を追加
        Village village = villageService.selectVillage(villageId, false, false);

        VillagePlayer bomber = village.getVillagePlayers().filterBySkill(CDef.Skill.爆弾魔).list.stream().findFirst().get();

        // ## Act ##
        logic.setAbility(village, bomber, 2, null);

        // ## Assert ##
        Abilities abilities = abilityService.selectAbilities(villageId);
        assertTrue(abilities.filterBy(a -> a.getCharaId().equals(bomber.getCharaId())).list.isEmpty());
    }

    @Test
    public void test_setAbility_選べない対象を選んでいる() {
        // ## Arrange ##
        Integer villageId = assist.createPrologueFullMemberVillage("村狼狼狼棲棲狂爆");
        assist.toNextDay(villageId); // 1日目へ
        assist.addNextDay(villageId); // 2日目を追加
        Village village = villageService.selectVillage(villageId, false, false);

        VillagePlayer bomber = village.getVillagePlayers().filterBySkill(CDef.Skill.爆弾魔).list.stream().findFirst().get();

        // ## Act ##
        logic.setAbility(village, bomber, 2, bomber.getCharaId());

        // ## Assert ##
        Abilities abilities = abilityService.selectAbilities(villageId);
        assertTrue(abilities.filterBy(a -> a.getCharaId().equals(bomber.getCharaId())).list.isEmpty());
    }

    @Test
    public void test_setAbility_設置済み() {
        // ## Arrange ##
        Integer villageId = assist.createPrologueFullMemberVillage("村狼狼狼棲棲狂爆");
        assist.toNextDay(villageId); // 1日目へ
        assist.addNextDay(villageId); // 2日目を追加
        Village village = villageService.selectVillage(villageId, false, false);
        VillagePlayer bomber = village.getVillagePlayers().filterBySkill(CDef.Skill.爆弾魔).list.stream().findFirst().get();
        VillagePlayers targets = logic.getSelectableTarget(village, bomber, 2);
        abilityService.insertAbility(villageId, 1, bomber.getCharaId(), targets.list.get(0).getCharaId(), null, CDef.AbilityType.爆弾設置);

        // ## Act ##
        logic.setAbility(village, bomber, 2, targets.list.get(0).getCharaId());

        // ## Assert ##
        Abilities abilities = abilityService.selectAbilities(villageId);
        assertTrue(abilities.filterBy(a -> a.getDay().equals(2) && a.getCharaId().equals(bomber.getCharaId())).list.isEmpty());
    }

    @Test
    public void test_getSelectableTarget_未設置() {
        // ## Arrange ##
        Integer villageId = assist.createPrologueFullMemberVillage("村狼狼狼棲棲狂爆");
        assist.toNextDay(villageId); // 1日目へ
        assist.addNextDay(villageId); // 2日目を追加
        Village village = villageService.selectVillage(villageId, false, false);
        VillagePlayer bomber = village.getVillagePlayers().filterBySkill(CDef.Skill.爆弾魔).list.stream().findFirst().get();

        // ## Act ##
        VillagePlayers targets = logic.getSelectableTarget(village, bomber, 2);

        // ## Assert ##
        assertTrue(!targets.list.isEmpty());
        assertTrue(targets.list.stream().noneMatch(vp -> vp.getVillagePlayerId().equals(bomber.getVillagePlayerId())));
    }

    @Test
    public void test_getSelectableTarget_設置済み() {
        // ## Arrange ##
        Integer villageId = assist.createPrologueFullMemberVillage("村狼狼狼棲棲狂爆");
        assist.toNextDay(villageId); // 1日目へ
        assist.addNextDay(villageId); // 2日目を追加
        Village village = villageService.selectVillage(villageId, false, false);
        VillagePlayer bomber = village.getVillagePlayers().filterBySkill(CDef.Skill.爆弾魔).list.stream().findFirst().get();
        VillagePlayers targets = logic.getSelectableTarget(village, bomber, 2);
        abilityService.insertAbility(villageId, 1, bomber.getCharaId(), targets.list.get(0).getCharaId(), null, CDef.AbilityType.爆弾設置);

        // ## Act ##
        targets = logic.getSelectableTarget(village, bomber, 2);

        // ## Assert ##
        assertTrue(targets.list.isEmpty());
    }
}
