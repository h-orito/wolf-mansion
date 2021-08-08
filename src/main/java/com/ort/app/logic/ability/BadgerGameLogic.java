package com.ort.app.logic.ability;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

import com.ort.app.datasource.AbilityService;
import com.ort.app.datasource.VillageService;
import com.ort.app.logic.MessageLogic;
import com.ort.app.logic.ability.helper.AttackLogicHelper;
import com.ort.app.logic.daychange.DayChangeVillage;
import com.ort.app.logic.message.MessageEntity;
import com.ort.dbflute.allcommon.CDef;
import com.ort.dbflute.exentity.Abilities;
import com.ort.dbflute.exentity.Ability;
import com.ort.dbflute.exentity.Village;
import com.ort.dbflute.exentity.VillagePlayer;
import com.ort.dbflute.exentity.VillagePlayers;

@Component
public class BadgerGameLogic {

    // ===================================================================================
    //                                                                           Attribute
    //                                                                           =========
    @Autowired
    private MessageSource messageSource;
    @Autowired
    private MessageLogic messageLogic;
    @Autowired
    private VillageService villageService;
    @Autowired
    private AbilityService abilityService;
    @Autowired
    private AttackLogicHelper helper;

    // ===================================================================================
    //                                                                             Execute
    //                                                                             =======
    // 美人局
    public void badgerGame(DayChangeVillage dayChangeVillage) {
        int day = dayChangeVillage.day;
        List<VillagePlayer> livingBadgergameList = dayChangeVillage //
                .alivePlayers() // 死亡していない
                .filterBySkill(CDef.Skill.美人局).list;
        if (CollectionUtils.isEmpty(livingBadgergameList)) {
            return; // 美人局が既に死亡している場合は何もしない
        }

        livingBadgergameList.forEach(badgergame -> {
            Optional<Ability> optBadgergameAbility = dayChangeVillage.abilities //
                    .filterYesterday(day) //
                    .filterByType(CDef.AbilityType.美人局) //
                    .filterByChara(badgergame.getCharaId()).list.stream().findFirst();
            if (!optBadgergameAbility.isPresent()) {
                return; // 能力セットしていない場合は何もしない
            }
            VillagePlayer targetPlayer = dayChangeVillage.vPlayers.findByCharaId(optBadgergameAbility.get().getTargetCharaId());
            // 恋絆
            villageService.insertVillagePlayerStatus(targetPlayer, badgergame, CDef.VillagePlayerStatusType.後追い);
            // メッセージ
            String seduceMessage = String.format("%sは、%sを誘惑した。", //
                    badgergame.name(), //
                    targetPlayer.name());
            messageLogic.saveIgnoreError(MessageEntity.systemBuilder(dayChangeVillage.villageId, dayChangeVillage.day) //
                    .messageType(CDef.MessageType.恋人メッセージ)
                    .content(seduceMessage)
                    .villagePlayer(badgergame)
                    .build());
            String seducedMessage = String.format("%sは%sに誘惑され、恋をしてしまった。", //
                    targetPlayer.name(), //
                    badgergame.name());
            messageLogic.saveIgnoreError(MessageEntity.systemBuilder(dayChangeVillage.villageId, dayChangeVillage.day) //
                    .messageType(CDef.MessageType.恋人メッセージ)
                    .content(seducedMessage)
                    .villagePlayer(targetPlayer)
                    .build());
            // 勝利条件を恋人陣営で上書き
            villageService.updatePlayerWinCamp(targetPlayer, CDef.Camp.恋人陣営);
        });
    }

    // 対象を襲撃
    public void badgerGameAttack(DayChangeVillage dayChangeVillage) {
        int day = dayChangeVillage.day;
        List<VillagePlayer> livingBadgergameList = dayChangeVillage //
                .alivePlayers() // 死亡していない
                .filterBySkill(CDef.Skill.美人局).list;
        if (CollectionUtils.isEmpty(livingBadgergameList)) {
            return; // 美人局が既に死亡している場合は何もしない
        }

        livingBadgergameList.forEach(badgergame -> {
            Optional<Ability> optBadgergameAbility = dayChangeVillage.abilities //
                    .filterByDay(day - 2) // 2日前の能力行使
                    .filterByType(CDef.AbilityType.美人局) //
                    .filterByChara(badgergame.getCharaId()).list.stream().findFirst();
            if (!optBadgergameAbility.isPresent()) {
                return; // 能力セットしていない場合は何もしない
            }
            Ability badgergameAbility = optBadgergameAbility.get();
            VillagePlayer targetPlayer = dayChangeVillage.vPlayers.findByCharaId(badgergameAbility.getTargetCharaId());

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

    // 能力セット
    public void setAbility(//
            Village village, //
            VillagePlayer villagePlayer, //
            int day, //
            Integer targetCharaId //
    ) {
        if (isInvalidAbility(village, villagePlayer, day, targetCharaId)) {
            return;
        }
        Integer villageId = village.getVillageId();
        Integer charaId = villagePlayer.getCharaId();
        if (targetCharaId == null) {
            abilityService.deleteAbility(villageId, day, charaId, CDef.AbilityType.美人局);
        } else {
            abilityService.updateAbility(villageId, day, charaId, targetCharaId, CDef.AbilityType.美人局);
        }
        insertAbilityMessage(village, day, charaId, targetCharaId, false);
    }

    public VillagePlayers getSelectableTarget(Village village, int day, VillagePlayer villagePlayer) {
        Abilities abilities = abilityService.selectAbilities(village.getVillageId());
        // 前日以前に能力行使していたらもう使えない
        if (!abilities.filterPastDay(day).filterByType(CDef.AbilityType.美人局).filterByChara(villagePlayer.getCharaId()).list.isEmpty()) {
            return new VillagePlayers(new ArrayList<>());
        }

        // 自分以外の生存している人
        return village.getVillagePlayers() //
                .filterAlive() //
                .filterNotDummy(village.getVillageSettingsAsOne().get().getDummyCharaId())
                .filterNotSpecatate()
                .filterNot(villagePlayer)
                .sortedByRoomNumber();
    }

    // ===================================================================================
    //                                                                        Assist Logic
    //                                                                        ============
    private boolean isInvalidAbility(Village village, VillagePlayer villagePlayer, int day, Integer targetCharaId) {
        if (villagePlayer.isIsDeadTrue()) {
            return true;
        }
        if (targetCharaId != null
                && getSelectableTarget(village, day, villagePlayer).list.stream().noneMatch(vp -> vp.getCharaId().equals(targetCharaId))) {
            return true;
        }
        return false;
    }

    private void insertAbilityMessage( //
            Village village, //
            int day, //
            Integer charaId, //
            Integer targetCharaId, //
            boolean isDefault //
    ) {
        VillagePlayer chara = village.getVillagePlayers().findByCharaId(charaId);
        String message = messageSource.getMessage("ability.badgergame.message", new String[] { //
                chara.name(), //
                targetCharaId != null ? village.getVillagePlayers().findByCharaId(targetCharaId).name() : "なし", //
                isDefault ? "（自動設定）" : "" //
        }, Locale.JAPAN);
        messageLogic.insertAbilityMessage(village.getVillageId(), day, message);
    }
}
