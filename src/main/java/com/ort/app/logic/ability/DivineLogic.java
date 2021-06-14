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
    // 占い
    public void divine(DayChangeVillage dayChangeVillage) {
        // 生存している占い師
        dayChangeVillage.alivePlayers().filterBy(vp -> vp.getSkillCodeAsSkill().isHasDivineAbility()).list.forEach(seer -> {
            Optional<Ability> optDivine = dayChangeVillage.abilities //
                    .filterYesterday(dayChangeVillage.day) //
                    .filterByType(CDef.AbilityType.占い) //
                    .filterByChara(seer.getCharaId()).list.stream().findFirst();
            if (!optDivine.isPresent()) {
                return; // 能力セットしていない場合は何もしない
            }

            VillagePlayer targetPlayer = dayChangeVillage.vPlayers.findByCharaId(optDivine.get().getTargetCharaId());

            // 結果メッセージ
            insertDivineMessage(dayChangeVillage, seer, targetPlayer);
            // 呪殺
            divineKillIfNeeded(dayChangeVillage, targetPlayer);
            // 逆呪殺
            counterDivineKillIfNeeded(dayChangeVillage, seer, targetPlayer);
        });
    }

    public void insertDefaultDivine(Village village, int newDay) {
        Integer villageId = village.getVillageId();
        // 占う人
        VillagePlayers aliveSeers = village.getVillagePlayers() //
                .filterAlive() //
                .filterBy(vp -> vp.getSkillCodeAsSkill().isHasDivineAbility());
        if (aliveSeers.list.isEmpty()) {
            return;
        }
        aliveSeers.list.forEach(seer -> {
            Integer seerCharaId = seer.getCharaId();
            // 占われる人（生存者の中の誰か）
            Integer targetCharaId = getSelectableTarget(village, seer) //
                    .getRandom()
                    .getCharaId();
            // 能力セット
            abilityService.insertAbility(villageId, newDay, seerCharaId, targetCharaId, null, CDef.AbilityType.占い);
            // 時計回りの足音セット
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
        abilityService.updateAbility(villageId, day, charaId, targetCharaId, CDef.AbilityType.占い);
        footstepService.deleteFootstep(villageId, day, charaId);
        footstepService.insertFootstep(villageId, day, charaId, footstep);
        insertAbilityMessage(village, day, charaId, targetCharaId, footstep, false);
    }

    // 対象として選べる相手
    public VillagePlayers getSelectableTarget(Village village, VillagePlayer seer) {
        return village.getVillagePlayers() //
                .filterAlive() //
                .filterNotSpecatate()
                .filterNot(seer)
                .sortedByRoomNumber();
    }

    // 能力行使履歴
    public List<String> makeSkillHistoryList(Village village, VillagePlayer villagePlayer, int day) {
        // 過去日の能力
        Abilities abilities = abilityService.selectAbilities(village.getVillageId()) //
                .filterPastDay(day) //
                .filterByType(CDef.AbilityType.占い) //
                .filterByChara(villagePlayer.getCharaId())
                .sortedByDay();
        // 足音
        Footsteps footsteps = footstepService.selectFootsteps(village.getVillageId()).filterByChara(villagePlayer.getCharaId());

        return abilities.list.stream().map(ability -> {
            Integer abilityDay = ability.getDay();
            String footstep =
                    footsteps.filterByDay(abilityDay).list.stream().findFirst().map(Footstep::getFootstepRoomNumbers).orElse("なし");
            VillagePlayer target = village.getVillagePlayers().findByCharaId(ability.getTargetCharaId());
            return String.format("%d日目 %s を占う（%s）", abilityDay, target.name(abilityDay), footstep);
        }).collect(Collectors.toList());
    }

    // ===================================================================================
    //                                                                        Assist Logic
    //                                                                        ============
    private void insertDivineMessage(DayChangeVillage dayChangeVillage, VillagePlayer seer, VillagePlayer targetPlayer) {
        CDef.Skill skill = seer.getSkillCodeAsSkill();
        String message = makeDivineMessage(skill, seer, targetPlayer, dayChangeVillage);
        CDef.MessageType messageType =
                skill == CDef.Skill.占い師 || skill == CDef.Skill.占星術師 ? CDef.MessageType.白黒占い結果 : CDef.MessageType.役職占い結果;
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
        if (skill == CDef.Skill.占い師) {
            boolean isTargetWerewolf = targetPlayer.getSkillCodeAsSkill().isHasAttackAbility();
            return String.format("%sは、%sを占った。\n%sは%sのようだ。", seerName, targetCharaName, targetCharaName, isTargetWerewolf ? "人狼" : "人間");
        } else if (skill == CDef.Skill.賢者) {
            return String.format("%sは、%sを占った。\n%sは%sのようだ。", seerName, targetCharaName, targetCharaName,
                    targetPlayer.getSkillCodeAsSkill().alias());
        } else if (skill == CDef.Skill.占星術師) {
            String resultMessage = makeAstrologerDivineResultMessage(dayChangeVillage, targetPlayer);
            return String.format("%sは、%sのあたりを占った。\nこのあたりには、%sいるようだ。", seerName, targetCharaName, resultMessage);
        }
        return null;
    }

    // 占星術結果
    private String makeAstrologerDivineResultMessage(DayChangeVillage dayChangeVillage, VillagePlayer targetPlayer) {
        Village village = villageBhv.selectByPK(dayChangeVillage.villageId).get();
        Integer targetRoomNumber = targetPlayer.getRoomNumber();
        List<Integer> targetRoomNumberList = detectAroundRoomNumber(targetRoomNumber, village.getRoomSizeWidth());
        Map<Skill, List<VillagePlayer>> targetPlayerSkillMap = dayChangeVillage.vPlayers //
                .filterBy(vp -> targetRoomNumberList.contains(vp.getRoomNumber())).list.stream()
                        .collect(Collectors.groupingBy(VillagePlayer::getSkillCodeAsSkill));
        StringJoiner joiner = new StringJoiner("、");
        SkillUtil.SORTED_SKILL_LIST.forEach(skill -> {
            if (!targetPlayerSkillMap.containsKey(skill)) {
                return;
            }
            joiner.add(String.format("%sが%d名", skill.alias(), targetPlayerSkillMap.get(skill).size()));
        });

        return joiner.toString();
    }

    // 対象＋周辺8部屋の部屋番号（存在しない部屋番号を含んでいても良い）
    private List<Integer> detectAroundRoomNumber(Integer targetRoomNumber, Integer roomSizeWidth) {
        if (isLeftSide(targetRoomNumber, roomSizeWidth)) {
            return Arrays.asList(//
                    targetRoomNumber, // 対象の部屋
                    targetRoomNumber - roomSizeWidth, // 上
                    targetRoomNumber - roomSizeWidth + 1, // 右上
                    targetRoomNumber + 1, // 右
                    targetRoomNumber + roomSizeWidth, // 下
                    targetRoomNumber + roomSizeWidth + 1 // 右下
            );
        } else if (isRightSide(targetRoomNumber, roomSizeWidth)) {
            return Arrays.asList(//
                    targetRoomNumber, // 対象の部屋
                    targetRoomNumber - roomSizeWidth - 1, // 左上
                    targetRoomNumber - roomSizeWidth, // 上
                    targetRoomNumber - 1, // 左
                    targetRoomNumber + roomSizeWidth - 1, // 左下
                    targetRoomNumber + roomSizeWidth // 下
            );
        } else {
            return Arrays.asList(//
                    targetRoomNumber, // 対象の部屋
                    targetRoomNumber - roomSizeWidth - 1, // 左上
                    targetRoomNumber - roomSizeWidth, // 上
                    targetRoomNumber - roomSizeWidth + 1, // 右上
                    targetRoomNumber - 1, // 左
                    targetRoomNumber + 1, // 右
                    targetRoomNumber + roomSizeWidth - 1, // 左下
                    targetRoomNumber + roomSizeWidth, // 下
                    targetRoomNumber + roomSizeWidth + 1 // 右下
            );
        }
    }

    private boolean isLeftSide(int targetRoomNumber, int width) {
        return targetRoomNumber % width == 1;
    }

    private boolean isRightSide(int targetRoomNumber, int width) {
        return targetRoomNumber % width == 0;
    }

    // 呪殺
    private void divineKillIfNeeded(DayChangeVillage dayChangeVillage, VillagePlayer targetPlayer) {
        if (dayChangeVillage.isAlive(targetPlayer) // 死亡していない
                && targetPlayer.getSkillCodeAsSkill() == CDef.Skill.妖狐 // 呪殺対象
        ) {
            villageService.dead(targetPlayer, dayChangeVillage.day, CDef.DeadReason.呪殺);
            dayChangeVillage.deadPlayers.add(targetPlayer, CDef.DeadReason.呪殺);
        }
    }

    // 逆呪殺
    private void counterDivineKillIfNeeded(DayChangeVillage dayChangeVillage, VillagePlayer seer, VillagePlayer targetPlayer) {
        if (dayChangeVillage.isAlive(targetPlayer) // 死亡していない
                && targetPlayer.getSkillCodeAsSkill() == CDef.Skill.呪狼 //　逆呪殺対象
        ) {
            villageService.dead(seer, dayChangeVillage.day, CDef.DeadReason.呪殺);
            dayChangeVillage.deadPlayers.add(seer, CDef.DeadReason.呪殺);
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
            return true; // 選べない相手
        }
        // 自分、占い対象、足音の整合性がとれていなかったらNG
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
                new String[] { chara.name(), target.name(), footstep, isDefault ? "（自動設定）" : "" }, Locale.JAPAN);
        messageLogic.insertAbilityMessage(village.getVillageId(), day, message);
    }
}
