package com.ort.app.web.controller.assist;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ort.app.logic.FootstepLogic;
import com.ort.app.logic.VillageDispLogic;
import com.ort.app.web.dto.VillageInfo;
import com.ort.app.web.model.VillageFootstepDto;
import com.ort.app.web.model.VillageSituationDto;
import com.ort.dbflute.allcommon.CDef;
import com.ort.dbflute.exentity.Abilities;
import com.ort.dbflute.exentity.Ability;
import com.ort.dbflute.exentity.Footstep;
import com.ort.dbflute.exentity.VillagePlayers;

@Component
public class VillageSituationAssist {

    // ===================================================================================
    //                                                                           Attribute
    //                                                                           =========
    @Autowired
    private FootstepLogic footstepLogic;
    @Autowired
    private VillageDispLogic villageDispLogic;

    // ===================================================================================
    //                                                                             Execute
    //                                                                             =======
    public List<VillageFootstepDto> makeFootstepList(VillageInfo villageInfo) {
        List<VillageFootstepDto> footstepList = new ArrayList<>();
        for (int i = 2; i < villageInfo.days.list.size(); i++) {
            int day = i;
            List<Integer> livingPlayerRoomNumList = villageInfo.vPlayers //
                    .filterNotSpecatate() //
                    .filterBy(vp -> vp.isAliveWhen(day))
                    .map(vp -> vp.getRoomNumberWhen(day - 1));
            String message;
            List<Footstep> dayFootstepList = villageInfo.footsteps.filterYesteday(day).list;
            if (villageDispLogic.isDispSpoilerContent(villageInfo)) {
                message = footstepLogic.makeFootstepMessageOpenSkill( //
                        livingPlayerRoomNumList, //
                        villageInfo.vPlayers.filterNotSpecatate().filterNotDummy(villageInfo.settings.getDummyCharaId()).list,
                        dayFootstepList);
            } else {
                message = footstepLogic.makeFootstepMessageWithoutHeader(//
                        livingPlayerRoomNumList, //
                        dayFootstepList);
            }
            VillageFootstepDto footstep = new VillageFootstepDto();
            footstep.setDay(day);
            footstep.setFootstep(message);
            footstepList.add(footstep);
        }
        return footstepList;
    }

    public List<VillageSituationDto> makeSituationList(VillageInfo villageInfo) {
        // 2d???????????????????????????????????????
        List<VillageSituationDto> situationList = new ArrayList<>();
        VillagePlayers vPlayers = villageInfo.vPlayers.filterNotSpecatate();
        for (int i = 2; i <= villageInfo.day; i++) {
            final int day = i;
            VillageSituationDto situation = makeSituation(vPlayers, day);
            // ?????????????????????????????????????????????????????????
            if (villageDispLogic.isDispSpoilerContent(villageInfo)) {
                setSituationDetail(villageInfo, day, situation);
            }
            situationList.add(situation);
        }
        return situationList;
    }

    // ===================================================================================
    //                                                                        Assist Logic
    //                                                                        ============
    private VillageSituationDto makeSituation(VillagePlayers vPlayers, final int day) {
        VillageSituationDto situation = new VillageSituationDto();
        situation.setDay(day);
        VillagePlayers suddenly = vPlayers.filterBy(vp -> vp.existsDeadHistory(day, CDef.DeadReason.??????)).shuffled();
        situation.setSuddonlyDeathChara(suddenly.list.isEmpty() ? "??????" : String.join("???", suddenly.map(vp -> vp.shortName(day))));
        VillagePlayers miserable = vPlayers.filterBy(vp -> vp.existsMiserableDeadHistory(day)).shuffled();
        situation.setAttackedChara(miserable.list.isEmpty() ? "??????" : String.join("???", miserable.map(vp -> vp.shortName(day))));
        VillagePlayers executed = vPlayers.filterBy(vp -> vp.existsDeadHistory(day, CDef.DeadReason.??????));
        situation.setExecutedChara(executed.list.isEmpty() ? "??????" : String.join("???", executed.map(vp -> vp.shortName(day))));
        VillagePlayers suicide = vPlayers.filterBy(vp -> vp.existsDeadHistory(day, CDef.DeadReason.??????));
        situation.setSuicideChara(suicide.list.isEmpty() ? "??????" : String.join("???", suicide.map(vp -> {
            return String.format("%s", vp.shortName(day));
        })));
        VillagePlayers revival = vPlayers.filterBy(vp -> vp.existsReviveHistory(day)).shuffled();
        situation.setRevivalChara(revival.list.isEmpty() ? "??????" : String.join("???", revival.map(vp -> vp.shortName(day))));
        return situation;
    }

    private void setSituationDetail(VillageInfo villageInfo, final int day, VillageSituationDto situation) {
        VillagePlayers vPlayers = villageInfo.vPlayers.filterNotSpecatate();
        Abilities abilities = villageInfo.abilities.filterYesterday(day);

        List<String> divineList = abilities.filterByType(CDef.AbilityType.??????).map(ability -> makeAbilitySituationString(vPlayers, ability));
        situation.setDivinedChara(String.join("\n", divineList));

        List<String> guardList = abilities.filterByType(CDef.AbilityType.??????).map(ability -> makeAbilitySituationString(vPlayers, ability));
        situation.setGuardedChara(String.join("\n", guardList));

        abilities.filterByType(CDef.AbilityType.??????).list.stream().findFirst().map(ability -> {
            return makeAbilitySituationString(vPlayers, ability);
        }).ifPresent(attackString -> situation.setAttack(attackString));

        List<String> investigateList = abilities.filterByType(CDef.AbilityType.??????).map(ability -> {
            return String.format("%s ??? %s", vPlayers.findByCharaId(ability.getCharaId()).shortName(ability.getDay()),
                    ability.getTargetFootstep());
        });
        situation.setInvestigation(String.join("\n", investigateList));
    }

    private String makeAbilitySituationString(VillagePlayers villagePlayers, Ability ability) {
        return String.format("%s ??? %s", villagePlayers.findByCharaId(ability.getCharaId()).shortName(ability.getDay()),
                villagePlayers.findByCharaId(ability.getTargetCharaId()).shortName());
    }
}
