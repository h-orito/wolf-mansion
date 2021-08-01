package com.ort.app.logic.ability;

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
        if (day == 2) {
            badgerGame2Day(dayChangeVillage, day);
        } else if (day == 3) {
            badgerGame3Day(dayChangeVillage, day);
        }
    }

    private void badgerGame2Day(DayChangeVillage dayChangeVillage, int day) {
        List<VillagePlayer> livingBadgergameList = dayChangeVillage //
                .alivePlayers() // 死亡していない
                .filterBySkill(CDef.Skill.美人局).list;
        if (CollectionUtils.isEmpty(livingBadgergameList)) {
            return; // 美人局が既に死亡している場合は何もしない
        }

        livingBadgergameList.forEach(jorogumo -> {
            Optional<Ability> optCourt = dayChangeVillage.abilities //
                    .filterYesterday(day) //
                    .filterByType(CDef.AbilityType.美人局) //
                    .filterByChara(jorogumo.getCharaId()).list.stream().findFirst();
            if (!optCourt.isPresent()) {
                return; // 能力セットしていない場合は何もしない
            }
            VillagePlayer targetPlayer = dayChangeVillage.vPlayers.findByCharaId(optCourt.get().getTargetCharaId());
            // 恋絆
            villageService.insertVillagePlayerStatus(targetPlayer, jorogumo, CDef.VillagePlayerStatusType.後追い);
            // メッセージ
            String seduceMessage = String.format("%sは、%sを誘惑した。", //
                    jorogumo.name(), //
                    targetPlayer.name());
            messageLogic.saveIgnoreError(MessageEntity.systemBuilder(dayChangeVillage.villageId, dayChangeVillage.day) //
                    .messageType(CDef.MessageType.恋人メッセージ)
                    .content(seduceMessage)
                    .villagePlayer(jorogumo)
                    .build());
            String seducedMessage = String.format("%sは%sに誘惑され、恋をしてしまった。", //
                    targetPlayer.name(), //
                    jorogumo.name());
            messageLogic.saveIgnoreError(MessageEntity.systemBuilder(dayChangeVillage.villageId, dayChangeVillage.day) //
                    .messageType(CDef.MessageType.恋人メッセージ)
                    .content(seducedMessage)
                    .villagePlayer(targetPlayer)
                    .build());
        });
    }

    // 対象を襲撃
    private void badgerGame3Day(DayChangeVillage dayChangeVillage, int day) {
        dayChangeVillage.abilities //
                .filterByDay(1) // 1日目の能力行使
                .filterByType(CDef.AbilityType.美人局).list.forEach(badgergameAbility -> {
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

    // 日付変更時のデフォルトセット
    public void insertDefaultSeduce(Village village, int newDay) {
        if (newDay != 1) {
            return;
        }
        Integer villageId = village.getVillageId();
        // 生存している美人局
        VillagePlayers aliveJorogumos = village.getVillagePlayers() //
                .filterAlive() //
                .filterBy(vp -> vp.getSkillCodeAsSkill() == CDef.Skill.美人局);
        if (aliveJorogumos.list.isEmpty()) {
            return;
        }
        aliveJorogumos.list.forEach(jorogumo -> {
            Integer charaId = jorogumo.getCharaId();
            // 誘惑される人(生存者の中の誰か）
            Integer targetCharaId = getSelectableTarget(village, newDay, jorogumo) //
                    .getRandom()
                    .getCharaId();
            abilityService.insertAbility(villageId, newDay, charaId, targetCharaId, null, CDef.AbilityType.美人局);
            insertAbilityMessage(village, newDay, charaId, targetCharaId, true);
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
        abilityService.updateAbility(villageId, day, charaId, targetCharaId, CDef.AbilityType.美人局);
        insertAbilityMessage(village, day, charaId, targetCharaId, false);
    }

    public VillagePlayers getSelectableTarget(Village village, int day, VillagePlayer courtship) {
        if (day != 1) {
            return null;
        }
        // 自分以外の生存している人
        return village.getVillagePlayers() //
                .filterAlive() //
                .filterNotDummy(village.getVillageSettingsAsOne().get().getDummyCharaId())
                .filterNotSpecatate()
                .filterNot(courtship)
                .sortedByRoomNumber();
    }

    // ===================================================================================
    //                                                                        Assist Logic
    //                                                                        ============
    private boolean isInvalidAbility(Village village, VillagePlayer villagePlayer, int day, Integer targetCharaId) {
        if (targetCharaId == null) {
            return true;
        }
        if (villagePlayer.isIsDeadTrue()) {
            return true;
        }
        if (getSelectableTarget(village, day, villagePlayer).list.stream().noneMatch(vp -> vp.getCharaId().equals(targetCharaId))) {
            return true;
        }
        if (day != 1) {
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
        VillagePlayer target = village.getVillagePlayers().findByCharaId(targetCharaId);
        String message = messageSource.getMessage("ability.badgergame.message",
                new String[] { chara.name(), target.name(), isDefault ? "（自動設定）" : "" }, Locale.JAPAN);
        messageLogic.insertAbilityMessage(village.getVillageId(), day, message);
    }
}
