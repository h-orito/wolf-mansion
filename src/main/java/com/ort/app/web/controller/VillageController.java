package com.ort.app.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.ort.app.web.form.NewVillageForm;
import com.ort.dbflute.exbhv.VillageBhv;
import com.ort.dbflute.exentity.Village;

@Controller
public class VillageController {

    // ===================================================================================
    //                                                                           Attribute
    //                                                                           =========
    @Autowired
    private VillageBhv villageBhv;

    // ===================================================================================
    //                                                                             Execute
    //                                                                             =======
    // 新規村作成初期表示
    @GetMapping("/new-village")
    private String newVillageIndex(NewVillageForm form, Model model) {
        model.addAttribute("form", form);
        return "new-village";
    }

    // 新規村作成
    @PostMapping("/new-village")
    private String makeVillage(@Validated NewVillageForm form, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("form", form);
            return "new-village";
        }

        Village village = new Village();
        village.setVillageDisplayName(form.getVillageName());
        villageBhv.insert(village);
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
}
