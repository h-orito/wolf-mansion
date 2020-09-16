package com.ort.app.logic.ability;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

import com.ort.app.datasource.AbilityService;
import com.ort.app.logic.FootstepLogic;
import com.ort.app.logic.MessageLogic;
import com.ort.app.logic.daychange.DayChangeLogicHelper;
import com.ort.app.logic.daychange.DayChangeVillage;
import com.ort.dbflute.allcommon.CDef;
import com.ort.dbflute.exentity.Abilities;
import com.ort.dbflute.exentity.Ability;
import com.ort.dbflute.exentity.Village;
import com.ort.dbflute.exentity.VillagePlayer;
import com.ort.dbflute.exentity.VillagePlayers;

@Component
public class TrapLogic {

    // ===================================================================================
    //                                                                           Attribute
    //                                                                           =========
    @Autowired
    private DayChangeLogicHelper helper;
    @Autowired
    private MessageSource messageSource;
    @Autowired
    private FootstepLogic footstepLogic;
    @Autowired
    private MessageLogic messageLogic;
    @Autowired
    private AbilityService abilityService;

    // ===================================================================================
    //                                                                             Execute
    //                                                                             =======
    // 罠設置メッセージ
    public void insertTrapMessages(DayChangeVillage dayChangeVillage) {
        dayChangeVillage.abilities //
                .filterYesterday(dayChangeVillage.day) //
                .filterByType(CDef.AbilityType.罠設置) //
                .filterMylsefNotIn(dayChangeVillage.deadPlayers.getList()) //
                        .list.forEach(trap -> insertTrapMessage(trap, dayChangeVillage));
    }

    // 罠発動
    public void trap(DayChangeVillage dayChangeVillage) {
        int day = dayChangeVillage.day;
        dayChangeVillage.abilities //
                .filterYesterday(day) //
                .filterByType(CDef.AbilityType.罠設置) //
                .filterMylsefNotIn(dayChangeVillage.deadPlayers.filterSuddenly().getList()).list.stream().flatMap(trap -> {
                    // 設置された部屋
                    Integer roomNumber = dayChangeVillage.vPlayers.findByCharaId(trap.getTargetCharaId()).getRoomNumber();
                    // 設置された部屋を通過した人
                    return footstepLogic
                            .getPassedPlayerList(dayChangeVillage.villageId, day - 1, roomNumber, dayChangeVillage.vPlayers.list)
                            .stream();
                }).filter(vp -> dayChangeVillage.isAlive(vp)).forEach(trappedPlayer -> {
                    helper.updateVillagePlayerDead(day, trappedPlayer, CDef.DeadReason.罠死); // 死亡処理
                    dayChangeVillage.deadPlayers.add(trappedPlayer, CDef.DeadReason.罠死);
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
            abilityService.deleteAbility(villageId, day, charaId, CDef.AbilityType.罠設置);
        } else {
            abilityService.updateAbility(villageId, day, charaId, targetCharaId, CDef.AbilityType.罠設置);
        }
        insertAbilitySetMessage(village, day, villagePlayer, targetCharaId, false);
    }

    public VillagePlayers getSelectableTarget(Village village, VillagePlayer villagePlayer, int day) {
        if (day < 2) {
            return new VillagePlayers(new ArrayList<>());
        }
        Abilities abilities = abilityService.selectAbilities(village.getVillageId());
        // 前日以前に能力行使していたらもう使えない
        if (!abilities.filterPastDay(day).filterByType(CDef.AbilityType.罠設置).filterByChara(villagePlayer.getCharaId()).list.isEmpty()) {
            return new VillagePlayers(new ArrayList<>());
        }
        return village.getVillagePlayers() //
                .filterAlive()
                .filterNotSpecatate()
                .sortedByRoomNumber();
    }

    // 能力行使履歴
    public List<String> makeSkillHistoryList(Village village, VillagePlayer villagePlayer, int day) {
        // 過去日の能力
        Abilities abilities = abilityService.selectAbilities(village.getVillageId()) //
                .filterPastDay(day) //
                .filterByType(CDef.AbilityType.罠設置) //
                .filterByChara(villagePlayer.getCharaId())
                .sortedByDay();
        return abilities.list.stream().map(ability -> {
            VillagePlayer target = village.getVillagePlayers().findByCharaId(ability.getTargetCharaId());
            return String.format("%d日目 %s の部屋に罠を設置する", ability.getDay(), target.name());
        }).collect(Collectors.toList());
    }

    // ===================================================================================
    //                                                                        Assist Logic
    //                                                                        ============
    private void insertTrapMessage(Ability trap, DayChangeVillage dayChangeVillage) {
        // 設置した人
        VillagePlayer trapper = dayChangeVillage.vPlayers.findByCharaId(trap.getCharaId());
        // 設置された部屋の人
        VillagePlayer targetPlayer = dayChangeVillage.vPlayers.findByCharaId(trap.getTargetCharaId());
        String message = String.format("%sは、%sの部屋に罠を設置した。", trapper.name(), targetPlayer.name());
        helper.insertMessage(dayChangeVillage, CDef.MessageType.非公開システムメッセージ, message);
    }

    private boolean isInvalidAbility(Village village, VillagePlayer villagePlayer, int day, Integer targetCharaId) {
        if (villagePlayer.isIsDeadTrue()) {
            return true; // 死亡していたら設置できない
        }
        if (this.getSelectableTarget(village, villagePlayer, day).list.stream().noneMatch(vp -> vp.getCharaId().equals(targetCharaId))) {
            return true; // 選べない対象を選んでいる
        }
        return false;
    }

    private void insertAbilitySetMessage(Village village, int day, VillagePlayer villagePlayer, Integer targetCharaId, boolean isDefault) {
        String message;
        if (targetCharaId == null) {
            message = String.format("%sが罠を解除しました。", villagePlayer.name());
        } else {
            VillagePlayer target = village.getVillagePlayers().findByCharaId(targetCharaId);
            message = messageSource.getMessage("ability.trapper.message",
                    new String[] { villagePlayer.name(), target.name(), isDefault ? "（自動設定）" : "" }, Locale.JAPAN);
        }
        messageLogic.insertAbilityMessage(village.getVillageId(), day, message);
    }
}
