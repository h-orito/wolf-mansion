package com.ort.app.web.controller.assist;

import org.dbflute.optional.OptionalEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

import com.ort.app.datasource.VillageService;
import com.ort.app.logic.DayChangeLogic;
import com.ort.app.logic.MessageLogic;
import com.ort.app.logic.message.MessageEntity;
import com.ort.app.web.controller.assist.impl.VillageForms;
import com.ort.app.web.form.VillageCommitForm;
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
    private DayChangeLogic dayChangeLogic;
    @Autowired
    private MessageLogic messageLogic;
    @Autowired
    private VillageService villageService;
    @Autowired
    private VillageBhv villageBhv;
    @Autowired
    private CommitBhv commitBhv;

    // ===================================================================================
    //                                                                             Execute
    //                                                                             =======
    public String setCommit(Integer villageId, VillageCommitForm commitForm, BindingResult result, Model model) {
        // ログインしていなかったらNG
        UserInfo userInfo = WerewolfMansionUserInfoUtil.getUserInfo();
        if (result.hasErrors() || userInfo == null) {
            // 最新の日付を表示
            return villageAssist.setIndexModelAndReturnView(villageId, VillageForms.empty(), model);
        }
        VillagePlayer villagePlayer = villageService.selectVillagePlayer(villageId, userInfo, false).orElseThrow(() -> {
            return new IllegalArgumentException("セッション切れ？");
        });
        Village village = villageBhv.selectEntityWithDeletedCheck(cb -> {
            cb.setupSelect_VillageSettingsAsOne();
            cb.query().setVillageId_Equal(villageId);
        });
        // コミットできない設定だったり進行中でなかったら何もしない
        if (village.getVillageSettingsAsOne().get().isIsAvailableCommitFalse() || !village.isVillageStatusCode進行中()) {
            return villageAssist.setIndexModelAndReturnView(villageId, VillageForms.empty(), model);
        }
        // 自分がダミーだったり死亡していたり観戦だったら何もしない
        if (villagePlayer.isIsDeadTrue() || villagePlayer.isIsSpectatorTrue()
                || villagePlayer.getCharaId().intValue() == village.getVillageSettingsAsOne().get().getDummyCharaId()) {
            return villageAssist.setIndexModelAndReturnView(villageId, VillageForms.empty(), model);
        }
        int day = villageService.selectLatestDay(villageId);
        Integer vPlayerId = villagePlayer.getVillagePlayerId();
        // 念のためselect
        OptionalEntity<Commit> optCommit = selectCommit(villageId, vPlayerId, day);
        if (commitForm.getCommit()) {
            // コミットする
            if (!optCommit.isPresent()) {
                insertCommit(villageId, vPlayerId, day);
                messageLogic.saveIgnoreError(MessageEntity.privateSystemBuilder(villageId, day)
                        .content(String.format("%sがコミットしました。", villagePlayer.name()))
                        .build());
                dayChangeLogic.dayChangeIfNeeded(villageId);
            }
        } else {
            if (optCommit.isPresent()) {
                deleteCommit(villageId, vPlayerId, day);
                messageLogic.saveIgnoreError(MessageEntity.privateSystemBuilder(villageId, day)
                        .content(String.format("%sがコミットを取り消しました。", villagePlayer.name()))
                        .build());
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
