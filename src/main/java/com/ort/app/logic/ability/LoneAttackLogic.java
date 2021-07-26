package com.ort.app.logic.ability;

import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

import com.ort.app.datasource.AbilityService;
import com.ort.app.datasource.FootstepService;
import com.ort.app.datasource.VillageService;
import com.ort.app.logic.FootstepLogic;
import com.ort.app.logic.MessageLogic;
import com.ort.app.logic.ability.helper.AttackLogicHelper;
import com.ort.app.logic.daychange.DayChangeVillage;
import com.ort.app.logic.message.MessageEntity;
import com.ort.dbflute.allcommon.CDef;
import com.ort.dbflute.exentity.Abilities;
import com.ort.dbflute.exentity.Ability;
import com.ort.dbflute.exentity.Footstep;
import com.ort.dbflute.exentity.Footsteps;
import com.ort.dbflute.exentity.Village;
import com.ort.dbflute.exentity.VillagePlayer;
import com.ort.dbflute.exentity.VillagePlayers;

@Component
public class LoneAttackLogic {

    // ===================================================================================
    //                                                                           Attribute
    //                                                                           =========
    @Autowired
    private MessageSource messageSource;
    @Autowired
    private AttackLogicHelper helper;
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
    // 単独襲撃
    public void loneAttack(DayChangeVillage dayChangeVillage) {
        dayChangeVillage.alivePlayers().filterBy(vp -> vp.getSkillCodeAsSkill() == CDef.Skill.一匹狼).list.forEach(loneWolf -> {
            Optional<Ability> optLoneAttack = dayChangeVillage.abilities //
                    .filterYesterday(dayChangeVillage.day) //
                    .filterByType(CDef.AbilityType.単独襲撃) //
                    .filterByChara(loneWolf.getCharaId()).list.stream().findFirst();
            if (!optLoneAttack.isPresent()) {
                return; // 能力セットしていない場合は何もしない
            }

            Ability loneAttack = optLoneAttack.get();
            VillagePlayer targetPlayer = dayChangeVillage.vPlayers.findByCharaId(optLoneAttack.get().getTargetCharaId());

            // 襲撃メッセージ
            insertAttackMessageIfNeeded(dayChangeVillage, loneAttack, loneWolf, targetPlayer);

            // 襲撃成功したら死亡
            if (!helper.isAttackSuccess(dayChangeVillage, targetPlayer)) {
                return;
            }

            // 同棲者がいる部屋だったら移動元の同棲者も死亡
            if (helper.isCohabitting(dayChangeVillage, targetPlayer)) {
                VillagePlayer lover = targetPlayer.getTargetCohabitor();
                villageService.dead(lover, dayChangeVillage.day, CDef.DeadReason.襲撃);
                dayChangeVillage.deadPlayers.add(lover, CDef.DeadReason.襲撃);
            }

            // 死亡処理
            villageService.dead(targetPlayer, dayChangeVillage.day, CDef.DeadReason.襲撃);
            dayChangeVillage.deadPlayers.add(targetPlayer, CDef.DeadReason.襲撃);
        });
    }

    // デフォルトの噛み先を設定
    public void insertDefaultAttack(Village village, int newDay) {
        Integer villageId = village.getVillageId();
        village.getVillagePlayers() //
                .filterAlive() //
                .filterBy(vp -> vp.getSkillCodeAsSkill() == CDef.Skill.一匹狼).list.forEach(loneWolf -> {
                    Integer loneWolfCharaId = loneWolf.getCharaId();
                    // 対象
                    Integer targetCharaId = getSelectableTarget(village, loneWolf).getRandom().getCharaId();
                    // 能力セット
                    abilityService.insertAbility(villageId, newDay, loneWolfCharaId, targetCharaId, null, CDef.AbilityType.単独襲撃);
                    // 時計回りの足音セット
                    String footStep =
                            footstepLogic.makeClockwiseFootStep(village, loneWolfCharaId, targetCharaId, village.getVillagePlayerList());
                    footstepLogic.insertFootStep(villageId, newDay, loneWolfCharaId, footStep);
                    insertAbilityMessage(village, newDay, loneWolfCharaId, targetCharaId, footStep, true);
                });
    }

