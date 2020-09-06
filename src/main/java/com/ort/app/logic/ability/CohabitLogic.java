package com.ort.app.logic.ability;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

import com.ort.app.datasource.AbilityService;
import com.ort.app.logic.MessageLogic;
import com.ort.app.logic.daychange.DayChangeLogicHelper;
import com.ort.app.logic.daychange.DayChangeVillage;
import com.ort.dbflute.allcommon.CDef;
import com.ort.dbflute.exentity.Abilities;
import com.ort.dbflute.exentity.Village;
import com.ort.dbflute.exentity.VillagePlayer;
import com.ort.dbflute.exentity.VillagePlayers;

@Component
public class CohabitLogic {

    // ===================================================================================
    //                                                                           Attribute
    //                                                                           =========
    @Autowired
    private DayChangeLogicHelper helper;
    @Autowired
    private MessageSource messageSource;
    @Autowired
    private AbilityService abilityService;
    @Autowired
    private MessageLogic messageLogic;

    // ===================================================================================
    //                                                                             Execute
    //                                                                             =======
    // 同棲
    public void cohabit(DayChangeVillage dayChangeVillage) {
        dayChangeVillage.abilities //
                .filterYesterday(dayChangeVillage.day)
                .filterByType(CDef.AbilityType.同棲)
                .filterMylsefNotIn(dayChangeVillage.deadPlayers.getList())
                .filterTargetNotIn(dayChangeVillage.deadPlayers.getList()).list.forEach(cohabit -> {
                    // 移動元
                    VillagePlayer from = dayChangeVillage.vPlayers.findByCharaId(cohabit.getCharaId());
                    // 移動先
                    VillagePlayer to = dayChangeVillage.vPlayers.findByCharaId(cohabit.getTargetCharaId());
                    String message = String.format("%sは、%sの部屋で過ごすことにした。", from.name(), to.name());
                    helper.insertMessage(dayChangeVillage, CDef.MessageType.非公開システムメッセージ, message);
                });
    }

    // 日付変更時のデフォルト能力セット
    public void insertDefaultCohabit(Village village, int newDay) {
        Integer villageId = village.getVillageId();
        // 同棲者
        VillagePlayers aliveCohabiters = village.getVillagePlayers() //
                .filterAlive() //
                .filterBy(vp -> vp.getSkillCodeAsSkill() == CDef.Skill.同棲者 && vp.isIsDeadFalse());
        if (aliveCohabiters.list.isEmpty()) {
            return;
        }
        Set<Integer> cohabitSet = new HashSet<>();
        List<VillagePlayer> list = new ArrayList<>(aliveCohabiters.list);
        Collections.shuffle(list);
        list.forEach(cohabiter -> {
            VillagePlayer lover = cohabiter.getTargetLover();
            if (cohabitSet.contains(cohabiter.getVillagePlayerId())) {
                return;
            }
            abilityService.insertAbility(villageId, newDay, cohabiter.getCharaId(), lover.getCharaId(), null, CDef.AbilityType.同棲);
            cohabitSet.add(cohabiter.getVillagePlayerId());
            cohabitSet.add(lover.getVillagePlayerId());
            insertAbilitySetMessage(village, newDay, cohabiter.getCharaId(), lover.getCharaId(), true);
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
        Integer loverCharaId = villagePlayer.getTargetLover().getCharaId();
        Integer myself = targetCharaId.equals(villagePlayer.getCharaId()) ? loverCharaId : villagePlayer.getCharaId();
        abilityService.deleteAbility(villageId, day, myself, CDef.AbilityType.同棲);
        abilityService.deleteAbility(villageId, day, targetCharaId, CDef.AbilityType.同棲);
        abilityService.insertAbility(villageId, day, myself, targetCharaId, null, CDef.AbilityType.同棲);
        insertAbilitySetMessage(village, day, myself, targetCharaId, false);
    }

    public VillagePlayers getSelectableTarget(VillagePlayer villagePlayer) {
        return new VillagePlayers(Arrays.asList(villagePlayer, villagePlayer.getTargetLover())).sortedByRoomNumber();
    }

    // 能力行使履歴
    public List<String> makeSkillHistoryList(Village village, VillagePlayer villagePlayer, int day) {
        // 過去日の能力
        Integer charaId = villagePlayer.getCharaId();
        Abilities abilities = abilityService.selectAbilities(village.getVillageId()) //
                .filterPastDay(day) //
                .filterByType(CDef.AbilityType.同棲) //
                .filterBy(a -> a.getCharaId().equals(charaId) || a.getTargetCharaId().equals(charaId))
                .sortedByDay();
        return abilities.list.stream().map(ability -> {
            VillagePlayer target = village.getVillagePlayers().findByCharaId(ability.getTargetCharaId());
            return String.format("%d日目 %s の部屋で過ごす", ability.getDay(), target.name());
        }).collect(Collectors.toList());
    }

    // ===================================================================================
    //                                                                        Assist Logic
    //                                                                        ============
    private boolean isInvalidAbility(Village village, VillagePlayer villagePlayer, int day, Integer targetCharaId) {
        VillagePlayer lover = villagePlayer.getTargetLover();
        if (lover == null) {
            return true;
        }
        if (villagePlayer.isIsDeadTrue() || lover.isIsDeadTrue()) {
            return true;
        }
        if (targetCharaId != villagePlayer.getCharaId() && targetCharaId != lover.getCharaId()) {
            return true;
        }
        return false;
    }

    private void insertAbilitySetMessage(Village village, int day, Integer charaId, Integer targetCharaId, boolean isDefault) {
        VillagePlayer myself = village.getVillagePlayers().findByCharaId(charaId);
        VillagePlayer target = village.getVillagePlayers().findByCharaId(targetCharaId);
        String message = messageSource.getMessage("ability.cohabit.message",
                new String[] { myself.name(), target.name(), isDefault ? "（自動設定）" : "" }, Locale.JAPAN);
        messageLogic.insertAbilityMessage(village.getVillageId(), day, message);
    }
}
