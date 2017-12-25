package com.ort.app.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ort.app.web.controller.assist.VillageAssist;
import com.ort.app.web.exception.WerewolfMansionBusinessException;
import com.ort.app.web.form.VillageGetMessageListForm;
import com.ort.app.web.form.VillageParticipateForm;
import com.ort.app.web.model.VillageMessageListResultContent;
import com.ort.fw.security.UserInfo;
import com.ort.fw.util.WerewolfMansionUserInfoUtil;

@Controller
public class VillageController {

    // ===================================================================================
    //                                                                           Attribute
    //                                                                           =========
    @Autowired
    private VillageAssist assist;

    // ===================================================================================
    //                                                                             Execute
    //                                                                             =======
    // 村最新日付初期表示
    @GetMapping("/village/{villageId}")
    private String villageIndex(@PathVariable Integer villageId, Model model) {
        // 最新の日付取得
        int day = assist.selectLatestDay(villageId);
        assist.setIndexModel(villageId, day, model);
        return "village";
    }

    // 発言取得
    @GetMapping("/village/getMessageList")
    @ResponseBody
    private VillageMessageListResultContent getDayMessageList(VillageGetMessageListForm form) {
        return assist.getMessageList(form);
    }

    // 参戦
    @PostMapping("/village/{villageId}/participate")
    private String participate(@PathVariable Integer villageId,
            @Validated @ModelAttribute("participateForm") VillageParticipateForm participateForm, BindingResult result, Model model) {
        if (result.hasErrors()) {
            // 最新の日付取得
            int day = assist.selectLatestDay(villageId);
            assist.setIndexModel(villageId, day, model);
            return "village";
        }
        // 既にそのキャラが参戦していたらNG
        try {
            assist.assertAlreadyParticipateChara(villageId, participateForm);
        } catch (WerewolfMansionBusinessException e) {
            model.addAttribute("participateErrorMessage", e.getMessage());
            // 最新の日付取得
            int day = assist.selectLatestDay(villageId);
            assist.setIndexModel(villageId, day, model);
            return "village";
        }
        // ログインしていなかったらNG
        UserInfo userInfo = WerewolfMansionUserInfoUtil.getUserInfo();
        if (userInfo == null) {
            // 最新の日付取得
            int day = assist.selectLatestDay(villageId);
            assist.setIndexModel(villageId, day, model);
            return "village";
        }
        // 入村
        assist.participate(villageId, participateForm, userInfo);

        return "redirect:/village/" + villageId;
    }
}
