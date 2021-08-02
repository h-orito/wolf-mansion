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
import com.ort.app.logic.daychange.DayChangeVillage;
import com.ort.app.logic.message.MessageEntity;
import com.ort.dbflute.allcommon.CDef;
import com.ort.dbflute.exentity.Abilities;
import com.ort.dbflute.exentity.Ability;
import com.ort.dbflute.exentity.Village;
import com.ort.dbflute.exentity.VillagePlayer;
import com.ort.dbflute.exentity.VillagePlayers;

@Component
public class CheatLogic {

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

    // ===================================================================================
    //                                                                             Execute
    //                                                                             =======
    // 誑かす
    public void cheat(DayChangeVillage dayChangeVillage) {
        int day = dayChangeVillage.day;

        List<VillagePlayer> livingCheaterFoxList = dayChangeVillage //
                .alivePlayers() // 死亡していない
                .filterBySkill(CDef.Skill.誑狐).list;
        if (CollectionUtils.isEmpty(livingCheaterFoxList)) {
            return; // ストーカーが既に死亡している場合は何もしない
        }

        livingCheaterFoxList.forEach(cheater -> {
            Optional<Ability> optCourt = dayChangeVillage.abilities //
                    .filterYesterday(day) //
                    .filterByType(CDef.AbilityType.誑かす) //
                    .filterByChara(cheater.getCharaId()).list.stream().findFirst();
            if (!optCourt.isPresent()) {
                return; // 能力セットしていない場合は何もしない
            }
            VillagePlayer targetPlayer = dayChangeVillage.vPlayers.findByCharaId(optCourt.get().getTargetCharaId());
            // 狐憑き
            villageService.insertVillagePlayerStatus(cheater, targetPlayer, CDef.VillagePlayerStatusType.狐憑き);
            // メッセージ
            String cheatMessage = String.format("%sは%sを誑かし、仲間に引き入れた。", cheater.name(), targetPlayer.name());
            messageLogic.saveIgnoreError(MessageEntity.systemBuilder(dayChangeVillage.villageId, dayChangeVillage.day) //
                    .messageType(CDef.MessageType.妖狐メッセージ)
                    .content(cheatMessage)
                    .villagePlayer(cheater)
                    .build());
            String cheatedMessage = String.format("あなたは%sに誑かされ、妖狐に与するものとなりました。", cheater.name());
            messageLogic.saveIgnoreError(MessageEntity.systemBuilder(dayChangeVillage.villageId, dayChangeVillage.day) //
                    .messageType(CDef.MessageType.妖狐メッセージ)
                    .content(cheatedMessage)
                    .villagePlayer(targetPlayer)
                    .build());
            // 勝利条件を妖狐陣営で上書き
            if (!targetPlayer.hasLover()) {
                villageService.updatePlayerWinCamp(targetPlayer, CDef.Camp.狐陣営);
            }
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
            abilityService.deleteAbility(villageId, day, charaId, CDef.AbilityType.誑かす);
        } else {
            abilityService.updateAbility(villageId, day, charaId, targetCharaId, CDef.AbilityType.誑かす);
        }
        insertAbilityMessage(village, day, charaId, targetCharaId, false);
    }

    public VillagePlayers getSelectableTarget(Village village, int day, VillagePlayer villagePlayer) {
        Abilities abilities = abilityService.selectAbilities(village.getVillageId());
        // 前日以前に能力行使していたらもう使えない
        if (!abilities.filterPastDay(day).filterByType(CDef.AbilityType.誑かす).filterByChara(villagePlayer.getCharaId()).list.isEmpty()) {
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
        String message = messageSource.getMessage("ability.cheat.message", new String[] { //
                chara.name(), //
                targetCharaId != null ? village.getVillagePlayers().findByCharaId(targetCharaId).name() : "なし", //
                isDefault ? "（自動設定）" : ""//
        }, Locale.JAPAN);
        messageLogic.insertAbilityMessage(village.getVillageId(), day, message);
    }
}
