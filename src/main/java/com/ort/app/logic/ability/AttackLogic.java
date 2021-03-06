package com.ort.app.logic.ability;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.stream.Collectors;

import org.apache.commons.collections4.CollectionUtils;
import org.dbflute.optional.OptionalEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

import com.ort.app.datasource.AbilityService;
import com.ort.app.datasource.FootstepService;
import com.ort.app.datasource.VillageService;
import com.ort.app.logic.FootstepLogic;
import com.ort.app.logic.MessageLogic;
import com.ort.app.logic.daychange.DayChangeVillage;
import com.ort.app.logic.message.MessageEntity;
import com.ort.dbflute.allcommon.CDef;
import com.ort.dbflute.exbhv.CharaImageBhv;
import com.ort.dbflute.exentity.Abilities;
import com.ort.dbflute.exentity.Ability;
import com.ort.dbflute.exentity.CharaImage;
import com.ort.dbflute.exentity.Footstep;
import com.ort.dbflute.exentity.Footsteps;
import com.ort.dbflute.exentity.Village;
import com.ort.dbflute.exentity.VillagePlayer;
import com.ort.dbflute.exentity.VillagePlayers;

@Component
public class AttackLogic {

    // ===================================================================================
    //                                                                           Attribute
    //                                                                           =========
    @Autowired
    private MessageSource messageSource;
    @Autowired
    private FootstepLogic footstepLogic;
    @Autowired
    private MessageLogic messageLogic;
    @Autowired
    private VillageService villageService;
    @Autowired
    private AbilityService abilityService;
    @Autowired
    private FootstepService footstepService;
    @Autowired
    private CharaImageBhv charaImageBhv;

    // ===================================================================================
    //                                                                             Execute
    //                                                                             =======
    // ??????
    public void attack(DayChangeVillage dayChangeVillage) {
        List<VillagePlayer> livingWolfList =
                dayChangeVillage.alivePlayers().filterBy(vp -> vp.getSkillCodeAsSkill().isHasAttackAbility()).list;
        if (CollectionUtils.isEmpty(livingWolfList)) {
            return; // ??????????????????????????????????????????
        }

        Optional<Ability> optAttack =
                dayChangeVillage.abilities.filterYesterday(dayChangeVillage.day).filterByType(CDef.AbilityType.??????).list.stream()
                        .findFirst();
        if (!optAttack.isPresent()) {
            return; // ??????????????????????????????????????????????????????
        }

        Ability attack = optAttack.get();
        VillagePlayer targetPlayer = dayChangeVillage.vPlayers.findByCharaId(attack.getTargetCharaId());

        // ?????????????????????
        insertAttackMessageIfNeeded(dayChangeVillage, attack, targetPlayer);

        // ???????????????????????????
        if (!isAttackSuccess(dayChangeVillage, targetPlayer)) {
            return;
        }

        // ??????????????????????????????????????????????????????????????????
        if (isCohabitting(dayChangeVillage, targetPlayer)) {
            VillagePlayer lover = targetPlayer.getTargetCohabitor();
            villageService.dead(lover, dayChangeVillage.day, CDef.DeadReason.??????);
            dayChangeVillage.deadPlayers.add(lover, CDef.DeadReason.??????);
        }

        // ????????????
        villageService.dead(targetPlayer, dayChangeVillage.day, CDef.DeadReason.??????);
        dayChangeVillage.deadPlayers.add(targetPlayer, CDef.DeadReason.??????);

        // ??????????????????????????????????????????????????????????????????
        if (isAttackerWiseWolf(dayChangeVillage, attack)) {
            dayChangeVillage.deadPlayers.filterAttacked().getList().forEach(attackedPlayer -> {
                String message = String.format("%s???%s?????????????????????", targetPlayer.name(), attackedPlayer.getSkillCodeAsSkill().alias());
                messageLogic.saveIgnoreError(MessageEntity.systemBuilder(dayChangeVillage.villageId, dayChangeVillage.day) //
                        .messageType(CDef.MessageType.????????????)
                        .content(message)
                        .build());
            });
        }
    }

    // ????????????????????????????????????
    public void insertDefaultAttack(Village village, int newDay) {
        Integer villageId = village.getVillageId();
        // ???????????????
        Integer attackedCharaId = getSelectableTarget(village, newDay).getRandom().getCharaId();
        // ?????????????????????????????????????????????2???????????????????????????????????????????????????
        VillagePlayers attackableWolfs = getAttackableWolfs(village, newDay);
        if (attackableWolfs.list.isEmpty()) {
            return; // ??????
        }
        Integer attackCharaId = attackableWolfs.getRandom().getCharaId();
        // ???????????????
        abilityService.insertAbility(villageId, newDay, attackCharaId, attackedCharaId, null, CDef.AbilityType.??????);
        // ??????????????????????????????
        String footStep = footstepLogic.makeClockwiseFootStep(village, attackCharaId, attackedCharaId, village.getVillagePlayerList());
        footstepLogic.insertFootStep(villageId, newDay, attackCharaId, footStep);
        insertAbilityMessage(village, newDay, attackCharaId, attackedCharaId, footStep, true);
    }

