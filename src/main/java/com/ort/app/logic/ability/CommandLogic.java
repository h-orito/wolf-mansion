package com.ort.app.logic.ability;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

import com.ort.app.datasource.AbilityService;
import com.ort.app.logic.MessageLogic;
import com.ort.dbflute.allcommon.CDef;
import com.ort.dbflute.exentity.Abilities;
import com.ort.dbflute.exentity.Village;
import com.ort.dbflute.exentity.VillagePlayer;
import com.ort.dbflute.exentity.VillagePlayers;

@Component
public class CommandLogic {

    @Autowired
    private AbilityService abilityService;
    @Autowired
    private MessageLogic messageLogic;
    @Autowired
    private MessageSource messageSource;

    public void setAbility(Village village, VillagePlayer villagePlayer, int day, Integer targetCharaId) {
        if (isInvalidCommandAbility(village, villagePlayer, day, targetCharaId)) {
            return;
        }
        if (targetCharaId == null) {
            return;
        }
        Integer villageId = village.getVillageId();
        Integer charaId = villagePlayer.getCharaId();
        abilityService.updateAbility(villageId, day, charaId, targetCharaId, CDef.AbilityType.指揮);
        insertAbilityMessage(village, day, charaId, targetCharaId);
    }

    private boolean isInvalidCommandAbility(Village village, VillagePlayer villagePlayer, int day, Integer targetCharaId) {
        if (villagePlayer.isIsDeadTrue()) {
            return true;
        }
        if (getSelectableTarget(village, villagePlayer, day).list.stream().noneMatch(vp -> vp.getCharaId().equals(targetCharaId))) {
            return true; // 選べない相手
        }
        return false;
    }

    public VillagePlayers getSelectableTarget(Village village, VillagePlayer villagePlayer, int day) {
        Abilities abilities = abilityService.selectAbilities(village.getVillageId());
        // 当日に能力行使していたらもう使えない
        if (!abilities.filterByDay(day).filterByType(CDef.AbilityType.指揮).filterByChara(villagePlayer.getCharaId()).list.isEmpty()) {
            return new VillagePlayers(new ArrayList<>());
        }
        return village.getVillagePlayers() //
                .filterAlive() //
                .filterNotSpecatate()
                .sortedByRoomNumber();
    }

    private void insertAbilityMessage( //
            Village village, //
            int day, //
            Integer charaId, //
            Integer targetCharaId //
    ) {
        VillagePlayer chara = village.getVillagePlayers().findByCharaId(charaId);
        VillagePlayer target = village.getVillagePlayers().findByCharaId(targetCharaId);
        String message = messageSource.getMessage("ability.command.message", new String[] { chara.name(), target.name() }, Locale.JAPAN);
        messageLogic.insertPublicAbilityMessage(village.getVillageId(), day, message);
    }

    public List<String> makeSkillHistoryList(Village village, VillagePlayer villagePlayer, int day) {
        // 過去日の能力
        Abilities abilities = abilityService.selectAbilities(village.getVillageId()) //
                .filterByType(CDef.AbilityType.指揮) //
                .filterByChara(villagePlayer.getCharaId())
                .sortedByDay();

        return abilities.list.stream().map(ability -> {
            VillagePlayer target = village.getVillagePlayers().findByCharaId(ability.getTargetCharaId());
            Integer abilityDay = ability.getDay();
            return String.format("%d日目 %s を指差す", abilityDay, target.name(abilityDay));
        }).collect(Collectors.toList());
    }
}
