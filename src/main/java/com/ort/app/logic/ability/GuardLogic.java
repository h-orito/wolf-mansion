package com.ort.app.logic.ability;

import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.stream.Collectors;

import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

import com.ort.app.datasource.AbilityService;
import com.ort.app.datasource.FootstepService;
import com.ort.app.logic.FootstepLogic;
import com.ort.app.logic.MessageLogic;
import com.ort.app.logic.daychange.DayChangeLogicHelper;
import com.ort.app.logic.daychange.DayChangeVillage;
import com.ort.dbflute.allcommon.CDef;
import com.ort.dbflute.exbhv.AbilityBhv;
import com.ort.dbflute.exentity.Abilities;
import com.ort.dbflute.exentity.Ability;
import com.ort.dbflute.exentity.Footstep;
import com.ort.dbflute.exentity.Footsteps;
import com.ort.dbflute.exentity.Village;
import com.ort.dbflute.exentity.VillagePlayer;
import com.ort.dbflute.exentity.VillagePlayers;

@Component
public class GuardLogic {

    // ===================================================================================
    //                                                                           Attribute
    //                                                                           =========
    @Autowired
    private DayChangeLogicHelper helper;
    @Autowired
    private MessageSource messageSource;
    @Autowired
    private MessageLogic messageLogic;
    @Autowired
    private FootstepLogic footstepLogic;
    @Autowired
    private AbilityBhv abilityBhv;
    @Autowired
    private AbilityService abilityService;
    @Autowired
    private FootstepService footstepService;

    // ===================================================================================
    //                                                                             Execute
    //                                                                             =======
    // 護衛
    public void guard(DayChangeVillage dayChangeVillage) {
        int day = dayChangeVillage.day;
        if (day == 2) {
            return;
        }

        List<VillagePlayer> livingHunterList = dayChangeVillage //
                .alivePlayers() // 死亡していない
                .filterBySkill(CDef.Skill.狩人).list; // 狩人
        if (CollectionUtils.isEmpty(livingHunterList)) {
            return; // 狩人が既に死亡している場合は何もしない
        }

        livingHunterList.forEach(hunter -> {
            Optional<Ability> optGuard = dayChangeVillage.abilities //
                    .filterYesterday(day) //
                    .filterByType(CDef.AbilityType.護衛) //
                    .filterByChara(hunter.getCharaId()).list.stream().findFirst();
            if (!optGuard.isPresent()) {
                return; // 能力セットしていない場合は何もしない
            }
            VillagePlayer targetPlayer = dayChangeVillage.vPlayers.findByCharaId(optGuard.get().getTargetCharaId());
            String message = String.format("%sは、%sを護衛している。", hunter.name(), targetPlayer.name());
            helper.insertMessage(dayChangeVillage, CDef.MessageType.非公開システムメッセージ, message);
            dayChangeVillage.guardedPlayers.add(targetPlayer);
        });
    }

    // 日付変更時のデフォルトセット
    public void insertDefaultGuard(Village village, int newDay) {
        Integer villageId = village.getVillageId();
        // 生存している狩人
        VillagePlayers aliveHunters = village.getVillagePlayers() //
                .filterAlive() //
                .filterBy(vp -> vp.getSkillCodeAsSkill() == CDef.Skill.狩人);
        if (aliveHunters.list.isEmpty()) {
            return;
        }
        aliveHunters.list.forEach(hunter -> {
            Integer hunterCharaId = hunter.getCharaId();
            // 護衛される人(生存者の中の誰か）
            Integer targetCharaId = getSelectableTarget(village, newDay, hunter) //
                    .getRandom()
                    .getCharaId();
            abilityService.insertAbility(villageId, newDay, hunterCharaId, targetCharaId, null, CDef.AbilityType.護衛);
            // 時計回りの足音セット
            String footStep = footstepLogic.makeClockwiseFootStep(village, hunterCharaId, targetCharaId, village.getVillagePlayerList());
            footstepLogic.insertFootStep(villageId, newDay, hunterCharaId, footStep);
            insertAbilityMessage(village, newDay, hunterCharaId, targetCharaId, footStep, true);
        });
    }