    // ???????????????
    public void setAbility( //
            Village village, //
            VillagePlayer villagePlayer, //
            int day, //
            Integer charaId, //
            Integer targetCharaId, //
            String footstep//
    ) {
        if (isInvalidWolfAbility(village, villagePlayer, day, charaId, targetCharaId, footstep)) {
            return;
        }
        Integer villageId = village.getVillageId();
        abilityService.updateAbility(villageId, day, charaId, targetCharaId, CDef.AbilityType.??????);
        VillagePlayers wolfPlayers = village.getVillagePlayers().filterBy(vp -> vp.getSkillCodeAsSkill().isHasAttackAbility());
        footstepService.deleteWolfFootstep(villageId, day, charaId, footstep, wolfPlayers);
        footstepService.insertFootstep(villageId, day, charaId, footstep);
        insertAbilityMessage(village, day, charaId, targetCharaId, footstep, false);
    }

    // ???????????????
    public VillagePlayers getSelectableTarget(Village village, int day) {
        if (day == 1) {
            // ???????????????
            Integer dummyCharaId = village.getVillageSettingsAsOne().get().getDummyCharaId();
            return new VillagePlayers(Arrays.asList(village.getVillagePlayers().findByCharaId(dummyCharaId)));
        } else {
            // ?????????????????????????????????
            return village.getVillagePlayers() //
                    .filterAlive() //
                    .filterNotSpecatate()
                    .filterBy(vp -> !vp.getSkillCodeAsSkill().isHasAttackAbility())
                    .sortedByRoomNumber();
        }
    }

    public VillagePlayers getAttackableWolfs(Village village, int day) {
        // ??????????????????????????????
        VillagePlayers aliveAttackers = village.getVillagePlayers() //
                .filterAlive() //
                .filterNotSpecatate() //
                .filterBy(vp -> vp.getSkillCodeAsSkill().isHasAttackAbility())
                .sortedByRoomNumber();
        if (village.getVillageSettingsAsOne().get().isIsAvailableSameWolfAttackTrue() || aliveAttackers.list.size() <= 1) {
            return aliveAttackers; // ??????????????????
        }
        // ??????????????????
        // ??????????????????????????????????????????
        Integer yesterdayAttackerId = selectYesterdayAttackerId(village.getVillageId(), day);
        return aliveAttackers.filterBy(attacker -> !attacker.getCharaId().equals(yesterdayAttackerId));
    }

    public List<String> makeSkillHistoryList(Village village, int day) {
        // ???????????????????????????
        Abilities abilities = abilityService.selectAbilities(village.getVillageId()) //
                .filterPastDay(day) //
                .filterByType(CDef.AbilityType.??????)
                .sortedByDay();
        List<Integer> wolfCharaIdList = village.getVillagePlayers()
                .filterNotSpecatate()
                .filterBy(vp -> vp.getSkillCodeAsSkill().isHasAttackAbility())
                .map(vp -> vp.getCharaId());
        // ???????????????
        Footsteps footsteps = footstepService.selectFootsteps(village.getVillageId()).filterInCharaIdList(wolfCharaIdList);

        return abilities.list.stream().map(ability -> {
            Integer abilityDay = ability.getDay();
            String footstep =
                    footsteps.filterByDay(abilityDay).list.stream().findFirst().map(Footstep::getFootstepRoomNumbers).orElse("??????");
            VillagePlayer myself = village.getVillagePlayers().findByCharaId(ability.getCharaId());
            VillagePlayer target = village.getVillagePlayers().findByCharaId(ability.getTargetCharaId());
            return String.format("%d?????? %s ??? %s ??????????????????%s???", abilityDay, myself.name(abilityDay), target.name(abilityDay), footstep);
        }).collect(Collectors.toList());
    }

    // ===================================================================================
    //                                                                        Assist Logic
    //                                                                        ============
    private void insertAttackMessageIfNeeded(DayChangeVillage dayChangeVillage, Ability attack, VillagePlayer targetPlayer) {
        if (dayChangeVillage.day == 2) {
            return;
        }
        List<VillagePlayer> livingWolfList =
                dayChangeVillage.alivePlayers().filterBy(vp -> vp.getSkillCodeAsSkill().isHasAttackAbility()).list;
        Collections.shuffle(livingWolfList);
        String attackMessage = String.format("%s?????????????????????????????????", targetPlayer.getChara().get().getCharaName());
        VillagePlayer attackerPlayer = livingWolfList.get(0);
        boolean hasWerewolfFace = hasWerewolfFace(attackerPlayer);
        messageLogic.saveIgnoreError(new MessageEntity.Builder(dayChangeVillage.villageId, dayChangeVillage.day) //
                .messageType(CDef.MessageType.???????????????)
                .content(attackMessage)
                .villagePlayer(attackerPlayer)
                .faceType(hasWerewolfFace ? CDef.FaceType.?????? : CDef.FaceType.??????)
                .isConvertDisable(true)
                .build());
    }

