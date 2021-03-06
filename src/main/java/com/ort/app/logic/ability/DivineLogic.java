package com.ort.app.logic.ability;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;
import java.util.StringJoiner;
import java.util.stream.Collectors;

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
import com.ort.app.util.SkillUtil;
import com.ort.dbflute.allcommon.CDef;
import com.ort.dbflute.allcommon.CDef.Skill;
import com.ort.dbflute.exbhv.VillageBhv;
import com.ort.dbflute.exentity.Abilities;
import com.ort.dbflute.exentity.Ability;
import com.ort.dbflute.exentity.Footstep;
import com.ort.dbflute.exentity.Footsteps;
import com.ort.dbflute.exentity.Village;
import com.ort.dbflute.exentity.VillagePlayer;
import com.ort.dbflute.exentity.VillagePlayers;

@Component
public class DivineLogic {

    // ===================================================================================
    //                                                                           Attribute
    //                                                                           =========
    @Autowired
    private VillageBhv villageBhv;
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

    // ===================================================================================
    //                                                                             Execute
    //                                                                             =======
    // ??????
    public void divine(DayChangeVillage dayChangeVillage) {
        // ???????????????????????????
        dayChangeVillage.alivePlayers().filterBy(vp -> vp.getSkillCodeAsSkill().isHasDivineAbility()).list.forEach(seer -> {
            Optional<Ability> optDivine = dayChangeVillage.abilities //
                    .filterYesterday(dayChangeVillage.day) //
                    .filterByType(CDef.AbilityType.??????) //
                    .filterByChara(seer.getCharaId()).list.stream().findFirst();
            if (!optDivine.isPresent()) {
                return; // ??????????????????????????????????????????????????????
            }

            VillagePlayer targetPlayer = dayChangeVillage.vPlayers.findByCharaId(optDivine.get().getTargetCharaId());

            // ?????????????????????
            insertDivineMessage(dayChangeVillage, seer, targetPlayer);
            // ??????
            divineKillIfNeeded(dayChangeVillage, targetPlayer);
            // ?????????
            counterDivineKillIfNeeded(dayChangeVillage, seer, targetPlayer);
        });
    }

    public void insertDefaultDivine(Village village, int newDay) {
        Integer villageId = village.getVillageId();
        // ?????????
        VillagePlayers aliveSeers = village.getVillagePlayers() //
                .filterAlive() //
                .filterBy(vp -> vp.getSkillCodeAsSkill().isHasDivineAbility());
        if (aliveSeers.list.isEmpty()) {
            return;
        }
        aliveSeers.list.forEach(seer -> {
            Integer seerCharaId = seer.getCharaId();
            // ?????????????????????????????????????????????
            Integer targetCharaId = getSelectableTarget(village, seer) //
                    .getRandom()
                    .getCharaId();
            // ???????????????
            abilityService.insertAbility(villageId, newDay, seerCharaId, targetCharaId, null, CDef.AbilityType.??????);
            // ??????????????????????????????
            String footStep = footstepLogic.makeClockwiseFootStep(village, seerCharaId, targetCharaId, village.getVillagePlayerList());
            footstepLogic.insertFootStep(villageId, newDay, seerCharaId, footStep);
            insertAbilityMessage(village, newDay, seerCharaId, targetCharaId, footStep, true);
        });
    }

    public void setAbility(//
            Village village, //
            VillagePlayer villagePlayer, //
            int day, //
            Integer targetCharaId, //
            String footstep//
    ) {
        if (isInvalidDivineAbility(village, villagePlayer, day, targetCharaId, footstep)) {
            return;
        }
        Integer villageId = village.getVillageId();
        Integer charaId = villagePlayer.getCharaId();
        abilityService.updateAbility(villageId, day, charaId, targetCharaId, CDef.AbilityType.??????);
        footstepService.deleteFootstep(villageId, day, charaId);
        footstepService.insertFootstep(villageId, day, charaId, footstep);
        insertAbilityMessage(village, day, charaId, targetCharaId, footstep, false);
    }

