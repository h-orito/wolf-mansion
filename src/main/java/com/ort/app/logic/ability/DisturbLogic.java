package com.ort.app.logic.ability;

import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;
import org.thymeleaf.util.StringUtils;

import com.ort.app.datasource.FootstepService;
import com.ort.app.logic.FootstepLogic;
import com.ort.app.logic.MessageLogic;
import com.ort.dbflute.allcommon.CDef;
import com.ort.dbflute.exentity.Footsteps;
import com.ort.dbflute.exentity.Village;
import com.ort.dbflute.exentity.VillagePlayer;
import com.ort.dbflute.exentity.VillagePlayers;

@Component
public class DisturbLogic {

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
    private FootstepService footstepService;

    // ===================================================================================
    //                                                                             Execute
    //                                                                             =======
    public void insertDefaultFootstep(Village village, int newDay) {
        // 妖狐狂人
        VillagePlayers disturbPlayers = village.getVillagePlayers() //
                .filterAlive() //
                .filterBy(vp -> vp.getSkillCodeAsSkill().isHasDisturbAbility());
        for (VillagePlayer vp : disturbPlayers.list) {
            Integer charaId = vp.getCharaId();
            // 足音なしセット
            footstepLogic.insertFootStep(village.getVillageId(), newDay, charaId, "なし");
            insertFootstepMessage(village, newDay, charaId, "なし", true);
        }
    }

    public void setAbility(//
            Village village, //
            VillagePlayer villagePlayer, //
            int day, //
            String footstep//
    ) {
        if (isInvalidDisturbAbility(village, footstep)) {
            return;
        }
        Integer villageId = village.getVillageId();
        Integer charaId = villagePlayer.getCharaId();
        footstepService.deleteFootstep(villageId, day, charaId);
        footstepService.insertFootstep(villageId, day, charaId, footstep);
        insertFootstepMessage(village, day, charaId, footstep, false);
    }

    // 能力行使履歴
    public List<String> makeSkillHistoryList(Village village, VillagePlayer villagePlayer, int day) {
        // 足音
        Footsteps footsteps = footstepService.selectFootsteps(village.getVillageId()) //
                .filterByChara(villagePlayer.getCharaId()) //
                .filterPastDay(day)
                .sortedByDay();
        return footsteps.list.stream().map(f -> {
            String footstep = f.getFootstepRoomNumbers();
            return String.format("%d日目 %s", //
                    f.getDay(), //
                    StringUtils.isEmpty(footstep) || "なし".equals(footstep) ? "徘徊しない" : footstep + "を徘徊する");
        }).collect(Collectors.toList());
    }

    // ===================================================================================
    //                                                                        Assist Logic
    //                                                                        ============
    private boolean isInvalidDisturbAbility(Village village, String footstep) {
        if (footstep == null) {
            return true;
        }
        // 足音が直線でなかったらNG
        if (!footstepLogic.isFootstepStraight(village, footstep)) {
            return true;
        }
        return false;
    }

    private void insertFootstepMessage(Village village, int day, Integer charaId, String footstep, boolean isDefault) {
        VillagePlayer myself = village.getVillagePlayers().findByCharaId(charaId);
        String message = messageSource.getMessage("ability.foxmadman.message",
                new String[] { myself.name(), footstep, isDefault ? "（自動設定）" : "" }, Locale.JAPAN);
        messageLogic.insertMessageIgnoreError(village.getVillageId(), day, CDef.MessageType.非公開システムメッセージ, message);
    }
}
