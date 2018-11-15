package com.ort.app.web.controller;

import java.util.Arrays;

import org.dbflute.cbean.result.ListResultBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ort.app.web.controller.logic.VillageParticipateLogic;
import com.ort.app.web.form.LoginForm;
import com.ort.app.web.model.IndexResultContent;
import com.ort.app.web.model.inner.IndexVillageDto;
import com.ort.dbflute.allcommon.CDef;
import com.ort.dbflute.exbhv.VillageBhv;
import com.ort.dbflute.exentity.Village;

@Controller
public class IndexController {

    // ===================================================================================
    //                                                                           Attribute
    //                                                                           =========
    @Autowired
    private VillageBhv villageBhv;

    @Autowired
    private VillageParticipateLogic participateLogic;

    // ===================================================================================
    //                                                                             Execute
    //                                                                             =======
    @GetMapping("/")
    private String index(LoginForm form, Model model) {
        setIndexModel(form, model);
        return "index";
    }

    @GetMapping("/recruiting")
    @ResponseBody
    private IndexResultContent recruiting() {
        ListResultBean<Village> villageList = villageBhv.selectList(cb -> {
            cb.setupSelect_VillageSettingsAsOne();
            cb.setupSelect_VillageStatus();
            cb.query().setVillageStatusCode_Equal_募集中();
            cb.query().addOrderBy_VillageId_Asc();
        });
        villageBhv.loadVillagePlayer(villageList, cb -> {
            cb.query().setIsGone_Equal_False();
            cb.query().setIsSpectator_Equal_False();
        });
        return mappingToContent(villageList);
    }

    // ===================================================================================
    //                                                                             Mapping
    //                                                                             =======
    private IndexResultContent mappingToContent(ListResultBean<Village> villageList) {
        IndexResultContent content = new IndexResultContent();
        content.setVillageList(villageList.mappingList(village -> convertToIndexVillage(village)));
        content.setIsParticipate(participateLogic.isParticipatingOrCreatingVillage());
        return content;
    }

    private IndexVillageDto convertToIndexVillage(Village village) {
        IndexVillageDto villageDto = new IndexVillageDto();
        villageDto.setVillageId(village.getVillageId());
        villageDto.setVillageNumber(String.format("%04d", village.getVillageId()));
        villageDto.setVillageName(village.getVillageDisplayName());
        int participateNum = village.getVillagePlayerList().size();
        Integer maxNum = village.getVillageSettingsAsOne().get().getPersonMaxNum();
        villageDto.setParticipateNum(String.format("%d/%d人", participateNum, maxNum));
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
            cb.query().setVillageStatusCode_InScope_AsVillageStatus(
                    Arrays.asList(CDef.VillageStatus.エピローグ, CDef.VillageStatus.募集中, CDef.VillageStatus.進行中, CDef.VillageStatus.開始待ち));
            cb.query().addOrderBy_VillageId_Asc();
        });
        villageBhv.loadVillagePlayer(villageList, cb -> {
            cb.query().setIsGone_Equal_False();
            cb.query().setIsSpectator_Equal_False();
        });
        IndexResultContent content = mappingToContent(villageList);
        model.addAttribute("content", content);
    }
}