    // ??????????????????????????????
    public VillagePlayers getSelectableTarget(Village village, VillagePlayer seer) {
        return village.getVillagePlayers() //
                .filterAlive() //
                .filterNotSpecatate()
                .filterNot(seer)
                .sortedByRoomNumber();
    }

    // ??????????????????
    public List<String> makeSkillHistoryList(Village village, VillagePlayer villagePlayer, int day) {
        // ??????????????????
        Abilities abilities = abilityService.selectAbilities(village.getVillageId()) //
                .filterPastDay(day) //
                .filterByType(CDef.AbilityType.??????) //
                .filterByChara(villagePlayer.getCharaId())
                .sortedByDay();
        // ??????
        Footsteps footsteps = footstepService.selectFootsteps(village.getVillageId()).filterByChara(villagePlayer.getCharaId());

        return abilities.list.stream().map(ability -> {
            Integer abilityDay = ability.getDay();
            String footstep =
                    footsteps.filterByDay(abilityDay).list.stream().findFirst().map(Footstep::getFootstepRoomNumbers).orElse("??????");
            VillagePlayer target = village.getVillagePlayers().findByCharaId(ability.getTargetCharaId());
            return String.format("%d?????? %s ????????????%s???", abilityDay, target.name(abilityDay), footstep);
        }).collect(Collectors.toList());
    }

    // ===================================================================================
    //                                                                        Assist Logic
    //                                                                        ============
    private void insertDivineMessage(DayChangeVillage dayChangeVillage, VillagePlayer seer, VillagePlayer targetPlayer) {
        CDef.Skill skill = seer.getSkillCodeAsSkill();
        String message = makeDivineMessage(skill, seer, targetPlayer, dayChangeVillage);
        CDef.MessageType messageType =
                skill == CDef.Skill.????????? || skill == CDef.Skill.???????????? ? CDef.MessageType.?????????????????? : CDef.MessageType.??????????????????;
        messageLogic.saveIgnoreError(MessageEntity.systemBuilder(dayChangeVillage.villageId, dayChangeVillage.day) //
                .messageType(messageType)
                .content(message)
                .villagePlayer(seer)
                .build());
    }

    private String makeDivineMessage(CDef.Skill skill, VillagePlayer seerPlayer, VillagePlayer targetPlayer,
            DayChangeVillage dayChangeVillage) {
        String targetCharaName = targetPlayer.name();
        String seerName = seerPlayer.name();
        if (skill == CDef.Skill.?????????) {
            boolean isTargetWerewolf = targetPlayer.getSkillCodeAsSkill().isHasAttackAbility();
            return String.format("%s??????%s???????????????\n%s???%s???????????????", seerName, targetCharaName, targetCharaName, isTargetWerewolf ? "??????" : "??????");
        } else if (skill == CDef.Skill.??????) {
            return String.format("%s??????%s???????????????\n%s???%s???????????????", seerName, targetCharaName, targetCharaName,
                    targetPlayer.getSkillCodeAsSkill().alias());
        } else if (skill == CDef.Skill.????????????) {
            String resultMessage = makeAstrologerDivineResultMessage(dayChangeVillage, targetPlayer);
            return String.format("%s??????%s???????????????????????????\n????????????????????????%s??????????????????", seerName, targetCharaName, resultMessage);
        }
        return null;
    }

    // ???????????????
    private String makeAstrologerDivineResultMessage(DayChangeVillage dayChangeVillage, VillagePlayer targetPlayer) {
        Village village = villageBhv.selectByPK(dayChangeVillage.villageId).get();
        Integer targetRoomNumber = targetPlayer.getRoomNumber();
        List<Integer> targetRoomNumberList = detectAroundRoomNumber(targetRoomNumber, village.getRoomSizeWidth());
        Map<Skill, List<VillagePlayer>> targetPlayerSkillMap = dayChangeVillage.vPlayers //
                .filterBy(vp -> targetRoomNumberList.contains(vp.getRoomNumber())).list.stream()
                        .collect(Collectors.groupingBy(VillagePlayer::getSkillCodeAsSkill));
        StringJoiner joiner = new StringJoiner("???");
        SkillUtil.SORTED_SKILL_LIST.forEach(skill -> {
            if (!targetPlayerSkillMap.containsKey(skill)) {
                return;
            }
            joiner.add(String.format("%s???%d???", skill.alias(), targetPlayerSkillMap.get(skill).size()));
        });

        return joiner.toString();
    }

