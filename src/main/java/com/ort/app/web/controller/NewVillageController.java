package com.ort.app.web.controller;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ort.app.logic.VillageParticipateLogic;
import com.ort.app.web.controller.assist.NewVillageAssist;
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
    // ???????????????????????????
    @RequestMapping("/new-village") // ???????????????????????????post???ok
    private String newVillageIndex(NewVillageForm form, Model model) {
        newVillageAssist.setIndexModel(form, model);
        return "new-village";
    }

    // ???????????????_??????
    @PostMapping("/new-village/divert/{villageId}")
    private String divert(@PathVariable Integer villageId, NewVillageForm form, Model model) {
        newVillageAssist.setIndexModel(form, model);
        newVillageAssist.override(form, model, villageId);
        return "new-village";
    }

    // ???????????????????????????
    @PostMapping("/new-village/confirm")
    private String newVillageConfirm(@Validated @ModelAttribute("villageForm") NewVillageForm villageForm, BindingResult bindingResult,
            Model model) {
        if (bindingResult.hasErrors()) {
            newVillageAssist.setIndexModel(villageForm, model);
            return "new-village";
        }
        // ????????????????????????????????????NG
        UserInfo userInfo = WerewolfMansionUserInfoUtil.getUserInfo();
        if (userInfo == null) {
            model.addAttribute("errorMessage", "???????????????????????????????????????");
            newVillageAssist.setIndexModel(villageForm, model);
            return "new-village";
        }
        // ?????????????????????????????????????????????????????????????????????????????????????????????NG
        if (participateLogic.isParticipatingOrCreatingVillage()) {
            model.addAttribute("errorMessage", "??????/???????????????????????????????????????????????????????????????????????????");
            newVillageAssist.setIndexModel(villageForm, model);
            return "new-village";
        }
        // ?????????????????????????????????????????????NG
        if (!newVillageAssist.isEnoughCharacterNum(villageForm)) {
            model.addAttribute("errorMessage", "?????????????????????????????????????????????????????????");
            newVillageAssist.setIndexModel(villageForm, model);
            return "new-village";
        }

        // ??????????????????????????????
        CharaGroup charaGroup =
                charaGroupBhv.selectEntityWithDeletedCheck(cb -> cb.query().setCharaGroupId_Equal(villageForm.getCharacterSetId()));
        String charaGroupName = charaGroup.getCharaGroupName();
        model.addAttribute("characterSetName", charaGroupName);
        // ???????????????URL
        Chara chara = charaBhv.selectEntityWithDeletedCheck(cb -> {
            cb.query().setCharaId_Equal(villageForm.getDummyCharaId());
            cb.query().setCharaGroupId_Equal(charaGroup.getCharaGroupId());
        });
        charaBhv.loadCharaImage(chara, charaImageCB -> {
            charaImageCB.query().setFaceTypeCode_Equal_??????();
        });
        model.addAttribute("dummyCharaName", chara.getCharaName());
        model.addAttribute("characterImgUrl", chara.getNormalCharaImgUrl());
        model.addAttribute("characterImgWidth", chara.getDisplayWidth());
        model.addAttribute("characterImgHeight", chara.getDisplayHeight());

        // ????????????????????????
        model.addAttribute("startDateTime", makeStartDateTime(villageForm));
        model.addAttribute("interval", makeInterval(villageForm));

        return "new-village-confirm";
    }

    // ???????????????
    @PostMapping("/new-village/create")
    private String makeVillage(@Validated @ModelAttribute("villageForm") NewVillageForm villageForm, BindingResult bindingResult,
            Model model) {
        if (bindingResult.hasErrors()) {
            newVillageAssist.setIndexModel(villageForm, model);
            return "new-village";
        }
        // ????????????????????????????????????NG
        UserInfo userInfo = WerewolfMansionUserInfoUtil.getUserInfo();
        if (userInfo == null) {
            model.addAttribute("errorMessage", "???????????????????????????????????????");
            newVillageAssist.setIndexModel(villageForm, model);
            return "new-village";
        }
        // ?????????????????????????????????????????????????????????????????????????????????????????????NG
        if (participateLogic.isParticipatingOrCreatingVillage()) {
            model.addAttribute("errorMessage", "??????/???????????????????????????????????????????????????????????????????????????");
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
        String hour = String.format("%02d??????", villageForm.getDayChangeIntervalHours());
        String minute = String.format("%02d???", villageForm.getDayChangeIntervalMinutes());
        String second = String.format("%02d???", villageForm.getDayChangeIntervalSeconds());
        return hour + minute + second;
    }

    private LocalDateTime makeStartDateTime(NewVillageForm villageForm) {
        return LocalDateTime.of(villageForm.getStartYear(), villageForm.getStartMonth(), villageForm.getStartDay(),
                villageForm.getStartHour(), villageForm.getStartMinute(), 0);
    }
}
