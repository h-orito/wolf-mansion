package com.ort.app.web.controller;

import org.dbflute.cbean.result.ListResultBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.ort.app.web.form.LoginForm;
import com.ort.app.web.model.IndexResultContent;
import com.ort.app.web.model.inner.IndexVillageDto;
import com.ort.dbflute.exbhv.VillageBhv;
import com.ort.dbflute.exentity.Village;

@Controller
public class IndexController {

    // ===================================================================================
    //                                                                           Attribute
    //                                                                           =========
    @Autowired
    private VillageBhv villageBhv;

    // ===================================================================================
    //                                                                             Execute
    //                                                                             =======
    @GetMapping("/")
    private String index(LoginForm form, Model model) {
        setIndexModel(form, model);
        return "index";
    }

    // ===================================================================================
    //                                                                             Mapping
    //                                                                             =======
    private IndexResultContent mappingToContent(ListResultBean<Village> villageList) {
        IndexResultContent content = new IndexResultContent();
        content.setVillageList(villageList.mappingList(village -> convertToIndexVillage(village)));
        return content;
    }

    private IndexVillageDto convertToIndexVillage(Village village) {
        IndexVillageDto villageDto = new IndexVillageDto();
        villageDto.setVillageId(village.getVillageId());
        villageDto.setVillageName(village.getVillageDisplayName());
        villageDto.setStatus(village.getVillageStatus().get().getVillageStatusName());
        return villageDto;
    }

    // ===================================================================================
    //                                                                        Assist Logic
    //                                                                        ============
    private void setIndexModel(LoginForm form, Model model) {
        model.addAttribute("form", form);
        ListResultBean<Village> villageList = villageBhv.selectList(cb -> {
            cb.setupSelect_VillageSettingsAsOne();
            cb.setupSelect_VillageStatus();
        });
        IndexResultContent content = mappingToContent(villageList);
        model.addAttribute("content", content);
    }

}
