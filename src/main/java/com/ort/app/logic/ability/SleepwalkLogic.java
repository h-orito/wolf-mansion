/**
 * 
 */
package com.ort.app.logic.ability;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ort.app.logic.FootstepLogic;
import com.ort.dbflute.allcommon.CDef;
import com.ort.dbflute.exentity.Village;
import com.ort.dbflute.exentity.VillagePlayers;

@Component
public class SleepwalkLogic {

    @Autowired
    private FootstepLogic footstepLogic;

    public void insertDefaultFootstep(Village village, int newDay) {
        Integer villageId = village.getVillageId();
        village.getVillagePlayers() //
                .filterAlive() //
                .filterBy(vp -> vp.getSkillCodeAsSkill() == CDef.Skill.夢遊病者).list.forEach(fantasist -> {
                    Integer charaId = fantasist.getCharaId();
                    VillagePlayers villagePlayers = new VillagePlayers(village.getVillagePlayerList());
                    Integer targetCharaId = villagePlayers.filterNot(fantasist).getRandom().getCharaId();
                    List<String> footstepList =
                            footstepLogic.getFootstepCandidateList(village.getVillageId(), fantasist, newDay, charaId, targetCharaId);
                    Collections.shuffle(footstepList);
                    footstepLogic.insertFootStep(villageId, newDay, charaId, footstepList.get(0));
                });
    }
}