    // ???????????????8?????????????????????????????????????????????????????????????????????????????????
    private List<Integer> detectAroundRoomNumber(Integer targetRoomNumber, Integer roomSizeWidth) {
        if (isLeftSide(targetRoomNumber, roomSizeWidth)) {
            return Arrays.asList(//
                    targetRoomNumber, // ???????????????
                    targetRoomNumber - roomSizeWidth, // ???
                    targetRoomNumber - roomSizeWidth + 1, // ??????
                    targetRoomNumber + 1, // ???
                    targetRoomNumber + roomSizeWidth, // ???
                    targetRoomNumber + roomSizeWidth + 1 // ??????
            );
        } else if (isRightSide(targetRoomNumber, roomSizeWidth)) {
            return Arrays.asList(//
                    targetRoomNumber, // ???????????????
                    targetRoomNumber - roomSizeWidth - 1, // ??????
                    targetRoomNumber - roomSizeWidth, // ???
                    targetRoomNumber - 1, // ???
                    targetRoomNumber + roomSizeWidth - 1, // ??????
                    targetRoomNumber + roomSizeWidth // ???
            );
        } else {
            return Arrays.asList(//
                    targetRoomNumber, // ???????????????
                    targetRoomNumber - roomSizeWidth - 1, // ??????
                    targetRoomNumber - roomSizeWidth, // ???
                    targetRoomNumber - roomSizeWidth + 1, // ??????
                    targetRoomNumber - 1, // ???
                    targetRoomNumber + 1, // ???
                    targetRoomNumber + roomSizeWidth - 1, // ??????
                    targetRoomNumber + roomSizeWidth, // ???
                    targetRoomNumber + roomSizeWidth + 1 // ??????
            );
        }
    }

    private boolean isLeftSide(int targetRoomNumber, int width) {
        return targetRoomNumber % width == 1;
    }

    private boolean isRightSide(int targetRoomNumber, int width) {
        return targetRoomNumber % width == 0;
    }

    // ??????
    private void divineKillIfNeeded(DayChangeVillage dayChangeVillage, VillagePlayer targetPlayer) {
        if (dayChangeVillage.isAlive(targetPlayer) // ?????????????????????
                && targetPlayer.getSkillCodeAsSkill() == CDef.Skill.?????? // ????????????
        ) {
            villageService.dead(targetPlayer, dayChangeVillage.day, CDef.DeadReason.??????);
            dayChangeVillage.deadPlayers.add(targetPlayer, CDef.DeadReason.??????);
        }
    }

    // ?????????
    private void counterDivineKillIfNeeded(DayChangeVillage dayChangeVillage, VillagePlayer seer, VillagePlayer targetPlayer) {
        if (dayChangeVillage.isAlive(targetPlayer) // ?????????????????????
                && targetPlayer.getSkillCodeAsSkill() == CDef.Skill.?????? //??????????????????
        ) {
            villageService.dead(seer, dayChangeVillage.day, CDef.DeadReason.??????);
            dayChangeVillage.deadPlayers.add(seer, CDef.DeadReason.??????);
        }
    }

    private boolean isInvalidDivineAbility(Village village, VillagePlayer villagePlayer, int day, Integer targetCharaId, String footstep) {
        if (targetCharaId == null || footstep == null) {
            return true;
        }
        Integer charaId = villagePlayer.getCharaId();
        if (villagePlayer.isIsDeadTrue()) {
            return true;
        }
        if (getSelectableTarget(village, villagePlayer).list.stream().noneMatch(vp -> vp.getCharaId().equals(targetCharaId))) {
            return true; // ??????????????????
        }
        // ????????????????????????????????????????????????????????????????????????NG
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
        String message = messageSource.getMessage("ability.seer.message",
                new String[] { chara.name(), target.name(), footstep, isDefault ? "??????????????????" : "" }, Locale.JAPAN);
        messageLogic.insertAbilityMessage(village.getVillageId(), day, message);
    }
}
