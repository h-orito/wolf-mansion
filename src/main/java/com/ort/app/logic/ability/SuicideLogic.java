package com.ort.app.logic.ability;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ort.app.logic.daychange.DayChangeLogicHelper;
import com.ort.app.logic.daychange.DayChangeVillage;
import com.ort.dbflute.allcommon.CDef;

@Component
public class SuicideLogic {

    @Autowired
    private DayChangeLogicHelper helper;

    // 後追い
    public void suicide(DayChangeVillage dayChangeVillage) {
        dayChangeVillage.vPlayers //
                .filterBy(vp -> vp.getTargetLover() != null) // 恋人がいて
                .filterBy(vp -> dayChangeVillage.isAlive(vp)) // 自分は生きていて
                .filterBy(vp -> !dayChangeVillage.isAlive(vp.getTargetLover())) // 恋人は死亡している
                        .list.forEach(vp -> {
                            helper.updateVillagePlayerDead(dayChangeVillage.day, vp, CDef.DeadReason.後追); // 死亡処理
                            dayChangeVillage.deadPlayers.add(vp, CDef.DeadReason.後追);

                            String message = String.format("%sは、絆に引きずられるように%sの後を追った。", vp.name(), vp.getTargetLover().name());
                            helper.insertMessage(dayChangeVillage, CDef.MessageType.公開システムメッセージ, message);
                        });
    }

}
