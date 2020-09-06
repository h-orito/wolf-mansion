package com.ort.app.logic;

import org.apache.commons.lang3.BooleanUtils;
import org.springframework.stereotype.Component;

import com.ort.app.web.dto.VillageInfo;

@Component
public class SkillRequestLogic {

    // 役職希望変更可能か
    public boolean isAvailableChangeSkillRequest(VillageInfo villageInfo) {
        // 現在プロローグでない場合NG
        if (!villageInfo.village.isVillageStatusCode募集中() && !villageInfo.village.isVillageStatusCode開始待ち()) {
            return false;
        }
        // この村に参戦していない場合NG
        if (!villageInfo.isParticipate() || villageInfo.isSpectator()) {
            return false;
        }
        // 役職希望有効でない場合NG
        if (!BooleanUtils.isTrue(villageInfo.settings.getIsPossibleSkillRequest())) {
            return false;
        }
        return true;
    }
}
