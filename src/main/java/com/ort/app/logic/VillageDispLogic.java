package com.ort.app.logic;

import org.apache.commons.lang3.BooleanUtils;
import org.springframework.stereotype.Component;

import com.ort.app.web.dto.VillageInfo;

/**
 * 村の初期表示の業務ロジック
 */
@Component
public class VillageDispLogic {

    // ===================================================================================
    //                                                                             Execute
    //                                                                             =======
    // 希望役職不可メッセージを表示するか
    public boolean isDispChangeRequestSkillNgMessage(VillageInfo villageInfo) {
        // 現在プロローグでない場合表示しない
        if (!villageInfo.village.isVillageStatusCode募集中() && !villageInfo.village.isVillageStatusCode開始待ち()) {
            return false;
        }
        // この村に参戦していない場合は表示しない
        if (!villageInfo.isParticipate() || villageInfo.isSpectator()) {
            return false;
        }
        // 役職希望有効でない場合に表示する
        if (!BooleanUtils.isTrue(villageInfo.settings.getIsPossibleSkillRequest())) {
            return true;
        }
        return false;
    }

    // ネタバレ要素を表示するか
    public boolean isDispSpoilerContent(VillageInfo villageInfo) {
        // エピっていたら表示
        if (villageInfo.village.isVillageStatusCodeエピローグ() || villageInfo.village.isVillageStatusCode終了()) {
            return true;
        }
        // 墓下役職公開で墓下か見物だったら表示
        if (villageInfo.settings.isIsOpenSkillInGraveTrue() && (villageInfo.isDead() || villageInfo.isSpectator())) {
            return true;
        }

        return false;
    }
}