    // 能力セット
    public void setAbility( //
            Village village, //
            VillagePlayer villagePlayer, //
            int day, //
            Integer targetCharaId, //
            String footstep//
    ) {
        if (isInvalidWolfAbility(village, villagePlayer, day, targetCharaId, footstep)) {
            return;
        }
        Integer villageId = village.getVillageId();
        Integer charaId = villagePlayer.getCharaId();
        abilityService.updateAbility(villageId, day, charaId, targetCharaId, CDef.AbilityType.単独襲撃);
        footstepService.deleteFootstep(villageId, day, charaId);
        footstepService.insertFootstep(villageId, day, charaId, footstep);
        insertAbilityMessage(village, day, charaId, targetCharaId, footstep, false);
    }

    // 能力行使先
    public VillagePlayers getSelectableTarget(Village village, VillagePlayer loneWolf) {
        // 自分以外の生存している人
        return village.getVillagePlayers() //
                .filterAlive() //
                .filterNotSpecatate()
                .filterNot(loneWolf)
                .sortedByRoomNumber();
    }

    public List<String> makeSkillHistoryList(Village village, VillagePlayer villagePlayer, int day) {
        // 過去日の能力
        Abilities abilities = abilityService.selectAbilities(village.getVillageId()) //
                .filterPastDay(day) //
                .filterByType(CDef.AbilityType.単独襲撃) //
                .filterByChara(villagePlayer.getCharaId())
                .sortedByDay();
        // 足音
        Footsteps footsteps = footstepService.selectFootsteps(village.getVillageId()).filterByChara(villagePlayer.getCharaId());

        return abilities.list.stream().map(ability -> {
            Integer abilityDay = ability.getDay();
            String footstep =
                    footsteps.filterByDay(abilityDay).list.stream().findFirst().map(Footstep::getFootstepRoomNumbers).orElse("なし");
            VillagePlayer target = village.getVillagePlayers().findByCharaId(ability.getTargetCharaId());
            return String.format("%d日目 %s を襲撃する（%s）", abilityDay, target.name(abilityDay), footstep);
        }).collect(Collectors.toList());
    }

    // ===================================================================================
    //                                                                        Assist Logic
    //                                                                        ============
    private void insertAttackMessageIfNeeded(DayChangeVillage dayChangeVillage, Ability attack, VillagePlayer loneWolf,
            VillagePlayer targetPlayer) {
        String attackMessage = String.format("%s！今日がお前の命日だ！", targetPlayer.getCharaName());
        boolean hasWerewolfFace = helper.hasWerewolfFace(loneWolf);
        messageLogic.saveIgnoreError(new MessageEntity.Builder(dayChangeVillage.villageId, dayChangeVillage.day) //
                .messageType(CDef.MessageType.独り言)
                .content(attackMessage)
                .villagePlayer(loneWolf)
                .faceType(hasWerewolfFace ? CDef.FaceType.囁き : CDef.FaceType.通常)
                .isConvertDisable(true)
                .build());
    }

    private boolean isInvalidWolfAbility(//
            Village village, //
            VillagePlayer villagePlayer, //
            int day, //
            Integer targetCharaId, //
            String footstep//
    ) {
        if (targetCharaId == null || footstep == null) {
            return true;
        }
        if (villagePlayer.isIsDeadTrue()) {
            return true;
        }
        if (getSelectableTarget(village, villagePlayer).list.stream().noneMatch(vp -> vp.getCharaId().equals(targetCharaId))) {
            return true; // 襲撃できない対象を選んでいる
        }
        // 襲撃者、襲撃対象、足音の整合性がとれていなかったらNG
        List<String> footstepCandidateList = footstepLogic.getFootstepCandidateList(village.getVillageId(), villagePlayer, day,
                villagePlayer.getCharaId(), targetCharaId);
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
        String message = messageSource.getMessage("ability.lonewolf.message",
                new String[] { chara.name(), target.name(), footstep, isDefault ? "（自動設定）" : "" }, Locale.JAPAN);
        messageLogic.insertAbilityMessage(village.getVillageId(), day, message);
    }
}
