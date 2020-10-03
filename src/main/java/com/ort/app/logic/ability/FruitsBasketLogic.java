package com.ort.app.logic.ability;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.StringJoiner;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

import com.ort.app.datasource.AbilityService;
import com.ort.app.datasource.VillageService;
import com.ort.app.logic.AssignLogic;
import com.ort.app.logic.MessageLogic;
import com.ort.app.logic.daychange.DayChangeLogicHelper;
import com.ort.app.logic.daychange.DayChangeVillage;
import com.ort.dbflute.allcommon.CDef;
import com.ort.dbflute.exentity.Abilities;
import com.ort.dbflute.exentity.Village;
import com.ort.dbflute.exentity.VillagePlayer;
import com.ort.dbflute.exentity.VillagePlayers;

@Component
public class FruitsBasketLogic {

    // ===================================================================================
    //                                                                           Attribute
    //                                                                           =========
    @Autowired
    private DayChangeLogicHelper helper;
    @Autowired
    private MessageSource messageSource;
    @Autowired
    private AssignLogic assignLogic;
    @Autowired
    private MessageLogic messageLogic;
    @Autowired
    private VillageService villageService;
    @Autowired
    private AbilityService abilityService;

    // ===================================================================================
    //                                                                             Execute
    //                                                                             =======
    // フルーツバスケット発動
    public void fruitBasket(DayChangeVillage dayChangeVillage) {
        int day = dayChangeVillage.day;
        dayChangeVillage.abilities //
                .filterYesterday(day) //
                .filterByType(CDef.AbilityType.フルーツバスケット) //
                .filterMylsefNotIn(dayChangeVillage.deadPlayers.getList()).list.forEach(ability -> {
                    Village village = villageService.selectVillage(dayChangeVillage.villageId, false, false);
                    VillagePlayer player = village.getVillagePlayers().findByCharaId(ability.getCharaId());
                    assignLogic.reAssignRoom(village, village.getVillagePlayers());
                    village = villageService.selectVillage(dayChangeVillage.villageId, false, false);
                    insertFruitsBasketMessage(dayChangeVillage, village, player);
                });
    }

    private void insertFruitsBasketMessage(DayChangeVillage dayChangeVillage, Village village, VillagePlayer player) {
        StringJoiner joiner =
                new StringJoiner("\n", String.format("%sは突然「フルーツバスケット！」と叫んだ。\nなんと、全員の部屋がシャッフルされてしまった。\n\n", player.name()), "");
        village.getVillagePlayers().list.forEach(vp -> {
            joiner.add(String.format("[%s] %sは、部屋番号%02dに移動した。", vp.getChara().get().getCharaShortName(), vp.getChara().get().getCharaName(),
                    vp.getRoomNumber()));
        });
        String message = joiner.toString();
        helper.insertMessage(dayChangeVillage, CDef.MessageType.公開システムメッセージ, message);
    }

    // 能力セット
    public void setAbility(//
            Village village, //
            VillagePlayer villagePlayer, //
            int day, //
            Integer targetCharaId) {
        if (isInvalidAbility(village, villagePlayer, day, targetCharaId)) {
            return;
        }
        Integer villageId = village.getVillageId();
        Integer charaId = villagePlayer.getCharaId();
        if (targetCharaId == null) {
            abilityService.deleteAbility(villageId, day, charaId, CDef.AbilityType.フルーツバスケット);
        } else {
            abilityService.updateAbility(villageId, day, charaId, targetCharaId, CDef.AbilityType.フルーツバスケット);
        }
        insertAbilitySetMessage(village, day, villagePlayer, targetCharaId, false);
    }

    public VillagePlayers getSelectableTarget(Village village, VillagePlayer villagePlayer, int day) {
        if (day < 2) {
            return new VillagePlayers(new ArrayList<>());
        }
        Abilities abilities = abilityService.selectAbilities(village.getVillageId());
        // 前日以前に能力行使していたらもう使えない
        if (!abilities.filterPastDay(day).filterByType(CDef.AbilityType.フルーツバスケット).filterByChara(villagePlayer.getCharaId()).list
                .isEmpty()) {
            return new VillagePlayers(new ArrayList<>());
        }
        return new VillagePlayers(Arrays.asList(villagePlayer));
    }

    // 能力行使履歴
    public List<String> makeSkillHistoryList(Village village, VillagePlayer villagePlayer, int day) {
        // 過去日の能力
        Abilities abilities = abilityService.selectAbilities(village.getVillageId()) //
                .filterPastDay(day) //
                .filterByType(CDef.AbilityType.フルーツバスケット) //
                .filterByChara(villagePlayer.getCharaId())
                .sortedByDay();
        return abilities.list.stream().map(ability -> {
            Integer abilityDay = ability.getDay();
            return String.format("%d日目 フルーツバスケット！", abilityDay);
        }).collect(Collectors.toList());
    }

    // ===================================================================================
    //                                                                        Assist Logic
    //                                                                        ============
    private boolean isInvalidAbility(Village village, VillagePlayer villagePlayer, int day, Integer targetCharaId) {
        if (villagePlayer.isIsDeadTrue()) {
            return true; // 死亡していたら使えない
        }
        if (targetCharaId != null && this.getSelectableTarget(village, villagePlayer, day).list.stream()
                .noneMatch(vp -> vp.getCharaId().equals(targetCharaId))) {
            return true; // 選べない対象を選んでいる
        }
        return false;
    }

    private void insertAbilitySetMessage(Village village, int day, VillagePlayer villagePlayer, Integer targetCharaId, boolean isDefault) {
        String message;
        if (targetCharaId == null) {
            message = String.format("%sがフルーツバスケットをやめました。", villagePlayer.name());
        } else {
            message = messageSource.getMessage("ability.fruitsbasket.message", new String[] { villagePlayer.name() }, Locale.JAPAN);
        }
        messageLogic.insertAbilityMessage(village.getVillageId(), day, message);
    }
}
