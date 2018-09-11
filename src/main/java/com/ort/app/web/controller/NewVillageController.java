package com.ort.app.web.controller;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.ort.app.web.controller.assist.NewVillageAssist;
import com.ort.app.web.controller.logic.VillageParticipateLogic;
import com.ort.app.web.exception.WerewolfMansionBusinessException;
import com.ort.app.web.form.NewVillageForm;
import com.ort.app.web.form.validator.NewVillageFormValidator;
import com.ort.dbflute.exbhv.CharaBhv;
import com.ort.dbflute.exbhv.CharaGroupBhv;
import com.ort.dbflute.exentity.Chara;
import com.ort.dbflute.exentity.CharaGroup;
import com.ort.dbflute.exentity.Village;
import com.ort.fw.security.UserInfo;
import com.ort.fw.util.WerewolfMansionUserInfoUtil;

@Controller
public class NewVillageController {

    // ===================================================================================
    //                                                                           Attribute
    //                                                                           =========
    @Autowired
    private CharaGroupBhv charaGroupBhv;

    @Autowired
    private CharaBhv charaBhv;

    @Autowired
    private NewVillageFormValidator newVillageFormValidator;

    @Autowired
    private NewVillageAssist newVillageAssist;

    @Autowired
    private VillageParticipateLogic participateLogic;

    @InitBinder("villageForm")
    public void initBinder(WebDataBinder binder) {
        binder.addValidators(newVillageFormValidator);
    }

    // ===================================================================================
    //                                                                             Execute
    //                                                                             =======
    // 新規村作成初期表示
    @GetMapping("/new-village")
    private String newVillageIndex(Model model) {
        newVillageAssist.setIndexModel(new NewVillageForm(), model);
        return "new-village";
    }

    // 新規村作成確認画面
    @PostMapping("/new-village/confirm")
    private String newVillageConfirm(@Validated @ModelAttribute("villageForm") NewVillageForm villageForm, BindingResult bindingResult,
            Model model) {
        if (bindingResult.hasErrors()) {
            newVillageAssist.setIndexModel(villageForm, model);
            return "new-village";
        }
        // ログインしていなかったらNG
        UserInfo userInfo = WerewolfMansionUserInfoUtil.getUserInfo();
        if (userInfo == null) {
            model.addAttribute("errorMessage", "ログインし直してください。");
            newVillageAssist.setIndexModel(villageForm, model);
            return "new-village";
        }
        // 決着のついていない参加していたり建てた村が終了していなかったらNG
        if (participateLogic.isParticipatingOrCreatingVillage()) {
            model.addAttribute("errorMessage", "参加/村建てした村の決着がつくまでは村を建てられません。");
            newVillageAssist.setIndexModel(villageForm, model);
            return "new-village";
        }
        // キャラセット名を取得
        CharaGroup charaGroup =
                charaGroupBhv.selectEntityWithDeletedCheck(cb -> cb.query().setCharaGroupId_Equal(villageForm.getCharacterSetId()));
        String charaGroupName = charaGroup.getCharaGroupName();
        model.addAttribute("characterSetName", charaGroupName);
        // キャラ画像URL
        Chara chara = charaBhv.selectEntityWithDeletedCheck(cb -> {
            cb.query().setIsDummy_Equal_True();
            cb.query().setCharaGroupId_Equal(charaGroup.getCharaGroupId());
        });
        model.addAttribute("characterImgUrl", chara.getCharaImgUrl());
        model.addAttribute("characterImgWidth", chara.getDisplayWidth());
        model.addAttribute("characterImgHeight", chara.getDisplayHeight());

        // 時間と日時の表示
        model.addAttribute("startDateTime", makeStartDateTime(villageForm));
        model.addAttribute("interval", makeInterval(villageForm));

        return "new-village-confirm";
    }

    // 新規村作成
    @PostMapping("/new-village")
    private String makeVillage(@Validated @ModelAttribute("villageForm") NewVillageForm villageForm, BindingResult bindingResult,
            Model model) {
        if (bindingResult.hasErrors()) {
            newVillageAssist.setIndexModel(villageForm, model);
            return "new-village";
        }
        // ログインしていなかったらNG
        UserInfo userInfo = WerewolfMansionUserInfoUtil.getUserInfo();
        if (userInfo == null) {
            model.addAttribute("errorMessage", "ログインし直してください。");
            newVillageAssist.setIndexModel(villageForm, model);
            return "new-village";
        }
        // 決着のついていない参加していたり建てた村が終了していなかったらNG
        if (participateLogic.isParticipatingOrCreatingVillage()) {
            model.addAttribute("errorMessage", "参加/村建てした村の決着がつくまでは村を建てられません。");
            newVillageAssist.setIndexModel(villageForm, model);
            return "new-village";
        }
        Village village = null;
        try {
            village = newVillageAssist.createVillage(villageForm, userInfo.getUsername());
        } catch (WerewolfMansionBusinessException e) {
            model.addAttribute("errorMessage", e.getMessage());
            newVillageAssist.setIndexModel(villageForm, model);
            return "new-village";
        }
        return "redirect:/village/" + village.getVillageId();
    }

    // ===================================================================================
    //                                                                        Assist Logic
    //                                                                        ============
    private String makeInterval(NewVillageForm villageForm) {
        String hour = String.format("%02d時間", villageForm.getDayChangeIntervalHours());
        String minute = String.format("%02d分", villageForm.getDayChangeIntervalMinutes());
        String second = String.format("%02d秒", villageForm.getDayChangeIntervalSeconds());
        return hour + minute + second;
    }

    private LocalDateTime makeStartDateTime(NewVillageForm villageForm) {
        return LocalDateTime.of(villageForm.getStartYear(), villageForm.getStartMonth(), villageForm.getStartDay(),
                villageForm.getStartHour(), villageForm.getStartMinute(), 0);
    }
}