    private boolean hasWerewolfFace(VillagePlayer attackerPlayer) {
        OptionalEntity<CharaImage> optAttackFace = charaImageBhv.selectEntity(cb -> {
            cb.query().setCharaId_Equal(attackerPlayer.getCharaId());
            cb.query().setFaceTypeCode_Equal_??????();
        });
        return optAttackFace.isPresent();
    }

    private boolean isAttackSuccess(DayChangeVillage dayChangeVillage, VillagePlayer targetPlayer) {
        if (!dayChangeVillage.isAlive(targetPlayer)) {
            return false; // ???????????????????????????
        }
        if (isGuarded(dayChangeVillage, targetPlayer)) {
            return false; // ?????????????????????
        }
        if (targetPlayer.getSkillCodeAsSkill().isNoDeadByAttack()) {
            return false; // ????????????????????????????????????
        }
        if (isAbsence(dayChangeVillage, targetPlayer)) {
            return false; // ????????????????????????????????????
        }
        return true;
    }

    private boolean isGuarded(DayChangeVillage dayChangeVillage, VillagePlayer targetPlayer) {
        return dayChangeVillage.abilities //
                .filterYesterday(dayChangeVillage.day) //
                .filterByType(CDef.AbilityType.??????) //
                .filterMylsefNotIn(dayChangeVillage.deadPlayers.getList()) //
                        .list.stream().anyMatch(a -> a.getTargetCharaId().equals(targetPlayer.getCharaId()));
    }

    private boolean isAbsence(DayChangeVillage dayChangeVillage, VillagePlayer targetPlayer) {
        return !dayChangeVillage.abilities //
                .filterYesterday(dayChangeVillage.day) //
                .filterByType(CDef.AbilityType.??????) //
                .filterMylsefNotIn(dayChangeVillage.deadPlayers.getList()) //
                .filterTargetNotIn(dayChangeVillage.deadPlayers.getList()) //
                .filterByChara(targetPlayer.getCharaId()).list.isEmpty();
    }

    private boolean isCohabitting(DayChangeVillage dayChangeVillage, VillagePlayer targetPlayer) {
        return !dayChangeVillage.abilities //
                .filterYesterday(dayChangeVillage.day) //
                .filterByType(CDef.AbilityType.??????) //
                .filterMylsefNotIn(dayChangeVillage.deadPlayers.getList()) //
                .filterTargetNotIn(dayChangeVillage.deadPlayers.getList()) //
                .filterByTargetChara(targetPlayer.getCharaId()).list.isEmpty();
    }

    private boolean isAttackerWiseWolf(DayChangeVillage dayChangeVillage, Ability attack) {
        VillagePlayer attacker = dayChangeVillage.vPlayers.findByCharaId(attack.getCharaId());
        return attacker.getSkillCodeAsSkill() == CDef.Skill.?????? && dayChangeVillage.isAlive(attacker);
    }

    private boolean isInvalidWolfAbility(//
            Village village, //
            VillagePlayer villagePlayer, //
            int day, //
            Integer charaId, //
            Integer targetCharaId, //
            String footstep//
    ) {
        if (charaId == null || targetCharaId == null || footstep == null) {
            return true;
        }
        VillagePlayers attackableWolfs = getAttackableWolfs(village, day);
        if (attackableWolfs.list.stream().noneMatch(vp -> vp.getCharaId().equals(charaId))) {
            return true; // ??????????????????????????????????????????
        }
        if (getSelectableTarget(village, day).list.stream().noneMatch(vp -> vp.getCharaId().equals(targetCharaId))) {
            return true; // ??????????????????????????????????????????
        }
        // ???????????????????????????????????????????????????????????????????????????NG
        List<String> footstepCandidateList =
                footstepLogic.getFootstepCandidateList(village.getVillageId(), villagePlayer, day, charaId, targetCharaId);
        if (!footstepCandidateList.contains(footstep)) {
            return true;
        }

        return false;
    }

    private void insertAbilityMessage( //
            Village village, //
            int day, //
            Integer charaId, //
            Integer targetCharaId, //
            String footstep, //
            boolean isDefault //
    ) {
        VillagePlayer chara = village.getVillagePlayers().findByCharaId(charaId);
        VillagePlayer target = village.getVillagePlayers().findByCharaId(targetCharaId);
        String message = messageSource.getMessage("ability.werewolf.message",
                new String[] { chara.name(), target.name(), footstep, isDefault ? "??????????????????" : "" }, Locale.JAPAN);
        messageLogic.insertAbilityMessage(village.getVillageId(), day, message);
    }

    public Integer selectYesterdayAttackerId(Integer villageId, int day) {
        return abilityService.selectAbilities(villageId) //
                .filterYesterday(day) //
                .filterByType(CDef.AbilityType.??????).list.stream() //
                        .findFirst()
                        .map(a -> a.getCharaId())
                        .orElse(null);
    }
}
