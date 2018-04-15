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
import com.ort.app.web.exception.WerewolfMansionBusinessException;
import com.ort.app.web.form.NewVillageForm;
import com.ort.app.web.form.validator.NewVillageFormValidator;
import com.ort.dbflute.exbhv.CharaBhv;
import com.ort.dbflute.exbhv.CharaGroupBhv;
import com.ort.dbflute.exentity.CharaGroup;
import com.ort.dbflute.exentity.Village;
import com.ort.fw.security.UserInfo;
import com.ort.fw.util.WerewolfMansionUserInfoUtil;

@Controller
public class NewVillageController {

    //    alter table VILLAGE_SETTINGS add column IS_AVAILABLE_SAME_WOLF_ATTACK BOOLEAN NOT NULL COMMENT '連続襲撃可能か' after IS_AVAILABLE_SPECTATE;
    //    alter table VILLAGE_SETTINGS add column IS_OPEN_SKILL_IN_GRAVE BOOLEAN NOT NULL COMMENT '墓下役職公開ありか' after IS_AVAILABLE_SAME_WOLF_ATTACK;
    //    alter table VILLAGE_SETTINGS add column IS_VISIBLE_GRAVE_SPECTATE_MESSAGE BOOLEAN NOT NULL COMMENT '墓下見学発言を生存者が見られるか' after IS_OPEN_SKILL_IN_GRAVE;
    //    alter table VILLAGE_SETTINGS add column IS_AVAILABLE_MESSAGE_FUNCTION BOOLEAN NOT NULL COMMENT '特殊発言機能が使用可能か' after IS_VISIBLE_GRAVE_SPECTATE_MESSAGE;
    //    alter table VILLAGE_SETTINGS add column ORGANIZE VARCHAR(400) NOT NULL COMMENT '構成' after JOIN_PASSWORD;
    //
    //    update VILLAGE_SETTINGS set 
    //        IS_AVAILABLE_SAME_WOLF_ATTACK = 1,
    //        IS_OPEN_SKILL_IN_GRAVE = 0,
    //        IS_VISIBLE_GRAVE_SPECTATE_MESSAGE = 0,
    //        IS_AVAILABLE_MESSAGE_FUNCTION = 0,
    //        ORGANIZE = '狼狼賢導村村村村\n狼狼賢導村村村村村\n狼狼狂賢導狩村村村村\n狼狼狂賢導狩村村村村村\n狼狼狂賢導狩村村村村共共\n狼狼狼狂賢導狩村村村村共共\n狼狼狼狂狐賢導狩村村村村共共\n狼狼狼狂狐賢導狩村村村村村共共\n狼狼狼狂狐賢導狩村村村村村村共共\n狼狼狼狂狐賢導狩村村村村村村村共共\n狼狼狼狼狂狐賢導狩村村村村村村村共共\n狼狼狼狼狂狐賢導狩村村村村村村村村共共\n狼狼狼狼狂狐賢導狩村村村村村村村村村共共'
    //    ;
    //    
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
        // キャラセット名を取得
        CharaGroup charaGroup =
                charaGroupBhv.selectEntityWithDeletedCheck(cb -> cb.query().setCharaGroupId_Equal(villageForm.getCharacterSetId()));
        String charaGroupName = charaGroup.getCharaGroupName();
        model.addAttribute("characterSetName", charaGroupName);
        // キャラ画像URL
        String imgUrl = charaBhv.selectEntityWithDeletedCheck(cb -> {
            cb.query().setIsDummy_Equal_True();
            cb.query().setCharaGroupId_Equal(charaGroup.getCharaGroupId());
        }).getCharaImgUrl();
        model.addAttribute("characterImgUrl", imgUrl);
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
