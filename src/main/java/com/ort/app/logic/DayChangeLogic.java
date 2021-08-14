package com.ort.app.logic;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ort.app.datasource.VillageService;
import com.ort.app.logic.daychange.DayChangeLogicHelper;
import com.ort.app.logic.daychange.ProgressLogic;
import com.ort.app.logic.daychange.PrologueLogic;
import com.ort.app.web.dto.VillageInfo;
import com.ort.dbflute.allcommon.CDef;
import com.ort.dbflute.allcommon.CDef.VillageStatus;
import com.ort.dbflute.exentity.Village;
import com.ort.fw.util.WolfMansionDateUtil;

@Component
public class DayChangeLogic {

    // ===================================================================================
    //                                                                           Attribute
    //                                                                           =========
    @Autowired
    private PrologueLogic prologueLogic;
    @Autowired
    private ProgressLogic progressLogic;
    @Autowired
    private DayChangeLogicHelper dayChangeLogicHelper;
    @Autowired
    private VillageService villageService;

    // ===================================================================================
    //                                                                             Execute
    //                                                                             =======
    public void dayChangeIfNeeded(Integer villageId) {
        Village village = villageService.selectVillage(villageId, false, false);
        VillageInfo villageInfo = dayChangeLogicHelper.convertToVillageInfo(village);

        // プロローグで接続していない人を退村させる
        prologueLogic.leaveVillageIfNeeded(villageInfo);

        // 日付更新の必要がなければ終了
        LocalDateTime daychangeDatetime = villageInfo.days.latestDay().getDaychangeDatetime();
        if (!shouldChangeDay(villageInfo, daychangeDatetime)) {
            return;
        }

        // 最低開始人数を満たしていない
        if (isInsufficientVillagerNum(villageInfo)) {
            prologueLogic.extendOrCancelVillage(villageInfo, daychangeDatetime);
            return;
        }

        // 日付切り替え -----------------------------------------------------------
        int newDay = villageInfo.day + 1;
        // 日付追加
        addDay(villageInfo, daychangeDatetime, newDay);

        if (villageInfo.day == 0) { // プロ→1日目
            prologueLogic.startVillage(villageInfo, newDay);
        } else if (villageInfo.village.getVillageStatusCodeAsVillageStatus() == CDef.VillageStatus.エピローグ) {
            dayChangeLogicHelper.updateVillageStatus(villageId, CDef.VillageStatus.終了); // 終了
        } else {
            progressLogic.dayChange(villageId, newDay, villageInfo.vPlayers, villageInfo.settings);
        }
    }

    // ===================================================================================
    //                                                                        Assist Logic
    //                                                                        ============
    // 日付更新の必要があるか
    private boolean shouldChangeDay(VillageInfo vInfo, LocalDateTime nextDayChangeDatetime) {
        // 村が終了していたら必要なし
        VillageStatus villageStatus = vInfo.village.getVillageStatusCodeAsVillageStatus();
        if (villageStatus == CDef.VillageStatus.廃村 || villageStatus == CDef.VillageStatus.終了) {
            return false;
        }
        // 全員コミットしている
        if (allLivingPlayerCommited(vInfo)) {
            return true;
        }
        // 最新の村日付の更新時間を過ぎていない
        if (WolfMansionDateUtil.currentLocalDateTime().isBefore(nextDayChangeDatetime)) {
            return false;
        }
        return true;
    }

    // 全員コミットしている
    private boolean allLivingPlayerCommited(VillageInfo vInfo) {
        if (vInfo.settings.isIsAvailableCommitFalse()) { // コミットなし設定
            return false;
        }
        if (!vInfo.village.isVillageStatusCode進行中()) { // 進行中じゃなかったら対象外
            return false;
        }
        // 全員コミットしているか
        int commitNum = dayChangeLogicHelper.selectCommitNum(vInfo);
        int livingPersonNum = vInfo.vPlayers //
                .filterAlive()
                .filterNotSpecatate()
                .filterNotDummy(vInfo.settings).list.size();
        return commitNum == livingPersonNum;
    }

    // 参加者数が不足している
    private boolean isInsufficientVillagerNum(VillageInfo vInfo) {
        return vInfo.vPlayers.list.size() < vInfo.settings.getStartPersonMinNum();
    }

    // 日付追加
    private void addDay(VillageInfo villageInfo, LocalDateTime daychangeDatetime, int newDay) {
        Integer intervalSeconds = villageInfo.settings.getDayChangeIntervalSeconds();
        LocalDateTime nextDaychangeDatetime = daychangeDatetime.plusSeconds(intervalSeconds);
        dayChangeLogicHelper.insertVillageDayTransactional(villageInfo.villageId, newDay, nextDaychangeDatetime); // 村日付を追加
    }
}
