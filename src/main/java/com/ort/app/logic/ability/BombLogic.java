package com.ort.app.logic.ability;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;
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
public class BombLogic {

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
    // 爆弾設置メッセージ
    public void insertBombMessages(DayChangeVillage dayChangeVillage) {
        dayChangeVillage.abilities //
                .filterYesterday(dayChangeVillage.day) //
                .filterByType(CDef.AbilityType.爆弾設置) //
                .filterMylsefNotIn(dayChangeVillage.deadPlayers.getList()) //
                        .list.forEach(bomb -> insertBombMessage(bomb, dayChangeVillage));
    }

    // 爆弾発動
    public void bomb(DayChangeVillage dayChangeVillage) {
        int day = dayChangeVillage.day;

        dayChangeVillage.abilities //
                .filterYesterday(day)
                .filterByType(CDef.AbilityType.爆弾設置)
                .filterMylsefNotIn(dayChangeVillage.deadPlayers.filterSuddenly().getList()).list.stream().flatMap(bomb -> {
                    // 設置した人
                    VillagePlayer bomber = dayChangeVillage.vPlayers.findByCharaId(bomb.getCharaId());
                    // 設置された部屋の人
                    VillagePlayer bombedRoomPlayer = dayChangeVillage.vPlayers.findByCharaId(bomb.getTargetCharaId());
                    // 設置された部屋を通過した人
                    List<VillagePlayer> bombedPlayerList = footstepLogic.getPassedPlayerList(dayChangeVillage.villageId, day - 1,
                            bombedRoomPlayer.getRoomNumber(), dayChangeVillage.vPlayers.list);
                    if (bombedPlayerList.isEmpty()) {
                        // 爆弾魔が死亡
                        return Arrays.asList(bomber).stream();
                    } else {
                        // 設置された部屋の人と通過した人が死亡
                        Set<VillagePlayer> bombedPlayerSet = new HashSet<>(bombedPlayerList);
                        if (!isAbsence(dayChangeVillage, bombedRoomPlayer)) {
                            // 設置された部屋の人が同棲先に行っていない場合は設置された部屋の人は死亡する
                            bombedPlayerSet.add(bombedRoomPlayer);
                        }
                        if (isCohabitting(dayChangeVillage, bombedRoomPlayer)) {
                            // 設置された部屋に同棲者が来ていたら来ていた同棲者も死亡する
                            bombedPlayerSet.add(bombedRoomPlayer.getTargetLover());
                        }
                        return bombedPlayerSet.stream();
                    }
                }).filter(vp -> dayChangeVillage.isAlive(vp)).forEach(bombedPlayer -> {
                    // 死亡処理
                    helper.updateVillagePlayerDead(day, bombedPlayer, CDef.DeadReason.爆死); // 死亡処理
                    dayChangeVillage.deadPlayers.add(bombedPlayer, CDef.DeadReason.爆死);
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
            abilityService.deleteAbility(villageId, day, charaId, CDef.AbilityType.爆弾設置);
        } else {
            abilityService.updateAbility(villageId, day, charaId, targetCharaId, CDef.AbilityType.爆弾設置);
        }
        insertAbilitySetMessage(village, day, villagePlayer, targetCharaId, false);
    }

    public VillagePlayers getSelectableTarget(Village village, VillagePlayer villagePlayer, int day) {
        if (day < 2) {
            return new VillagePlayers(new ArrayList<>());
        }
        Abilities abilities = abilityService.selectAbilities(village.getVillageId());
        // 前日以前に能力行使していたらもう使えない
        if (!abilities.filterPastDay(day).filterByType(CDef.AbilityType.爆弾設置).filterByChara(villagePlayer.getCharaId()).list.isEmpty()) {
            return new VillagePlayers(new ArrayList<>());
        }
        return village.getVillagePlayers() //
                .filterAlive()
                .filterNotSpecatate()
                .filterNot(villagePlayer)
                .sortedByRoomNumber();
    }

    // 能力行使履歴
    public List<String> makeSkillHistoryList(Village village, VillagePlayer villagePlayer, int day) {
        // 過去日の能力
        Abilities abilities = abilityService.selectAbilities(village.getVillageId()) //
                .filterPastDay(day) //
                .filterByType(CDef.AbilityType.爆弾設置) //
                .filterByChara(villagePlayer.getCharaId())
                .sortedByDay();
        return abilities.list.stream().map(ability -> {
            VillagePlayer target = village.getVillagePlayers().findByCharaId(ability.getTargetCharaId());
            return String.format("%d日目 %s の部屋に爆弾を設置する", ability.getDay(), target.name());
        }).collect(Collectors.toList());
    }

    // ===================================================================================
    //                                                                        Assist Logic
    //                                                                        ============
    private void insertBombMessage(Ability bomb, DayChangeVillage dayChangeVillage) {
        // 設置した人
        VillagePlayer trapper = dayChangeVillage.vPlayers.findByCharaId(bomb.getCharaId());
        // 設置された部屋の人
        VillagePlayer targetPlayer = dayChangeVillage.vPlayers.findByCharaId(bomb.getTargetCharaId());
        String message = String.format("%sは、%sの部屋に爆弾を設置した。", trapper.name(), targetPlayer.name());

        helper.insertMessage(dayChangeVillage, CDef.MessageType.非公開システムメッセージ, message);
    }

    private boolean isAbsence(DayChangeVillage dayChangeVillage, VillagePlayer targetPlayer) {
        return !dayChangeVillage.abilities //
                .filterYesterday(dayChangeVillage.day) //
                .filterByType(CDef.AbilityType.同棲) //
                .filterMylsefNotIn(dayChangeVillage.deadPlayers.getList()) //
                .filterTargetNotIn(dayChangeVillage.deadPlayers.getList()) //
                .filterByChara(targetPlayer.getCharaId()).list.isEmpty();
    }

    private boolean isCohabitting(DayChangeVillage dayChangeVillage, VillagePlayer targetPlayer) {
        return !dayChangeVillage.abilities //
                .filterYesterday(dayChangeVillage.day) //
                .filterByType(CDef.AbilityType.同棲) //
                .filterMylsefNotIn(dayChangeVillage.deadPlayers.getList()) //
                .filterTargetNotIn(dayChangeVillage.deadPlayers.getList()) //
                .filterByTargetChara(targetPlayer.getCharaId()).list.isEmpty();
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
            message = String.format("%sが爆弾を解除しました。", villagePlayer.name());
        } else {
            VillagePlayer target = village.getVillagePlayers().findByCharaId(targetCharaId);
            message = messageSource.getMessage("ability.bomber.message",
                    new String[] { villagePlayer.name(), target.name(), isDefault ? "（自動設定）" : "" }, Locale.JAPAN);
        }
        messageLogic.insertAbilityMessage(village.getVillageId(), day, message);
    }
}
