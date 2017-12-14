package com.ort.app.web.controller;

import org.dbflute.cbean.result.ListResultBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.ort.app.web.form.IndexForm;
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
    private String index(IndexForm form, Model model) {
        ListResultBean<Village> villageList = villageBhv.selectList(cb -> {});
        IndexResultContent content = mappingToContent(villageList);
        model.addAttribute("content", content);
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
        villageDto.setVillageId(village.getVilalgeId());
        villageDto.setVillageName(village.getVillageDisplayName());
        return villageDto;
    }

}
