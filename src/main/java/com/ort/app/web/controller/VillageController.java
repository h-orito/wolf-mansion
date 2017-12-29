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
import com.ort.app.web.controller.logic.DayChangeLogic;
import com.ort.app.web.controller.logic.MessageLogic;
import com.ort.app.web.exception.WerewolfMansionBusinessException;
import com.ort.app.web.form.VillageGetMessageListForm;
import com.ort.app.web.form.VillageParticipateForm;
import com.ort.app.web.form.VillageSayForm;
import com.ort.app.web.model.VillageMessageListResultContent;
import com.ort.dbflute.allcommon.CDef;
import com.ort.dbflute.allcommon.CDef.MessageType;
import com.ort.dbflute.exentity.VillagePlayer;
import com.ort.fw.security.UserInfo;
import com.ort.fw.util.WerewolfMansionUserInfoUtil;

@Controller
public class VillageController {

    // ===================================================================================
    //                                                                           Attribute
    //                                                                           =========
    @Autowired
    private VillageAssist assist;

    @Autowired
    DayChangeLogic dayChangeLogic;

    @Autowired
    MessageLogic messageLogic;

    // ===================================================================================
    //                                                                             Execute
    //                                                                             =======
    // 村最新日付初期表示
    @GetMapping("/village/{villageId}")
    private String villageIndex(@PathVariable Integer villageId, Model model) {
        // 更新時間が過ぎていたら日付更新
        dayChangeLogic.dayChangeIfNeeded(villageId);

        // 最新の日付を表示
        return setIndexModelAndReturnView(villageId, null, model);
    }

    // 村最新日付初期表示
    @GetMapping("/village/{villageId}/day/{day}")
    private String villageDayIndex(@PathVariable Integer villageId, @PathVariable Integer day, Model model) {
        assist.setIndexModel(villageId, day, null, model);
        return "village";
    }

    // 発言取得
    @GetMapping("/village/getMessageList")
    @ResponseBody
    private VillageMessageListResultContent getDayMessageList(VillageGetMessageListForm form) {
        // 更新時間が過ぎていたら日付更新
        dayChangeLogic.dayChangeIfNeeded(form.getVillageId());

        return assist.getMessageList(form);
    }

    // 参戦
    @PostMapping("/village/{villageId}/participate")
    private String participate(@PathVariable Integer villageId,
            @Validated @ModelAttribute("participateForm") VillageParticipateForm participateForm, BindingResult result, Model model) {
        // ログインしていなかったらNG
        UserInfo userInfo = WerewolfMansionUserInfoUtil.getUserInfo();
        if (result.hasErrors() || userInfo == null) {
            // 最新の日付を表示
            return setIndexModelAndReturnView(villageId, null, model);
        }
        // 既にそのキャラが参戦していたらNG
        try {
            assist.assertAlreadyParticipateChara(villageId, participateForm);
        } catch (WerewolfMansionBusinessException e) {
            model.addAttribute("participateErrorMessage", e.getMessage());
            return setIndexModelAndReturnView(villageId, null, model);
        }

        // 入村
        assist.participate(villageId, participateForm, userInfo);

        return "redirect:/village/" + villageId;
    }

    // 発言確認画面へ
    @PostMapping("/village/{villageId}/confirm")
    private String confirm(@PathVariable Integer villageId, @Validated @ModelAttribute("sayForm") VillageSayForm sayForm,
            BindingResult result, Model model) {
        // ログインしていなかったらNG
        UserInfo userInfo = WerewolfMansionUserInfoUtil.getUserInfo();
        if (result.hasErrors() || userInfo == null) {
            // 最新の日付を表示
            return setIndexModelAndReturnView(villageId, sayForm, model);
        }
        // 発言権利がなかったらNG
        if (!assist.isAvailableSay(villageId, userInfo, sayForm)) {
            // 最新の日付を表示
            return setIndexModelAndReturnView(villageId, sayForm, model);
        }

        model.addAttribute("villageId", villageId);
        assist.setConfirmModel(villageId, model);
        // 発言確認画面へ
        return "say-confirm";
    }

    // 発言する
    @PostMapping("/village/{villageId}/say")
    private String say(@PathVariable Integer villageId, @Validated @ModelAttribute("sayForm") VillageSayForm sayForm, BindingResult result,
            Model model) {
        // ログインしていなかったらNG
        UserInfo userInfo = WerewolfMansionUserInfoUtil.getUserInfo();
        if (result.hasErrors() || userInfo == null) {
            // 最新の日付を表示
            return setIndexModelAndReturnView(villageId, sayForm, model);
        }
        // 発言権利がなかったらNG
        if (!assist.isAvailableSay(villageId, userInfo, sayForm)) {
            // 最新の日付を表示
            return setIndexModelAndReturnView(villageId, sayForm, model);
        }

        int day = assist.selectLatestDay(villageId);
        VillagePlayer villagePlayer = assist.selectVillagePlayer(villageId, userInfo).orElseThrow(() -> {
            return new IllegalArgumentException("セッション切れ？");
        });
        MessageType type = CDef.MessageType.codeOf(sayForm.getMessageType());
        if (type == null) {
            throw new IllegalArgumentException("発言種別改ざん");
        }

        messageLogic.insertMessage(villageId, day, type, sayForm.getMessage(), villagePlayer.getVillagePlayerId());
        return "redirect:/village/" + villageId;
    }

    // ===================================================================================
    //                                                                        Assist Logic
    //                                                                        ============
    private String setIndexModelAndReturnView(Integer villageId, VillageSayForm sayForm, Model model) {
        // 最新の日付取得
        int day = assist.selectLatestDay(villageId);
        assist.setIndexModel(villageId, day, sayForm, model);
        return "village";
    }
}
