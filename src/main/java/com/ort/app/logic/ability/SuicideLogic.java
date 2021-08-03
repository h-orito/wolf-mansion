package com.ort.app.logic.ability;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ort.app.datasource.VillageService;
import com.ort.app.logic.MessageLogic;
import com.ort.app.logic.RoomLogic;
import com.ort.app.logic.daychange.DayChangeVillage;
import com.ort.app.logic.message.MessageEntity;
import com.ort.dbflute.allcommon.CDef;
import com.ort.dbflute.exentity.Village;
import com.ort.dbflute.exentity.VillagePlayer;

@Component
public class SuicideLogic {

    @Autowired
    private RoomLogic roomLogic;
    @Autowired
    private MessageLogic messageLogic;
    @Autowired
    private VillageService villageService;

    // 後追い
    public void suicide(DayChangeVillage dayChangeVillage) {
        Village village = villageService.selectVillage(dayChangeVillage.villageId, false, false);

        // 恋絆が数珠つなぎになっていることがあるので繰り返す
        while (findSuicideTarget(dayChangeVillage, village).isPresent()) {
            VillagePlayer target = findSuicideTarget(dayChangeVillage, village).get();
            Optional<VillagePlayer> optLoverSuicideTarget = findLoverSuicideTarget(dayChangeVillage);

            // 後追い
            villageService.dead(target, dayChangeVillage.day, CDef.DeadReason.後追);
            dayChangeVillage.deadPlayers.add(target, CDef.DeadReason.後追);

            if (optLoverSuicideTarget.isPresent() && optLoverSuicideTarget.get().getVillagePlayerId().equals(target.getVillagePlayerId())) {
                // 恋絆
                // 後追い対象
                VillagePlayer lover = target.getTargetLovers().filterBy(lov -> !dayChangeVillage.isAlive(lov)).getRandom();
                messageLogic.saveIgnoreError(MessageEntity.publicSystemBuilder(dayChangeVillage.villageId, dayChangeVillage.day) //
                        .content(String.format("%sは、絆に引きずられるように%sの後を追った。", target.name(), lover.name()))
                        .build());
            } else {
                // 壁殴り
                messageLogic.saveIgnoreError(MessageEntity.publicSystemBuilder(dayChangeVillage.villageId, dayChangeVillage.day) //
                        .content(String.format("%sは、壁殴りを代行する部屋がなくなってしまい、孤独死した。", target.name()))
                        .build());
            }
        }
    }

    private Optional<VillagePlayer> findSuicideTarget(DayChangeVillage dayChangeVillage, Village village) {
        // 恋絆
        Optional<VillagePlayer> optLoverSuicide = findLoverSuicideTarget(dayChangeVillage);
        if (optLoverSuicide.isPresent()) {
            return optLoverSuicide;
        }

        // 壁殴り代行
        return dayChangeVillage.vPlayers.filterBy(vp -> dayChangeVillage.isAlive(vp)).filterBySkill(CDef.Skill.壁殴り代行).filterBy(puncher -> {
            // 四方の部屋の人が全員死亡していたら後追い対象
            List<Integer> candidateRoomNumberList = roomLogic.detectWasdRoomNumber(puncher.getRoomNumber(), village.getRoomSizeWidth());
            return dayChangeVillage.vPlayers.filterBy(vp -> candidateRoomNumberList.contains(vp.getRoomNumber()))
                    .filterBy(vp -> dayChangeVillage.isAlive(vp))
                    .isEmpty();
        }).list.stream().findFirst();
    }

    // 恋絆
    private Optional<VillagePlayer> findLoverSuicideTarget(DayChangeVillage dayChangeVillage) {
        return dayChangeVillage.vPlayers //
                .filterBy(vp -> vp.hasLover()) // 恋人がいて
                .filterBy(vp -> dayChangeVillage.isAlive(vp)) // 自分は生きていて
                .filterBy(vp -> {
                    // 恋人のいずれかが死亡している
                    return vp.getTargetLovers().list.stream().anyMatch(lover -> !dayChangeVillage.isAlive(lover));
                }).list.stream().findFirst();
    }
}
