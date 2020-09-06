package com.ort.app.logic;

import org.springframework.stereotype.Component;

import com.ort.app.web.dto.VillageInfo;

@Component
public class CommitLogic {

    // コミット可能か
    public Boolean isAvailableCommit(VillageInfo villageInfo) {
        // コミットできない設定の場合はNG
        if (villageInfo.settings.isIsAvailableCommitFalse()) {
            return false;
        }
        // 最新日以外はNG
        if (!villageInfo.isLatestDay()) {
            return false;
        }
        // 参加していなかったり生存していない場合はNG
        if (!villageInfo.isParticipate() || villageInfo.isSpectator() || villageInfo.isDead()) {
            return false;
        }
        // 進行中以外はNG
        if (!villageInfo.village.isVillageStatusCode進行中()) {
            return false;
        }
        return true;
    }

}
