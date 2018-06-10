package com.ort.app.web.controller.assist;

import org.dbflute.optional.OptionalEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

import com.ort.app.web.controller.logic.DayChangeLogic;
import com.ort.app.web.controller.logic.MessageLogic;
import com.ort.app.web.exception.WerewolfMansionBusinessException;
import com.ort.app.web.form.VillageCommitForm;
import com.ort.dbflute.allcommon.CDef;
import com.ort.dbflute.exbhv.CommitBhv;
import com.ort.dbflute.exbhv.VillageBhv;
import com.ort.dbflute.exentity.Commit;
import com.ort.dbflute.exentity.Village;
import com.ort.dbflute.exentity.VillagePlayer;
import com.ort.fw.security.UserInfo;
import com.ort.fw.util.WerewolfMansionUserInfoUtil;

@Component
public class VillageCommitAssist {

    // ===================================================================================
    //                                                                           Attribute
    //                                                                           =========
    @Autowired
    private VillageAssist villageAssist;

    @Autowired
    private VillageBhv villageBhv;

    @Autowired
    private CommitBhv commitBhv;

    @Autowired
    private DayChangeLogic dayChangeLogic;

    @Autowired
    private MessageLogic messageLogic;

    // ===================================================================================
    //                                                                             Execute
    //                                                                             =======
    public String setCommit(Integer villageId, VillageCommitForm commitForm, BindingResult result, Model model) {
        // ログインしていなかったらNG
        UserInfo userInfo = WerewolfMansionUserInfoUtil.getUserInfo();
        if (result.hasErrors() || userInfo == null) {
            // 最新の日付を表示
            return villageAssist.setIndexModelAndReturnView(villageId, null, null, null, model);
        }
        VillagePlayer villagePlayer = villageAssist.selectVillagePlayer(villageId, userInfo, false).orElseThrow(() -> {
            return new IllegalArgumentException("セッション切れ？");
        });
        Village village = villageBhv.selectEntityWithDeletedCheck(cb -> {
            cb.setupSelect_VillageSettingsAsOne();
            cb.query().setVillageId_Equal(villageId);
        });
        // コミットできない設定だったり進行中でなかったら何もしない
        if (village.getVillageSettingsAsOne().get().isIsAvailableCommitFalse() || !village.isVillageStatusCode進行中()) {
            return villageAssist.setIndexModelAndReturnView(villageId, null, null, null, model);
        }
        // 自分がダミーだったり死亡していたり観戦だったら何もしない
        if (villagePlayer.isIsDeadTrue() || villagePlayer.isIsSpectatorTrue() || villagePlayer.getChara().get().isIsDummyTrue()) {
            return villageAssist.setIndexModelAndReturnView(villageId, null, null, null, model);
        }
        int day = villageAssist.selectLatestDay(villageId);
        Integer vPlayerId = villagePlayer.getVillagePlayerId();
        // 念のためselect
        OptionalEntity<Commit> optCommit = selectCommit(villageId, vPlayerId, day);
        if (commitForm.getCommit()) {
            // コミットする
            if (!optCommit.isPresent()) {
                insertCommit(villageId, vPlayerId, day);
                try {
                    messageLogic.insertMessage(villageId, day, CDef.MessageType.非公開システムメッセージ,
                            String.format("%sがコミットしました。", villagePlayer.getChara().get().getCharaName()));
                } catch (WerewolfMansionBusinessException e) {
                    // 最悪登録されなくても良いので放置
                }
                dayChangeLogic.dayChangeIfNeeded(villageId);
            }
        } else {
            if (optCommit.isPresent()) {
                deleteCommit(villageId, vPlayerId, day);
                try {
                    messageLogic.insertMessage(villageId, day, CDef.MessageType.非公開システムメッセージ,
                            String.format("%sがコミットを取り消しました。", villagePlayer.getChara().get().getCharaName()));
                } catch (WerewolfMansionBusinessException e) {
                    // 最悪登録されなくても良いので放置
                }

            }
        }
        return "redirect:/village/" + villageId + "#bottom";
    }

    // ===================================================================================
    //                                                                              Select
    //                                                                              ======
    private OptionalEntity<Commit> selectCommit(Integer villageId, Integer vPlayerId, int day) {
        OptionalEntity<Commit> optCommit = commitBhv.selectEntity(cb -> {
            cb.query().setVillageId_Equal(villageId);
            cb.query().setDay_Equal(day);
            cb.query().setVillagePlayerId_Equal(vPlayerId);
        });
        return optCommit;
    }

    // ===================================================================================
    //                                                                              Update
    //                                                                              ======
    private void insertCommit(Integer villageId, Integer vPlayerId, int day) {
        Commit entity = new Commit();
        entity.setVillageId(villageId);
        entity.setDay(day);
        entity.setVillagePlayerId(vPlayerId);
        commitBhv.insert(entity);
    }

    private void deleteCommit(Integer villageId, Integer vPlayerId, int day) {
        commitBhv.queryDelete(cb -> {
            cb.query().setVillageId_Equal(villageId);
            cb.query().setDay_Equal(day);
            cb.query().setVillagePlayerId_Equal(vPlayerId);
        });
    }
}
