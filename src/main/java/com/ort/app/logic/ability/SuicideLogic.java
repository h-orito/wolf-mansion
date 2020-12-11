package com.ort.app.logic.ability;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ort.app.datasource.VillageService;
import com.ort.app.logic.daychange.DayChangeLogicHelper;
import com.ort.app.logic.daychange.DayChangeVillage;
import com.ort.dbflute.allcommon.CDef;
import com.ort.dbflute.exentity.VillagePlayer;

@Component
public class SuicideLogic {

    @Autowired
    private DayChangeLogicHelper helper;
    @Autowired
    private VillageService villageService;

    // 後追い
    public void suicide(DayChangeVillage dayChangeVillage) {
        // 恋絆が数珠つなぎになっていることがあるので繰り返す
        while (findFirstSuicideTarget(dayChangeVillage).isPresent()) {
            VillagePlayer target = findFirstSuicideTarget(dayChangeVillage).get();
            // 後追い
            villageService.dead(target, dayChangeVillage.day, CDef.DeadReason.後追);
            dayChangeVillage.deadPlayers.add(target, CDef.DeadReason.後追);
            // 後追い対象
            VillagePlayer lover = target.getTargetLovers().filterBy(lov -> !dayChangeVillage.isAlive(lov)).getRandom();

            String message = String.format("%sは、絆に引きずられるように%sの後を追った。", target.name(), lover.name());
            helper.insertMessage(dayChangeVillage, CDef.MessageType.公開システムメッセージ, message);
        }
    }

    private Optional<VillagePlayer> findFirstSuicideTarget(DayChangeVillage dayChangeVillage) {
        return dayChangeVillage.vPlayers //
                .filterBy(vp -> vp.hasLover()) // 恋人がいて
                .filterBy(vp -> dayChangeVillage.isAlive(vp)) // 自分は生きていて
                .filterBy(vp -> {
                    // 恋人のいずれかが死亡している
                    return vp.getTargetLovers().list.stream().anyMatch(lover -> !dayChangeVillage.isAlive(lover));
                }).list.stream().findFirst();
    }
}
