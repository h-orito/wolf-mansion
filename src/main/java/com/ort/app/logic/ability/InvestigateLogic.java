package com.ort.app.logic.ability;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.stream.Collectors;

import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

import com.ort.app.datasource.AbilityService;
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
public class InvestigateLogic {

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

    // ===================================================================================
    //                                                                             Execute
    //                                                                             =======
    // 調査
    public void invastigate(DayChangeVillage dayChangeVillage) {
        int day = dayChangeVillage.day;
        if (day == 2) {
            return;
        }
        dayChangeVillage.alivePlayers().filterBySkill(CDef.Skill.探偵).list.forEach(detective -> {
            Optional<Ability> optInvestigate = dayChangeVillage.abilities //
                    .filterYesterday(day) //
                    .filterByType(CDef.AbilityType.捜査) //
                    .filterByChara(detective.getCharaId()).list.stream().findFirst();
            if (!optInvestigate.isPresent()) {
                return; // 能力セットしていない場合は何もしない
            }

            String targetFootstep = optInvestigate.get().getTargetFootstep();
            String skillName =
                    footstepLogic.getSkillByFootstep(dayChangeVillage.villageId, day - 2, targetFootstep, dayChangeVillage.vPlayers.list);
            String message = String.format("%sは、昨日響いた足音%sについて調査した。\n%sの足音を響かせたのは%sのようだ。", detective.name(), targetFootstep, targetFootstep,
                    skillName);
            messageLogic.saveIgnoreError(MessageEntity.systemBuilder(dayChangeVillage.villageId, dayChangeVillage.day) //
                    .messageType(CDef.MessageType.足音調査結果)
                    .content(message)
                    .villagePlayer(detective)
                    .build());
        });
    }

    public void insertDefaultInvestigate(Village village, int day) {
        Integer villageId = village.getVillageId();
        // 生存している探偵
        VillagePlayers aliveDetectives = village.getVillagePlayers() //
                .filterAlive() //
                .filterBySkill(CDef.Skill.探偵);
        if (aliveDetectives.list.isEmpty()) {
            return;
        }
        List<String> footstepList = getSelectableTarget(village, day);
        if (CollectionUtils.isEmpty(footstepList)) {
            return; // 候補の足音なし
        }
        aliveDetectives.list.forEach(detective -> {
            Integer detectiveCharaId = detective.getCharaId();
            // ランダムでセット
            Collections.shuffle(footstepList);
            String footstep = footstepList.get(0);
            abilityService.insertAbility(villageId, day, detectiveCharaId, null, footstep, CDef.AbilityType.捜査);
            insertAbilityMessage(village, day, detective, footstep, false);
        });
    }

    public void setAbility(//
            Village village, //
            VillagePlayer villagePlayer, //
            int day, //
            String footstep//
    ) {
        if (isInvalidInvestigationAbility(village, villagePlayer, day, footstep)) {
            return;
        }
        Integer villageId = village.getVillageId();
        abilityService.updateAbility(villageId, day, villagePlayer.getCharaId(), footstep, CDef.AbilityType.捜査);
        insertAbilityMessage(village, day, villagePlayer, footstep, false);
    }

    public List<String> getSelectableTarget(Village village, int day) {
        if (day <= 1) {
            return new ArrayList<>();
        }
        return footstepLogic.getFootstepList(village.getVillageId(), day);
    }

    // 能力行使履歴
    public List<String> makeSkillHistoryList(Village village, VillagePlayer villagePlayer, int day) {
        // 過去日の能力
        Abilities abilities = abilityService.selectAbilities(village.getVillageId()) //
                .filterPastDay(day) //
                .filterByType(CDef.AbilityType.捜査) //
                .filterByChara(villagePlayer.getCharaId())
                .sortedByDay();
        return abilities.list.stream().map(ability -> {
            return String.format("%d日目 %s を調査する", ability.getDay(), ability.getTargetFootstep());
        }).collect(Collectors.toList());
    }

    // ===================================================================================
    //                                                                        Assist Logic
    //                                                                        ============
    private boolean isInvalidInvestigationAbility(Village village, VillagePlayer villagePlayer, int day, String footstep) {
        if (day == 1) {
            return true;
        }
        if ("なし".equals(footstep) || footstep == null) {
            return true;
        }
        List<String> footstepList = getSelectableTarget(village, day);
        if (!footstepList.contains(footstep)) {
            return true;
        }
        return false;
    }

    private void insertAbilityMessage(Village village, int day, VillagePlayer villagePlayer, String footstep, boolean isDefault) {
        VillagePlayer myself = village.getVillagePlayers().findByCharaId(villagePlayer.getCharaId());
        String message = messageSource.getMessage("ability.detective.message",
                new String[] { myself.name(), footstep, isDefault ? "（自動設定）" : "" }, Locale.JAPAN);
        messageLogic.insertAbilityMessage(village.getVillageId(), day, message);
    }
}
