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
import com.ort.app.logic.daychange.DayChangeVillage;
import com.ort.app.logic.message.MessageEntity;
import com.ort.dbflute.allcommon.CDef;
import com.ort.dbflute.exentity.Ability;
import com.ort.dbflute.exentity.Village;
import com.ort.dbflute.exentity.VillagePlayer;
import com.ort.dbflute.exentity.VillagePlayers;

@Component
public class StalkingLogic {

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
    // ストーキング
    public void stalking(DayChangeVillage dayChangeVillage) {
        int day = dayChangeVillage.day;
        if (day != 2) {
            return;
        }

        List<VillagePlayer> livingStalkerList = dayChangeVillage //
                .alivePlayers() // 死亡していない
                .filterBySkill(CDef.Skill.ストーカー).list;
        if (CollectionUtils.isEmpty(livingStalkerList)) {
            return; // ストーカーが既に死亡している場合は何もしない
        }

        livingStalkerList.forEach(stalker -> {
            Optional<Ability> optCourt = dayChangeVillage.abilities //
                    .filterYesterday(day) //
                    .filterByType(CDef.AbilityType.ストーキング) //
                    .filterByChara(stalker.getCharaId()).list.stream().findFirst();
            if (!optCourt.isPresent()) {
                return; // 能力セットしていない場合は何もしない
            }
            VillagePlayer targetPlayer = dayChangeVillage.vPlayers.findByCharaId(optCourt.get().getTargetCharaId());
            // 恋絆
            villageService.insertVillagePlayerStatus(stalker, targetPlayer, CDef.VillagePlayerStatusType.後追い);
            // メッセージ
            String courtMessage = String.format("%sは、%sをストーキングし始めた。\n%sは%sのようだ。", //
                    stalker.name(), //
                    targetPlayer.name(), //
                    targetPlayer.name(), //
                    targetPlayer.getSkillCodeAsSkill().alias());
            messageLogic.saveIgnoreError(MessageEntity.systemBuilder(dayChangeVillage.villageId, dayChangeVillage.day) //
                    .messageType(CDef.MessageType.恋人メッセージ)
                    .content(courtMessage)
                    .villagePlayer(stalker)
                    .build());
        });
    }

    // 日付変更時のデフォルトセット
    public void insertDefaultStalking(Village village, int newDay) {
        if (newDay != 1) {
            return;
        }
        Integer villageId = village.getVillageId();
        // 生存しているストーカー
        VillagePlayers aliveStalkers = village.getVillagePlayers() //
                .filterAlive() //
                .filterBy(vp -> vp.getSkillCodeAsSkill() == CDef.Skill.ストーカー);
        if (aliveStalkers.list.isEmpty()) {
            return;
        }
        aliveStalkers.list.forEach(hunter -> {
            Integer courtshipCharaId = hunter.getCharaId();
            // ストーキングされる人(生存者の中の誰か）
            Integer targetCharaId = getSelectableTarget(village, newDay, hunter) //
                    .getRandom()
                    .getCharaId();
            abilityService.insertAbility(villageId, newDay, courtshipCharaId, targetCharaId, null, CDef.AbilityType.ストーキング);
            insertAbilityMessage(village, newDay, courtshipCharaId, targetCharaId, true);
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
        abilityService.updateAbility(villageId, day, charaId, targetCharaId, CDef.AbilityType.ストーキング);
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
        String message = messageSource.getMessage("ability.stalking.message",
                new String[] { chara.name(), target.name(), isDefault ? "（自動設定）" : "" }, Locale.JAPAN);
        messageLogic.insertAbilityMessage(village.getVillageId(), day, message);
    }
}
