package com.ort.app.web.controller;

import com.ort.app.logic.VillageParticipateLogic;
import com.ort.app.web.controller.assist.NewVillageAssist;
import com.ort.app.web.exception.WolfMansionBusinessException;
import com.ort.app.web.form.NewVillageForm;
import com.ort.app.web.form.validator.NewVillageFormValidator;
import com.ort.dbflute.exbhv.CharaBhv;
import com.ort.dbflute.exbhv.CharaGroupBhv;
import com.ort.dbflute.exentity.Chara;
import com.ort.dbflute.exentity.CharaGroup;
import com.ort.dbflute.exentity.Village;
import com.ort.fw.security.UserInfo;
import com.ort.fw.util.WolfMansionUserInfoUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

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
    @RequestMapping("/new-village") // 戻る場合もあるのでpostもok
    private String newVillageIndex(NewVillageForm form, Model model) {
        newVillageAssist.setIndexModel(form, model);
        return "new-village";
    }

    // 新規村作成_流用
    @PostMapping("/new-village/divert/{villageId}")
    private String divert(@PathVariable Integer villageId, NewVillageForm form, Model model) {
        newVillageAssist.setIndexModel(form, model);
        newVillageAssist.override(form, model, villageId);
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
        UserInfo userInfo = WolfMansionUserInfoUtil.getUserInfo();
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
        // キャラチップ数が足りなかったらNG
        if (!newVillageAssist.isEnoughCharacterNum(villageForm)) {
            model.addAttribute("errorMessage", "定員に対してキャラ数が不足しています。");
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
            cb.query().setCharaId_Equal(villageForm.getDummyCharaId());
            cb.query().setCharaGroupId_Equal(charaGroup.getCharaGroupId());
        });
        charaBhv.loadCharaImage(chara, charaImageCB -> {
            charaImageCB.query().setFaceTypeCode_Equal_通常();
        });
        model.addAttribute("dummyCharaName", chara.getCharaName());
        model.addAttribute("characterImgUrl", chara.getNormalCharaImgUrl());
        model.addAttribute("characterImgWidth", chara.getDisplayWidth());
        model.addAttribute("characterImgHeight", chara.getDisplayHeight());

        // 時間と日時の表示
        model.addAttribute("startDateTime", makeStartDateTime(villageForm));
        model.addAttribute("interval", makeInterval(villageForm));

        return "new-village-confirm";
    }

    // 新規村作成
    @PostMapping("/new-village/create")
    private String makeVillage(
            @Validated @ModelAttribute("villageForm") NewVillageForm villageForm,
            BindingResult bindingResult,
            Model model
    ) {
        if (bindingResult.hasErrors()) {
            newVillageAssist.setIndexModel(villageForm, model);
            return "new-village";
        }
        // ログインしていなかったらNG
        UserInfo userInfo = WolfMansionUserInfoUtil.getUserInfo();
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
        } catch (WolfMansionBusinessException e) {
            model.addAttribute("errorMessage", e.getMessage());
            newVillageAssist.setIndexModel(villageForm, model);
            return "new-village";
        }
        return "redirect:/village/" + village.getVillageId() + "#bottom";
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
