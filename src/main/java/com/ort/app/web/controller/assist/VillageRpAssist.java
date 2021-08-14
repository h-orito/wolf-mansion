package com.ort.app.web.controller.assist;

import com.ort.app.datasource.VillageService;
import com.ort.app.logic.MessageLogic;
import com.ort.app.logic.message.MessageEntity;
import com.ort.app.web.controller.assist.impl.VillageForms;
import com.ort.app.web.exception.WolfMansionBusinessException;
import com.ort.app.web.form.VillageChangeNameForm;
import com.ort.app.web.form.VillageMemoForm;
import com.ort.dbflute.exentity.VillagePlayer;
import com.ort.fw.security.UserInfo;
import com.ort.fw.util.WolfMansionUserInfoUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

@Component
public class VillageRpAssist {

    // ===================================================================================
    //                                                                           Attribute
    //                                                                           =========
    @Autowired
    private VillageAssist villageAssist;
    @Autowired
    private MessageLogic messageLogic;
    @Autowired
    private VillageService villageService;

    // ===================================================================================
    //                                                                             Execute
    //                                                                             =======
    public String changeName(Integer villageId, VillageChangeNameForm form, BindingResult result, Model model) {
        // ログインしていなかったらNG
        UserInfo userInfo = WolfMansionUserInfoUtil.getUserInfo();
        if (result.hasErrors() || userInfo == null) {
            // 最新の日付を表示
            return villageAssist.setIndexModelAndReturnView(villageId, new VillageForms.Builder().changeNameForm(form).build(), model);
        }
        VillagePlayer villagePlayer = villageService.selectVillagePlayer(villageId, userInfo, true).orElseThrow(() -> {
            return new IllegalArgumentException("セッション切れ？");
        });

        // 登録
        try {
            villageService.changeName(villagePlayer, form.getName(), form.getShortName());
            VillagePlayer afterVPlayer = villageService.selectVillagePlayer(villageId, userInfo, true).get();
            int day = villageService.selectLatestDay(villageId);
            messageLogic.save(MessageEntity.publicSystemBuilder(villageId, day) //
                    .content(String.format("名前を変更しました。\n%s → %s", villagePlayer.name(), afterVPlayer.name()))
                    .build());
        } catch (WolfMansionBusinessException e) {
            model.addAttribute("changeNameErrorMessage", e.getMessage());
        }

        // 最新の日付を表示
        return "redirect:/village/" + villageId + "#bottom";
    }

    public String memo(Integer villageId, VillageMemoForm form, BindingResult result, Model model) {
        // ログインしていなかったらNG
        UserInfo userInfo = WolfMansionUserInfoUtil.getUserInfo();
        if (result.hasErrors() || userInfo == null) {
            // 最新の日付を表示
            return villageAssist.setIndexModelAndReturnView(villageId, new VillageForms.Builder().memoForm(form).build(), model);
        }
        VillagePlayer villagePlayer = villageService.selectVillagePlayer(villageId, userInfo, true).orElseThrow(() -> {
            return new IllegalArgumentException("セッション切れ？");
        });

        // 登録
        villageService.memo(villagePlayer, form.getMemo());

        // 最新の日付を表示
        return "redirect:/village/" + villageId + "#bottom";
    }
}