    // 能力セット
    public void setAbility(//
            Village village, //
            VillagePlayer villagePlayer, //
            int day, //
            Integer targetCharaId, //
            String footstep//
    ) {
        if (isInvalidHunterAbility(village, villagePlayer, day, targetCharaId, footstep)) {
            return;
        }
        Integer villageId = village.getVillageId();
        Integer charaId = villagePlayer.getCharaId();
        abilityService.updateAbility(villageId, day, charaId, targetCharaId, CDef.AbilityType.護衛);
        footstepService.deleteFootstep(villageId, day, charaId);
        footstepService.insertFootstep(villageId, day, charaId, footstep);
        insertAbilityMessage(village, day, charaId, targetCharaId, footstep, false);
    }

    public VillagePlayers getSelectableTarget(Village village, int day, VillagePlayer hunter) {
        if (day <= 1) {
            return null;
        }
        // 自分以外の生存している人
        VillagePlayers targets = village.getVillagePlayers() //
                .filterAlive() //
                .filterNotSpecatate()
                .filterNot(hunter)
                .sortedByRoomNumber();
        // 連続護衛
        boolean isAvailableGuardSameTargetTrue = village.getVillageSettingsAsOne().get().isIsAvailableGuardSameTargetTrue();
        if (isAvailableGuardSameTargetTrue) {
            return targets;
        } else {
            // 昨日と同じ対象は護衛できない
            Integer yesterdayGuardTargetCharaId = selectYesterdayGuardTarget(village.getVillageId(), day, hunter);
            return targets.filterBy(vp -> !vp.getCharaId().equals(yesterdayGuardTargetCharaId));
        }
    }

    // 能力行使履歴
    public List<String> makeSkillHistoryList(Village village, VillagePlayer villagePlayer, int day) {
        // 過去日の能力
        Abilities abilities = abilityService.selectAbilities(village.getVillageId()) //
                .filterPastDay(day) //
                .filterByType(CDef.AbilityType.護衛) //
                .filterByChara(villagePlayer.getCharaId())
                .sortedByDay();
        // 足音
        Footsteps footsteps = footstepService.selectFootsteps(village.getVillageId()).filterByChara(villagePlayer.getCharaId());

        return abilities.list.stream().map(ability -> {
            Integer abilityDay = ability.getDay();
            String footstep =
                    footsteps.filterByDay(abilityDay).list.stream().findFirst().map(Footstep::getFootstepRoomNumbers).orElse("なし");
            VillagePlayer target = village.getVillagePlayers().findByCharaId(ability.getTargetCharaId());
            return String.format("%d日目 %s を護衛する（%s）", abilityDay, target.name(abilityDay), footstep);
        }).collect(Collectors.toList());
    }

    // ===================================================================================
    //                                                                        Assist Logic
    //                                                                        ============
    private boolean isInvalidHunterAbility(Village village, VillagePlayer villagePlayer, int day, Integer targetCharaId, String footstep) {
        if (villagePlayer.isIsDeadTrue()) {
            return true;
        }
        if (getSelectableTarget(village, day, villagePlayer).list.stream().noneMatch(vp -> vp.getCharaId().equals(targetCharaId))) {
            return true;
        }
        if (day == 1) {
            return true;
        }
        // 自分、護衛対象、足音の整合性がとれていなかったらNG
        List<String> footstepCandidateList = footstepLogic.getFootstepCandidateList(village.getVillageId(), villagePlayer, day,
                villagePlayer.getCharaId(), targetCharaId);
        if (!footstepCandidateList.contains(footstep)) {
            return true;
        }
        return false;
    }

    private Integer selectYesterdayGuardTarget(Integer villageId, int day, VillagePlayer hunter) {
        return abilityBhv.selectEntity(cb -> {
            cb.query().setVillageId_Equal(villageId);
            cb.query().setDay_Equal(day - 1);
            cb.query().setAbilityTypeCode_Equal_護衛();
            cb.query().setCharaId_Equal(hunter.getCharaId());
        }).map(ab -> ab.getTargetCharaId()).orElse(null);
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
        String message = messageSource.getMessage("ability.hunter.message",
                new String[] { chara.name(), target.name(), footstep, isDefault ? "（自動設定）" : "" }, Locale.JAPAN);
        messageLogic.insertAbilityMessage(village.getVillageId(), day, message);
    }
}
