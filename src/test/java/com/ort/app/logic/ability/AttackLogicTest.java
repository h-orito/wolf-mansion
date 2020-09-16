package com.ort.app.logic.ability;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Optional;

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
import com.ort.dbflute.exbhv.VillageSettingsBhv;
import com.ort.dbflute.exentity.Abilities;
import com.ort.dbflute.exentity.Ability;
import com.ort.dbflute.exentity.Footstep;
import com.ort.dbflute.exentity.Footsteps;
import com.ort.dbflute.exentity.Village;
import com.ort.dbflute.exentity.VillagePlayer;
import com.ort.dbflute.exentity.VillagePlayers;
import com.ort.dbflute.exentity.VillageSettings;
import com.ort.dbflute.exentity.Votes;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class AttackLogicTest extends WerewolfMansionTest {

    @Autowired
    private TestAssist assist;
    @Autowired
    private AttackLogic logic;
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
    @Autowired
    private VillageSettingsBhv villageSettingsBhv;

    @Test
    public void test_attack_襲撃成功() {
        // ## Arrange ##
        Integer villageId = assist.createPrologueFullMemberVillage();
        assist.toNextDay(villageId); // 1日目へ
        assist.addNextDay(villageId); // 2日目を追加

        // ## Act ##
        Village village = villageService.selectVillage(villageId, false, false);
        Abilities abilities = abilityService.selectAbilities(villageId);
        Votes votes = voteService.selectVotes(villageId);
        DayChangeVillage dayChangeVillage = new DayChangeVillage(villageId, village.getVillageDays().latestDay().getDay(),
                village.getVillagePlayers(), abilities, votes, village.getVillageSettingsAsOne().get());
        logic.attack(dayChangeVillage);

        // ## Assert ##
        village = villageService.selectVillage(villageId, false, false);
        Ability ability = abilities.filterByDay(1).filterByType(CDef.AbilityType.襲撃).list.get(0);
        assertTrue(village.getVillagePlayers().findByCharaId(ability.getTargetCharaId()).isIsDeadTrue());
        assertTrue(village.getVillagePlayers().findByCharaId(ability.getTargetCharaId()).isDeadReasonCode襲撃());
        assertTrue(village.getVillagePlayers().findByCharaId(ability.getTargetCharaId()).getDeadDay() == 2);
        assertTrue(dayChangeVillage.deadPlayers.list.stream()
                .anyMatch(dp -> dp.villagePlayer.getCharaId().equals(ability.getTargetCharaId())));
    }

    @Test
    public void test_attack_襲撃失敗_処刑済み() {
        // ## Arrange ##
        Integer villageId = assist.createPrologueFullMemberVillage();
        assist.toNextDay(villageId); // 1日目へ
        assist.toNextDay(villageId); // 2日目へ
        assist.addNextDay(villageId); // 3日目を追加

        Village village = villageService.selectVillage(villageId, false, false);
        Abilities abilities = abilityService.selectAbilities(villageId);
        Votes votes = voteService.selectVotes(villageId);
        DayChangeVillage dayChangeVillage = new DayChangeVillage(villageId, village.getVillageDays().latestDay().getDay(),
                village.getVillagePlayers(), abilities, votes, village.getVillageSettingsAsOne().get());

        // 襲撃対象を処刑
        Ability ability = abilities.filterByDay(2).filterByType(CDef.AbilityType.襲撃).list.get(0);
        VillagePlayer target = village.getVillagePlayers().findByCharaId(ability.getTargetCharaId());
        assist.kill(target, 3, CDef.DeadReason.処刑);
        dayChangeVillage.deadPlayers.add(target, CDef.DeadReason.処刑);

        // ## Act ##
        logic.attack(dayChangeVillage);

        // ## Assert ##
        village = villageService.selectVillage(villageId, false, false);
        target = village.getVillagePlayers().findByCharaId(ability.getTargetCharaId());
        assertTrue(target.isIsDeadTrue());
        assertFalse(target.isDeadReasonCode襲撃());
        assertTrue(target.getDeadDay() == 3);
    }

    @Test
    public void test_attack_襲撃失敗_護衛() {
        // ## Arrange ##
        Integer villageId = assist.createPrologueFullMemberVillage();
        assist.toNextDay(villageId); // 1日目へ
        assist.toNextDay(villageId); // 2日目へ
        assist.addNextDay(villageId); // 3日目を追加

        Village village = villageService.selectVillage(villageId, false, false);
        Abilities abilities = abilityService.selectAbilities(villageId);

        // 襲撃対象を護衛
        Ability ability = abilities.filterByDay(2).filterByType(CDef.AbilityType.襲撃).list.get(0);
        VillagePlayer target = village.getVillagePlayers().findByCharaId(ability.getTargetCharaId());
        Ability guardAbility = abilityBhv.selectEntity(cb -> {
            cb.query().setVillageId_Equal(villageId);
            cb.query().setDay_Equal(2);
            cb.query().setAbilityTypeCode_Equal_護衛();
        }).get();
        guardAbility.setTargetCharaId(target.getCharaId());
        abilityBhv.update(guardAbility);

        abilities = abilityService.selectAbilities(villageId);
        Votes votes = voteService.selectVotes(villageId);
        DayChangeVillage dayChangeVillage = new DayChangeVillage(villageId, village.getVillageDays().latestDay().getDay(),
                village.getVillagePlayers(), abilities, votes, village.getVillageSettingsAsOne().get());

        // ## Act ##
        logic.attack(dayChangeVillage);

        // ## Assert ##
        village = villageService.selectVillage(villageId, false, false);
        target = village.getVillagePlayers().findByCharaId(ability.getTargetCharaId());
        assertTrue(target.isIsDeadFalse());
    }

    @Test
    public void test_attack_襲撃失敗_妖狐() {
        // ## Arrange ##
        Integer villageId = assist.createPrologueFullMemberVillage();
        assist.toNextDay(villageId); // 1日目へ
        assist.toNextDay(villageId); // 2日目へ
        assist.addNextDay(villageId); // 3日目を追加

        Village village = villageService.selectVillage(villageId, false, false);
        Abilities abilities = abilityService.selectAbilities(villageId);

        // 妖狐を襲撃
        Ability ability = abilities.filterByDay(2).filterByType(CDef.AbilityType.襲撃).list.get(0);
        VillagePlayer fox =
                village.getVillagePlayers().filterBy(vp -> vp.getSkillCodeAsSkill() == CDef.Skill.妖狐).list.stream().findFirst().get();
        ability.setTargetCharaId(fox.getCharaId());
        abilityBhv.update(ability);

        abilities = abilityService.selectAbilities(villageId);
        Votes votes = voteService.selectVotes(villageId);
        DayChangeVillage dayChangeVillage = new DayChangeVillage(villageId, village.getVillageDays().latestDay().getDay(),
                village.getVillagePlayers(), abilities, votes, village.getVillageSettingsAsOne().get());

        // ## Act ##
        logic.attack(dayChangeVillage);

        // ## Assert ##
        village = villageService.selectVillage(villageId, false, false);
        fox = village.getVillagePlayers().findByCharaId(ability.getTargetCharaId());
        assertTrue(fox.isIsDeadFalse());
    }

    @Test
    public void test_attack_襲撃失敗_同棲() {
        // ## Arrange ##
        Integer villageId = assist.createPrologueFullMemberVillage("狼狼狼狂狐賢導狩共共霊霊霊霊棲棲");
        assist.toNextDay(villageId); // 1日目へ
        assist.toNextDay(villageId); // 2日目へ
        assist.addNextDay(villageId); // 3日目を追加

        Village village = villageService.selectVillage(villageId, false, false);
        Abilities abilities = abilityService.selectAbilities(villageId);

        // 襲撃対象を同棲者（移動元）にする
        Ability attack = abilities.filterByDay(2).filterByType(CDef.AbilityType.襲撃).list.get(0);
        Ability cohabit = abilities.filterByDay(2).filterByType(CDef.AbilityType.同棲).list.get(0);
        attack.setTargetCharaId(cohabit.getCharaId());
        abilityBhv.update(attack);

        abilities = abilityService.selectAbilities(villageId);
        Votes votes = voteService.selectVotes(villageId);
        DayChangeVillage dayChangeVillage = new DayChangeVillage(villageId, village.getVillageDays().latestDay().getDay(),
                village.getVillagePlayers(), abilities, votes, village.getVillageSettingsAsOne().get());

        // ## Act ##
        logic.attack(dayChangeVillage);

        // ## Assert ##
        village = villageService.selectVillage(villageId, false, false);
        VillagePlayer cohabiter = village.getVillagePlayers().findByCharaId(cohabit.getCharaId());
        assertTrue(cohabiter.isIsDeadFalse());
    }

    @Test
    public void test_attack_襲撃成功_同棲ありだが相方が処刑死() {
        // ## Arrange ##
        Integer villageId = assist.createPrologueFullMemberVillage("狼狼狼狂狐賢導狩共共霊霊霊霊棲棲");
        assist.toNextDay(villageId); // 1日目へ
        assist.toNextDay(villageId); // 2日目へ
        assist.addNextDay(villageId); // 3日目を追加

        Village village = villageService.selectVillage(villageId, false, false);
        Abilities abilities = abilityService.selectAbilities(villageId);

        // 襲撃対象を同棲者（移動元）にする
        Ability attack = abilities.filterByDay(2).filterByType(CDef.AbilityType.襲撃).list.get(0);
        Ability cohabit = abilities.filterByDay(2).filterByType(CDef.AbilityType.同棲).list.get(0);
        attack.setTargetCharaId(cohabit.getCharaId());
        abilityBhv.update(attack);

        abilities = abilityService.selectAbilities(villageId);
        Votes votes = voteService.selectVotes(villageId);
        DayChangeVillage dayChangeVillage = new DayChangeVillage(villageId, village.getVillageDays().latestDay().getDay(),
                village.getVillagePlayers(), abilities, votes, village.getVillageSettingsAsOne().get());

        // 同棲対象を処刑
        VillagePlayer targetCohabiter = village.getVillagePlayers().findByCharaId(cohabit.getTargetCharaId());
        assist.kill(targetCohabiter, 3, CDef.DeadReason.処刑);
        dayChangeVillage.deadPlayers.add(targetCohabiter, CDef.DeadReason.処刑);

        // ## Act ##
        logic.attack(dayChangeVillage);

        // ## Assert ##
        village = villageService.selectVillage(villageId, false, false);
        targetCohabiter = village.getVillagePlayers().findByCharaId(cohabit.getTargetCharaId());
        assertTrue(targetCohabiter.isIsDeadTrue());
        assertFalse(targetCohabiter.isDeadReasonCode襲撃());
        assertTrue(targetCohabiter.getDeadDay() == 3);
    }

    @Test
    public void test_attack_智狼襲撃成功_同棲により両方死亡() {
        // ## Arrange ##
        Integer villageId = assist.createPrologueFullMemberVillage("智智智狂狐賢導狩共共霊霊霊霊棲棲");
        assist.toNextDay(villageId); // 1日目へ
        assist.toNextDay(villageId); // 2日目へ
        assist.addNextDay(villageId); // 3日目を追加

        Village village = villageService.selectVillage(villageId, false, false);
        Abilities abilities = abilityService.selectAbilities(villageId);

        // 襲撃対象を同棲者（移動先）にする
        Ability attack = abilities.filterByDay(2).filterByType(CDef.AbilityType.襲撃).list.get(0);
        Ability cohabit = abilities.filterByDay(2).filterByType(CDef.AbilityType.同棲).list.get(0);
        attack.setTargetCharaId(cohabit.getTargetCharaId());
        abilityBhv.update(attack);

        abilities = abilityService.selectAbilities(villageId);
        Votes votes = voteService.selectVotes(villageId);
        DayChangeVillage dayChangeVillage = new DayChangeVillage(villageId, village.getVillageDays().latestDay().getDay(),
                village.getVillagePlayers(), abilities, votes, village.getVillageSettingsAsOne().get());

        // ## Act ##
        logic.attack(dayChangeVillage);

        // ## Assert ##
        village = villageService.selectVillage(villageId, false, false);
        VillagePlayer cohabiter = village.getVillagePlayers().findByCharaId(cohabit.getCharaId());
        assertTrue(cohabiter.isIsDeadTrue());
        assertTrue(cohabiter.isDeadReasonCode襲撃());
        assertTrue(cohabiter.getDeadDay() == 3);
        VillagePlayer targetCohabiter = village.getVillagePlayers().findByCharaId(cohabit.getTargetCharaId());
        assertTrue(targetCohabiter.isIsDeadTrue());
        assertTrue(targetCohabiter.isDeadReasonCode襲撃());
        assertTrue(targetCohabiter.getDeadDay() == 3);
        assertTrue(dayChangeVillage.deadPlayers.list.stream().anyMatch(dp -> dp.villagePlayer.getCharaId().equals(cohabiter.getCharaId())));
        assertTrue(dayChangeVillage.deadPlayers.list.stream()
                .anyMatch(dp -> dp.villagePlayer.getCharaId().equals(targetCohabiter.getCharaId())));

    }

    @Test
    public void test_insertDefaultAttack() {
        // ## Arrange ##
        Integer villageId = assist.createPrologueFullMemberVillage();
        assist.toNextDay(villageId); // 1日目へ
        assist.addNextDay(villageId); // 2日目を追加

        // ## Act ##
        Village village = villageService.selectVillage(villageId, false, false);
        logic.insertDefaultAttack(village, 2);

        // ## Assert ##
        Abilities abilities = abilityService.selectAbilities(villageId);
        assertTrue(!abilities.filterByDay(2).filterByType(CDef.AbilityType.襲撃).list.isEmpty());
        Footsteps footsteps = footstepService.selectFootsteps(villageId);
        assertTrue(footsteps.filterByDay(2).list.stream().anyMatch(f -> {
            return village.getVillagePlayers().findByCharaId(f.getCharaId()).getSkillCodeAsSkill().isHasAttackAbility();
        }));
    }

    @Test
    public void test_insertDefaultAttack_人狼が全滅() {
        // ## Arrange ##
        Integer villageId = assist.createPrologueFullMemberVillage();
        assist.toNextDay(villageId); // 1日目へ
        assist.addNextDay(villageId); // 2日目を追加

        // 人狼を処刑
        Village village = villageService.selectVillage(villageId, false, false);
        village.getVillagePlayers().filterBySkill(CDef.Skill.人狼).list.forEach(wolf -> {
            assist.kill(wolf, 2, CDef.DeadReason.処刑);
        });

        // ## Act ##
        village = villageService.selectVillage(villageId, false, false);
        logic.insertDefaultAttack(village, 2);

        // ## Assert ##
        Abilities abilities = abilityService.selectAbilities(villageId);
        assertTrue(abilities.filterByDay(2).filterByType(CDef.AbilityType.襲撃).list.isEmpty());
        Footsteps footsteps = footstepService.selectFootsteps(villageId);
        final Village afterVillage = villageService.selectVillage(villageId, false, false);
        assertTrue(footsteps.filterByDay(2).list.stream().noneMatch(f -> {
            return afterVillage.getVillagePlayers().findByCharaId(f.getCharaId()).getSkillCodeAsSkill().isHasAttackAbility();
        }));
    }

    @Test
    public void test_setAbility() {
        // ## Arrange ##
        Integer villageId = assist.createPrologueFullMemberVillage();
        assist.toNextDay(villageId); // 1日目へ
        assist.toNextDay(villageId); // 2日目へ

        Village village = villageService.selectVillage(villageId, false, false);
        Integer charaId = logic.getAttackableWolfs(village, 2).list.stream().findFirst().get().getCharaId();
        VillagePlayer villagePlayer = village.getVillagePlayers().findByCharaId(charaId);
        Integer targetCharaId = logic.getSelectableTarget(village, 2).list.stream().findFirst().get().getCharaId();
        String footstep = footstepLogic.getFootstepCandidateList(villageId, villagePlayer, 2, charaId, targetCharaId).get(0);

        // ## Act ##
        logic.setAbility(village, villagePlayer, 2, charaId, targetCharaId, footstep);

        // ## Assert ##
        Abilities abilities = abilityService.selectAbilities(villageId);
        Optional<Ability> optAbility = abilities.findTodayAbility(2, CDef.AbilityType.襲撃, charaId);
        assertTrue(optAbility.isPresent());
        assertTrue(optAbility.get().getCharaId().equals(charaId));
        assertTrue(optAbility.get().getTargetCharaId().equals(targetCharaId));
        Footsteps footsteps = footstepService.selectFootsteps(villageId);
        Footstep registeredFootstep = footsteps.filterByDay(2).filterByChara(charaId).list.stream().findFirst().get();
        assertTrue(registeredFootstep.getFootstepRoomNumbers().equals(footstep));
    }

    @Test
    public void test_setAbility_襲撃担当がおかしい() {
        // ## Arrange ##
        Integer villageId = assist.createPrologueFullMemberVillage();
        assist.toNextDay(villageId); // 1日目へ
        assist.toNextDay(villageId); // 2日目へ

        Village village = villageService.selectVillage(villageId, false, false);
        // 襲撃できない人を襲撃担当にする
        VillagePlayers attackableWolfs = logic.getAttackableWolfs(village, 2);
        Integer charaId = village.getVillagePlayers().filterBy(vp -> {
            return attackableWolfs.list.stream().noneMatch(wolf -> wolf.getVillagePlayerId().equals(vp.getVillagePlayerId()));
        }).getRandom().getCharaId();
        VillagePlayer villagePlayer = village.getVillagePlayers().findByCharaId(charaId);
        Integer targetCharaId = logic.getSelectableTarget(village, 2).list.stream().findFirst().get().getCharaId();
        String footstep = "なし";

        // ## Act ##
        logic.setAbility(village, villagePlayer, 2, charaId, targetCharaId, footstep);

        // ## Assert ##
        Abilities abilities = abilityService.selectAbilities(villageId);
        Optional<Ability> optAbility = abilities.findTodayAbility(2, CDef.AbilityType.襲撃, charaId);
        assertTrue(optAbility.isPresent());
        assertFalse(optAbility.get().getCharaId().equals(charaId));
    }

    @Test
    public void test_setAbility_襲撃先がおかしい() {
        // ## Arrange ##
        Integer villageId = assist.createPrologueFullMemberVillage();
        assist.toNextDay(villageId); // 1日目へ
        assist.toNextDay(villageId); // 2日目へ

        Village village = villageService.selectVillage(villageId, false, false);
        Integer charaId = logic.getAttackableWolfs(village, 2).list.stream().findFirst().get().getCharaId();
        VillagePlayer villagePlayer = village.getVillagePlayers().findByCharaId(charaId);
        // 襲撃先に選べない人を襲撃する
        VillagePlayers selectableTarget = logic.getSelectableTarget(village, 2);
        Integer targetCharaId = village.getVillagePlayers().filterBy(vp -> {
            return selectableTarget.list.stream().noneMatch(target -> target.getVillagePlayerId().equals(vp.getVillagePlayerId()));
        }).getRandom().getCharaId();
        String footstep = "なし";

        // ## Act ##
        logic.setAbility(village, villagePlayer, 2, charaId, targetCharaId, footstep);

        // ## Assert ##
        Abilities abilities = abilityService.selectAbilities(villageId);
        Optional<Ability> optAbility = abilities.findTodayAbility(2, CDef.AbilityType.襲撃, charaId);
        assertTrue(optAbility.isPresent());
        assertFalse(optAbility.get().getTargetCharaId().equals(targetCharaId));
    }

    @Test
    public void test_setAbility_足音がおかしい() {
        // ## Arrange ##
        Integer villageId = assist.createPrologueFullMemberVillage();
        assist.toNextDay(villageId); // 1日目へ
        assist.toNextDay(villageId); // 2日目へ

        Village village = villageService.selectVillage(villageId, false, false);
        Integer charaId = logic.getAttackableWolfs(village, 2).list.stream().findFirst().get().getCharaId();
        VillagePlayer villagePlayer = village.getVillagePlayers().findByCharaId(charaId);
        Integer targetCharaId = logic.getSelectableTarget(village, 2).list.stream().findFirst().get().getCharaId();
        String footstep = "01,02,03,04,05,06";

        // ## Act ##
        logic.setAbility(village, villagePlayer, 2, charaId, targetCharaId, footstep);

        // ## Assert ##
        Footsteps footsteps = footstepService.selectFootsteps(villageId);
        Footstep registeredFootstep = footsteps.filterByDay(2).filterByChara(charaId).list.stream().findFirst().get();
        assertFalse(registeredFootstep.getFootstepRoomNumbers().equals(footstep));
    }

    @Test
    public void test_getSelectableTarget_1日目() {
        // ## Arrange ##
        Integer villageId = assist.createPrologueFullMemberVillage();
        assist.toNextDay(villageId); // 1日目へ

        // ## Act ##
        Village village = villageService.selectVillage(villageId, false, false);
        VillagePlayers targets = logic.getSelectableTarget(village, 1);

        // ## Assert ##
        assertTrue(targets.list.size() == 1);
        assertTrue(targets.list.get(0).getCharaId().equals(village.getVillageSettingsAsOne().get().getDummyCharaId()));
    }

    @Test
    public void test_getSelectableTarget_2日目() {
        // ## Arrange ##
        Integer villageId = assist.createPrologueFullMemberVillage();
        assist.toNextDay(villageId); // 1日目へ
        assist.toNextDay(villageId); // 2日目へ

        // ## Act ##
        Village village = villageService.selectVillage(villageId, false, false);
        VillagePlayers targets = logic.getSelectableTarget(village, 2);

        // ## Assert ##
        assertTrue(targets.list.stream().noneMatch(vp -> vp.isIsDeadTrue()));
        assertTrue(targets.list.stream().noneMatch(vp -> vp.isIsSpectatorTrue()));
        assertTrue(targets.list.stream().noneMatch(vp -> vp.getSkillCodeAsSkill().isHasAttackAbility()));
    }

    @Test
    public void test_getSelectableTarget_対象なし() {
        // ## Arrange ##
        Integer villageId = assist.createPrologueFullMemberVillage("村狼狼狼狼狼狼狼");
        assist.toNextDay(villageId); // 1日目へ
        assist.toNextDay(villageId); // 2日目へ

        // ## Act ##
        Village village = villageService.selectVillage(villageId, false, false);
        VillagePlayers targets = logic.getSelectableTarget(village, 2);

        // ## Assert ##
        assertTrue(targets.list.isEmpty());
    }

    @Test
    public void test_getAttackableWolfs_連続襲撃可能() {
        // ## Arrange ##
        Integer villageId = assist.createPrologueFullMemberVillage();
        assist.toNextDay(villageId); // 1日目へ
        assist.toNextDay(villageId); // 2日目へ
        // 連続襲撃可能にする
        VillageSettings settings = villageSettingsBhv.selectEntityWithDeletedCheck(cb -> cb.query().setVillageId_Equal(villageId));
        settings.setIsAvailableSameWolfAttack_True();
        villageSettingsBhv.update(settings);

        // ## Act ##
        Village village = villageService.selectVillage(villageId, false, false);
        VillagePlayers attackers = logic.getAttackableWolfs(village, 2);

        // ## Assert ##
        assertTrue(attackers.list.size() == 3);
    }

    @Test
    public void test_getAttackableWolfs_連続襲撃不可_3狼生存() {
        // ## Arrange ##
        Integer villageId = assist.createPrologueFullMemberVillage();
        assist.toNextDay(villageId); // 1日目へ
        assist.toNextDay(villageId); // 2日目へ

        // ## Act ##
        Village village = villageService.selectVillage(villageId, false, false);
        VillagePlayers attackers = logic.getAttackableWolfs(village, 2);

        // ## Assert ##
        assertTrue(attackers.list.size() == 2);
    }

    @Test
    public void test_getAttackableWolfs_連続襲撃不可_1狼生存() {
        // ## Arrange ##
        Integer villageId = assist.createPrologueFullMemberVillage("村狼村村村");
        assist.toNextDay(villageId); // 1日目へ
        assist.toNextDay(villageId); // 2日目へ

        // ## Act ##
        Village village = villageService.selectVillage(villageId, false, false);
        VillagePlayers attackers = logic.getAttackableWolfs(village, 2);

        // ## Assert ##
        assertTrue(attackers.list.size() == 1);
    }
}
