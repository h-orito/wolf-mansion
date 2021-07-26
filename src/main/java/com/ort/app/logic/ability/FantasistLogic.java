/**
 * 
 */
package com.ort.app.logic.ability;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ort.app.logic.FootstepLogic;
import com.ort.dbflute.allcommon.CDef;
import com.ort.dbflute.exentity.Village;

@Component
public class FantasistLogic {

    @Autowired
    private FootstepLogic footstepLogic;

    public void insertDefaultFootstep(Village village, int newDay) {
        Integer villageId = village.getVillageId();
        village.getVillagePlayers() //
                .filterAlive() //
                .filterBy(vp -> vp.getSkillCodeAsSkill() == CDef.Skill.妄想癖).list.forEach(fantasist -> {
                    String footStep = fantasist.getRoomNumber().toString();
                    footstepLogic.insertFootStep(villageId, newDay, fantasist.getCharaId(), footStep);
                });
    }
}
