package com.ort.app.logic.ability;

import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.stream.Collectors;

import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

import com.ort.app.datasource.AbilityService;
import com.ort.app.logic.MessageLogic;
import com.ort.app.logic.RoomLogic;
import com.ort.app.logic.daychange.DayChangeVillage;
import com.ort.app.logic.message.MessageEntity;
import com.ort.dbflute.allcommon.CDef;
import com.ort.dbflute.exentity.Abilities;
import com.ort.dbflute.exentity.Ability;
import com.ort.dbflute.exentity.Village;
import com.ort.dbflute.exentity.VillagePlayer;
import com.ort.dbflute.exentity.VillagePlayers;

@Component
public class WallPunchLogic {

    // ===================================================================================
    //                                                                           Attribute
    //                                                                           =========
    @Autowired
    private MessageSource messageSource;
    @Autowired
    private MessageLogic messageLogic;
    @Autowired
    private RoomLogic roomLogic;
    @Autowired
    private AbilityService abilityService;

    // ===================================================================================
    //                                                                             Execute
    //                                                                             =======
    // 壁殴り
    public void wallPunch(DayChangeVillage dayChangeVillage) {
        int day = dayChangeVillage.day;
        if (day == 2) {
            return;
        }

        List<VillagePlayer> livingPuncherList = dayChangeVillage //
                .alivePlayers() // 死亡していない
                .filterBySkill(CDef.Skill.壁殴り代行).list;
        if (CollectionUtils.isEmpty(livingPuncherList)) {
            return; // 既に死亡している場合は何もしない
        }

        livingPuncherList.forEach(puncher -> {
            Optional<Ability> optPunch = dayChangeVillage.abilities //
                    .filterYesterday(day) //
                    .filterByType(CDef.AbilityType.壁殴り) //
                    .filterByChara(puncher.getCharaId()).list.stream().findFirst();
            if (!optPunch.isPresent()) {
                return; // 能力セットしていない場合は何もしない
            }
            VillagePlayer targetPlayer = dayChangeVillage.vPlayers.findByCharaId(optPunch.get().getTargetCharaId());
            messageLogic.saveIgnoreError(MessageEntity.privateSystemBuilder(dayChangeVillage.villageId, dayChangeVillage.day) //
                    .content(String.format("%sは、%sの部屋の壁を殴っている。", puncher.name(), targetPlayer.name()))
                    .build());
            dayChangeVillage.guardedPlayers.add(targetPlayer);
        });
    }

    // 日付変更時のデフォルトセット
    public void insertDefaultWallPunch(Village village, int newDay) {
        Integer villageId = village.getVillageId();
        // 生存している壁殴り
        VillagePlayers aliveHunters = village.getVillagePlayers() //
                .filterAlive() //
                .filterBy(vp -> vp.getSkillCodeAsSkill() == CDef.Skill.壁殴り代行);
        if (aliveHunters.list.isEmpty()) {
            return;
        }
        aliveHunters.list.forEach(puncher -> {
            Integer hunterCharaId = puncher.getCharaId();
            // 壁殴りされる人(生存者の中の誰か）
            VillagePlayers candidates = getSelectableTarget(village, newDay, puncher);
            if (candidates.isEmpty()) {
                return;
            }
            Integer targetCharaId = candidates.getRandom().getCharaId();
            abilityService.insertAbility(villageId, newDay, hunterCharaId, targetCharaId, null, CDef.AbilityType.壁殴り);
            insertAbilityMessage(village, newDay, hunterCharaId, targetCharaId, true);
        });
    }

    // 能力セット
    public void setAbility(//
            Village village, //
            VillagePlayer villagePlayer, //
            int day, //
            Integer targetCharaId) {
        if (isInvalidPuncherAbility(village, villagePlayer, day, targetCharaId)) {
            return;
        }
        Integer villageId = village.getVillageId();
        Integer charaId = villagePlayer.getCharaId();
        abilityService.updateAbility(villageId, day, charaId, targetCharaId, CDef.AbilityType.壁殴り);
        insertAbilityMessage(village, day, charaId, targetCharaId, false);
    }

    public VillagePlayers getSelectableTarget(Village village, int day, VillagePlayer puncher) {
        if (day <= 1) {
            return null;
        }
        // 自分の部屋の上下左右の部屋の人
        List<Integer> candidateRoomNumberList = roomLogic.detectWasdRoomNumber(puncher.getRoomNumber(), village.getRoomSizeWidth());
        return village.getVillagePlayers() //
                .filterAlive() //
                .filterNotSpecatate()
                .filterBy(vp -> candidateRoomNumberList.contains(vp.getRoomNumber()))
                .sortedByRoomNumber();
    }

    // 能力行使履歴
    public List<String> makeSkillHistoryList(Village village, VillagePlayer villagePlayer, int day) {
        // 過去日の能力
        Abilities abilities = abilityService.selectAbilities(village.getVillageId()) //
                .filterPastDay(day) //
                .filterByType(CDef.AbilityType.壁殴り) //
                .filterByChara(villagePlayer.getCharaId())
                .sortedByDay();

        return abilities.list.stream().map(ability -> {
            Integer abilityDay = ability.getDay();
            VillagePlayer target = village.getVillagePlayers().findByCharaId(ability.getTargetCharaId());
            return String.format("%d日目 %s の部屋の壁を殴る", abilityDay, target.name(abilityDay));
        }).collect(Collectors.toList());
    }

    // ===================================================================================
    //                                                                        Assist Logic
    //                                                                        ============
    private boolean isInvalidPuncherAbility(Village village, VillagePlayer villagePlayer, int day, Integer targetCharaId) {
        if (targetCharaId == null) {
            return true;
        }
        if (villagePlayer.isIsDeadTrue()) {
            return true;
        }
        if (getSelectableTarget(village, day, villagePlayer).list.stream().noneMatch(vp -> vp.getCharaId().equals(targetCharaId))) {
            return true;
        }
        if (day == 1) {
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
        String message = messageSource.getMessage("ability.wallpunch.message",
                new String[] { chara.name(), target.name(), isDefault ? "（自動設定）" : "" }, Locale.JAPAN);
        messageLogic.insertAbilityMessage(village.getVillageId(), day, message);
    }
}
