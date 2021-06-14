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
import com.ort.app.datasource.VillageService;
import com.ort.app.logic.FootstepLogic;
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
public class BombLogic {

    // ===================================================================================
    //                                                                           Attribute
    //                                                                           =========
    @Autowired
    private MessageSource messageSource;
    @Autowired
    private FootstepLogic footstepLogic;
    @Autowired
    private MessageLogic messageLogic;
    @Autowired
    private AbilityService abilityService;
    @Autowired
    private VillageService villageService;

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
                            bombedPlayerSet.add(bombedRoomPlayer.getTargetCohabitor());
                        }
                        return bombedPlayerSet.stream();
                    }
                }).filter(vp -> dayChangeVillage.isAlive(vp)).forEach(bombedPlayer -> {
                    // 死亡処理
                    villageService.dead(bombedPlayer, day, CDef.DeadReason.爆死);
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
            Integer abilityDay = ability.getDay();
            VillagePlayer target = village.getVillagePlayers().findByCharaId(ability.getTargetCharaId());
            return String.format("%d日目 %s の部屋に爆弾を設置する", abilityDay, target.name(abilityDay));
        }).collect(Collectors.toList());
    }

    // エピローグ遷移時点で爆弾を設置していなかったら自爆
    public void deadBomberIfNeeded(Village village, int day) {
        Integer villageId = village.getVillageId();
        Abilities abilities = abilityService.selectAbilities(villageId);
        village.getVillagePlayers().filterAlive().filterBySkill(CDef.Skill.爆弾魔).list.forEach(bomber -> {
            if (!abilities.filterByType(CDef.AbilityType.爆弾設置).filterByChara(bomber.getCharaId()).list.isEmpty()) {
                return;
            }
            // 爆弾を設置していない
            String message = String.format("%sは、物足りないので自分の部屋を爆破した。", bomber.name());
            messageLogic.insertPublicAbilityMessage(villageId, day, message);
            villageService.dead(bomber, day, CDef.DeadReason.爆死);
        });
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

        messageLogic.saveIgnoreError(MessageEntity.privateSystemBuilder(dayChangeVillage.villageId, dayChangeVillage.day) //
                .content(message)
                .build());
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
        if (targetCharaId != null && this.getSelectableTarget(village, villagePlayer, day).list.stream()
                .noneMatch(vp -> vp.getCharaId().equals(targetCharaId))) {
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
