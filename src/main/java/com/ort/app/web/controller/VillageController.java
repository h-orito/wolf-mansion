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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.ort.app.web.form.NewVillageForm;
import com.ort.app.web.form.validator.NewVillageFormValidator;
import com.ort.dbflute.exbhv.VillageBhv;
import com.ort.dbflute.exbhv.VillageSettingsBhv;
import com.ort.dbflute.exentity.Village;
import com.ort.dbflute.exentity.VillageSettings;
import com.ort.fw.util.WerewolfMansionDateUtil;

@Controller
public class VillageController {

    // ===================================================================================
    //                                                                           Attribute
    //                                                                           =========
    @Autowired
    private VillageBhv villageBhv;

    @Autowired
    private VillageSettingsBhv villageSettingsBhv;

    @Autowired
    NewVillageFormValidator newVillageFormValidator;

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.addValidators(newVillageFormValidator);
    }

    // ===================================================================================
    //                                                                             Execute
    //                                                                             =======
    // 新規村作成初期表示
    @GetMapping("/new-village")
    private String newVillageIndex(Model model) {
        setIndexModel(new NewVillageForm(), model);
        return "new-village";
    }

    // 新規村作成
    @PostMapping("/new-village")
    private String makeVillage(@Validated @ModelAttribute("villageForm") NewVillageForm villageForm, BindingResult bindingResult,
            Model model) {
        if (bindingResult.hasErrors()) {
            // model.addAttribute("villageForm", form);
            return "new-village";
        }

        Village village = insertVillageAndSettings(villageForm);
        return "redirect:/village/" + village.getVilalgeId();
    }

    // 村初期表示
    @GetMapping("/village/{villageId}")
    private String villageIndex(@PathVariable Integer villageId, Model model) {
        Village village = villageBhv.selectByPK(villageId).orElseThrow(() -> {
            return new IllegalStateException("そんな村ないよ"); // TODO h-orito なおす (2017/12/14) 
        });
        model.addAttribute("villageName", village.getVillageDisplayName());
        return "village";
    }

    // ===================================================================================
    //                                                                              Update
    //                                                                              ======
    private Village insertVillageAndSettings(NewVillageForm villageForm) {
        Village village = insertVillage(villageForm);
        insertVillageSettings(villageForm, village);
        return village;
    }

    private Village insertVillage(NewVillageForm villageForm) {
        Village village = new Village();
        village.setVillageDisplayName(villageForm.getVillageName());
        villageBhv.insert(village);
        return village;
    }

    private void insertVillageSettings(NewVillageForm villageForm, Village village) {
        VillageSettings settings = new VillageSettings();
        settings.setVillageId(village.getVilalgeId());
        settings.setStartPersonMinNum(villageForm.getStartPersonMinNum());
        settings.setPersonMaxNum(villageForm.getPersonMaxNum());
        settings.setDayChangeIntervalSeconds(villageForm.getDayChangeIntervalHours() * 3600 + villageForm.getDayChangeIntervalMinutes() * 60
                + villageForm.getDayChangeIntervalSeconds());
        settings.setStartDatetime(LocalDateTime.of(villageForm.getStartYear(), villageForm.getStartMonth(), villageForm.getStartDay(),
                villageForm.getStartHour(), villageForm.getStartMinute()));
        settings.setIsOpenVote(villageForm.getIsOpenVote());
        settings.setIsPossibleSkillRequest(villageForm.getIsPossibleSkillRequest());
        villageSettingsBhv.insert(settings);
    }

    // ===================================================================================
    //                                                                        Assist Logic
    //                                                                        ============
    private void setIndexModel(NewVillageForm form, Model model) {
        if (form.getIsOpenVote() == null) {
            form.setIsOpenVote(true);
        }
        if (form.getIsPossibleSkillRequest() == null) {
            form.setIsPossibleSkillRequest(true);
        }
        if (form.getDayChangeIntervalHours() == null) {
            form.setDayChangeIntervalHours(24);
        }
        if (form.getStartYear() == null) {
            // 一週間後にしておく
            LocalDateTime oneWeekAfter = WerewolfMansionDateUtil.currentLocalDateTime().plusDays(7L);
            form.setStartYear(oneWeekAfter.getYear());
            form.setStartMonth(oneWeekAfter.getMonthValue());
            form.setStartDay(oneWeekAfter.getDayOfMonth());
            form.setStartHour(0);
            form.setStartMinute(0);
        }
        model.addAttribute("villageForm", form);
    }
}
